package web.uploads;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
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
import web.models.upload.RequestUploadFile;
import web.models.upload.UploadFileInfo;
import web.uploads.exceptions.FileNotFoundException;
import web.uploads.exceptions.UploadFailException;


/**
 * @author sergio
 */
@Component
@Scope(value="request", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class UploadProductsImagesStrategy implements UploadStrategy<String, RequestUploadFile>{
       
    private static Logger logger = LoggerFactory.getLogger(UploadProductsImagesStrategy.class);
    
    @Autowired
    private HttpServletRequest request;
    @Value("${folder.products.images}")
    private String folderProducts;
    private String realPathtoUploads;
    
    @PostConstruct
    public void init() {
        logger.info("init UploadProductsImagesStrategy bean");
        realPathtoUploads = request.getServletContext().getRealPath(folderProducts);
        if (!new File(realPathtoUploads).exists()) {
            logger.info("create path to products images");
            new File(realPathtoUploads).mkdirs();
        }
    }
    
    private File getFileToSave(String contentType){
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), contentType.replace("image/", ""));
        return new File(realPathtoUploads, name);
    }
    
    private File getFileToSave(String fileName, String contentType, boolean createIfNotExists){
        File file = new File(realPathtoUploads, fileName);
        if (file.exists() && file.canWrite())
            return file;
        else
            if(createIfNotExists)
                return getFileToSave(contentType);
            else
                throw new FileNotFoundException();
    }
    
    @Override
    public String save(RequestUploadFile fileInfo) throws UploadFailException{
        try {
            // get new file to save bytes
            File fileToSave = getFileToSave(fileInfo.getContentType());
            Files.write(fileToSave.toPath(), fileInfo.getBytes(), StandardOpenOption.CREATE);
            // return name
            return fileToSave.getName();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new UploadFailException();
        }
    }
    
    @Override
    public String append(RequestUploadFile fileinfo, String id) throws FileNotFoundException, UploadFailException {
        try {
            // get file to save bytes
            File fileToSave = getFileToSave(id, fileinfo.getContentType(), false);
            // append bytes to file
            Files.write(fileToSave.toPath(), fileinfo.getBytes(), StandardOpenOption.APPEND);
            return fileToSave.getName();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new UploadFailException();
        }
    }
    
    @Override
    public String appendIfExists(RequestUploadFile fileinfo, String id) throws UploadFailException {
        try {
            // get file to save bytes
            File fileToSave = getFileToSave(id, fileinfo.getContentType(), true);
            // append bytes to file
            Files.write(fileToSave.toPath(), fileinfo.getBytes(), StandardOpenOption.APPEND);
            return fileToSave.getName();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new UploadFailException();
        }
    }
    
    @Override
    public void delete(String id){
        if(id != null){
            File file = new File(realPathtoUploads, id);
            if(file.exists() && file.canWrite())
                file.delete();
        }
    }

    @Override
    public UploadFileInfo get(String id) throws FileNotFoundException, UploadFailException {
        UploadFileInfo info = null;
        if(id != null){
            try {
                File file = new File(realPathtoUploads, id);
                if(file.exists() && file.canRead()){
                    String contentType = Files.probeContentType(file.toPath());
                    byte[] content = Files.readAllBytes(file.toPath());
                    info = new UploadFileInfo(file.length(), contentType, content);
                } else {
                    throw new FileNotFoundException();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage());
                throw new UploadFailException();
            }
        }
        return info;
    }

    
}
