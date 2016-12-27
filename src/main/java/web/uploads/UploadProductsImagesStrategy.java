/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.uploads;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import web.models.Media;

/**
 * @author sergio
 */
@Component
@Scope(value="request", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class UploadProductsImagesStrategy implements UploadStrategy<Media>{
       
    private static Logger logger = LoggerFactory.getLogger(UploadProductsImagesStrategy.class);
    
    @Autowired
    private HttpServletRequest request;
    
    @Value("${folder.products.images}")
    private String folderProducts;
    
    private String realPathtoUploads;
    
    private Path getFilePath(Media media){
        if(media.getName() != null){
            File file = new File(realPathtoUploads, media.getName());
            if(file.exists() && file.canWrite())
                return file.toPath();
        }
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), media.getExt());
        media.setName(name);
        return new File(realPathtoUploads, name).toPath();
    }
    
    @Override
    public Media saveBytes(Media media, byte[] bytes) throws IOException{
        Files.write(getFilePath(media), bytes, StandardOpenOption.CREATE);
        return media;
    }

    @Override
    public Media appendBytes(Media media, byte[] bytes) throws IOException{
        Files.write(getFilePath(media), bytes, StandardOpenOption.APPEND);
        return media;
    }
    
    @PostConstruct
    public void init() {
        logger.info("init UploadProductsImagesStrategy bean");
        realPathtoUploads = request.getServletContext().getRealPath(folderProducts);
        if (!new File(realPathtoUploads).exists()) {
            logger.info("create path to products images");
            new File(realPathtoUploads).mkdirs();
        }
    }
}
