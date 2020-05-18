package controllers;

import com.as3j.messenger.controllers.FileController;
import com.as3j.messenger.exceptions.ErrorProcessingImageException;
import com.as3j.messenger.services.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FileControllerTest {
    private FileService fileService;
    private FileController fileController;

    @BeforeEach
    void setUp() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    void shouldUploadTempPhotoAndReturnUUID() throws ErrorProcessingImageException {
        //given
        var mockPhoto = new MockMultipartFile("file", "photo.png",
                "multipart/form-data", (byte[]) null);
        var id = UUID.randomUUID();
        doReturn(id).when(fileService).uploadTempPhoto(any(MultipartFile.class));
        //when
        var value = fileController.uploadPhoto(mockPhoto);
        //then
        verify(fileService, times(1)).uploadTempPhoto(mockPhoto);
        assertEquals(id, value.getValue());
    }
}
