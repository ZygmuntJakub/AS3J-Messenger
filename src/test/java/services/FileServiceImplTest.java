package services;

import com.as3j.messenger.common.ApiConfig;
import com.as3j.messenger.exceptions.ErrorProcessingImageException;
import com.as3j.messenger.exceptions.NoSuchFileException;
import com.as3j.messenger.services.impl.FileServiceImpl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileServiceImplTest {
    private ApiConfig apiConfig;
    private Storage storage;
    private FileServiceImpl fileService;

    @BeforeEach
    void setUp() {
        storage = mock(Storage.class);
        apiConfig = mock(ApiConfig.class);
        fileService = new FileServiceImpl(storage, apiConfig);
    }

    @Test
    void shouldUploadScaledTempPhoto() throws ErrorProcessingImageException, IOException {
        //given
        var file = new File("src/test/resources/lenna.png");
        var mockPhoto = new MockMultipartFile("file", Files.readAllBytes(file.toPath()));
        var argument = ArgumentCaptor.forClass(byte[].class);
        doReturn("test").when(apiConfig).getTempBucketName();
        //when
        fileService.uploadTempPhoto(mockPhoto);
        //then
        verify(storage).create(any(BlobInfo.class), argument.capture());
        verify(apiConfig, times(1)).getTempBucketName();
        verify(apiConfig, never()).getBucketName();
        try(var in = new ByteArrayInputStream(argument.getValue())) {
            var image = ImageIO.read(in);
            assertTrue(image.getWidth() == 400 || image.getHeight() == 400);
        }
    }

    @Test
    void shouldMovePhotoToPersistentBucket() throws NoSuchFileException {
        //given
        var source = UUID.randomUUID();
        var target = UUID.randomUUID();
        var argument = ArgumentCaptor.forClass(Storage.CopyRequest.class);
        doReturn("temp").when(apiConfig).getTempBucketName();
        doReturn("persistent").when(apiConfig).getBucketName();
        //when
        fileService.updatePhoto(source, target);
        //then
        verify(storage).copy(argument.capture());
        assertEquals("temp", argument.getValue().getSource().getBucket());
        assertEquals(source+".png", argument.getValue().getSource().getName());
        assertEquals("persistent", argument.getValue().getTarget().getBucket());
        assertEquals(target+".png", argument.getValue().getTarget().getName());
    }

    @Test
    void shouldThrowNoSuchFileExceptionOn404ResponseFromStorage() {
        //given
        doReturn("temp").when(apiConfig).getTempBucketName();
        doReturn("persistent").when(apiConfig).getBucketName();
        doThrow(new StorageException(404, "test")).when(storage).copy(any(Storage.CopyRequest.class));
        //then
        assertThrows(NoSuchFileException.class, () -> fileService.updatePhoto(UUID.randomUUID(), UUID.randomUUID()));
    }
}
