package services;

import com.as3j.messenger.common.ApiConfig;
import com.as3j.messenger.services.impl.EmailServiceImpl;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmailServiceImplTest {
    private ApiConfig apiConfig;
    private MailjetClient client;
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        apiConfig = mock(ApiConfig.class);
        doReturn("test@email.com").when(apiConfig).getMailAddress();
        doReturn("Test test").when(apiConfig).getMailSender();
        client = mock(MailjetClient.class);
        emailService = new EmailServiceImpl(apiConfig);
        ReflectionTestUtils.setField(emailService, "client", client);
    }

    @Test
    public void shouldSendEmail() throws MailjetSocketTimeoutException, MailjetException, JSONException {
        //given
        var name = "test";
        var email = "test@test.test";
        var subject = "Test";
        var content = "Testing";
        var argument = ArgumentCaptor.forClass(MailjetRequest.class);

        //when
        emailService.sendEmail(email, name, subject, content);

        //then
        verify(client, times(1)).post(argument.capture());
        JSONObject json = argument
                .getValue()
                .getBodyJSON()
                .getJSONArray(Emailv31.MESSAGES)
                .getJSONObject(0);

        JSONObject from = json.getJSONObject(Emailv31.Message.FROM);
        assertEquals(apiConfig.getMailAddress(), from.getString("Email"));
        assertEquals(apiConfig.getMailSender(), from.getString("Name"));

        JSONObject to = json.getJSONArray(Emailv31.Message.TO).getJSONObject(0);
        assertEquals(email, to.getString("Email"));
        assertEquals(name, to.getString("Name"));

        assertEquals(subject, json.getString(Emailv31.Message.SUBJECT));
        assertEquals(content, json.getString(Emailv31.Message.HTMLPART));
    }
}
