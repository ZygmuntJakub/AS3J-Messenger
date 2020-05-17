package com.as3j.messenger.services.impl;

import com.as3j.messenger.common.ApiConfig;
import com.as3j.messenger.exceptions.ErrorProcessingImageException;
import com.as3j.messenger.services.FileService;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private Storage storage;
    private ApiConfig apiConfig;

    @Autowired
    public FileServiceImpl(Storage storage, ApiConfig apiConfig) {
        this.storage = storage;
        this.apiConfig = apiConfig;
    }

    @Override
    public UUID uploadTempPhoto(MultipartFile file) throws ErrorProcessingImageException {
        UUID id = UUID.randomUUID();
        BlobId blobId = BlobId.of(apiConfig.getTempBucketName(), id.toString()+".png");
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, scaleImage(file));
        return id;
    }

    private byte[] scaleImage(MultipartFile file) throws ErrorProcessingImageException {
        try(InputStream in = file.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(in);
            Dimension dimensions = getScaledDimension(new Dimension(image.getWidth(), image.getHeight()), new Dimension(400, 400));
            Image img = image.getScaledInstance(dimensions.width, dimensions.height, Image.SCALE_SMOOTH);
            BufferedImage scaledImage = new BufferedImage(dimensions.width, dimensions.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();
            ImageIO.write(scaledImage, "png", bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new ErrorProcessingImageException();
        }
    }

    private Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {
        double widthRatio = boundary.getWidth() / imageSize.getWidth();
        double heightRatio = boundary.getHeight() / imageSize.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);
        return new Dimension((int) (imageSize.width  * ratio),
                (int) (imageSize.height * ratio));
    }
}
