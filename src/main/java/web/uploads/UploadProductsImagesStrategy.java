package web.uploads;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import web.admin.exceptions.AppendToNonExistentFileException;
import web.models.RequestUploadFile;


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
    
    private File getFileToSave(String fileName, String contentType) throws AppendToNonExistentFileException{
        if(fileName != null){
            File file = new File(realPathtoUploads, fileName);
            if(file.exists() && file.canWrite())
                return file;
            else
                throw new AppendToNonExistentFileException(fileName);
        }
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), contentType.replace("image/", ""));
        return new File(realPathtoUploads, name);
    }
    
    @Override
    public String saveBytes(RequestUploadFile uploadFile) throws IOException{
        // get new file to save bytes
        File fileToSave = getFileToSave(null, uploadFile.getContentType());
        Files.write(fileToSave.toPath(), uploadFile.getBytes(), StandardOpenOption.CREATE);
        // return name
        return fileToSave.getName();
    }

    @Override
    public String appendBytes(RequestUploadFile uploadFile) throws IOException{
        HttpSession session = request.getSession();
        String fileName = null;
        if(uploadFile.getId() != null){
            // get file name from session for append bytes
            if(session.getAttribute(uploadFile.getId()) != null)
                fileName = (String)session.getAttribute(uploadFile.getId());
        }
        // get file to save bytes
        File fileToSave = getFileToSave(fileName, uploadFile.getContentType());
        // append bytes to file
        Files.write(fileToSave.toPath(), uploadFile.getBytes(), StandardOpenOption.APPEND);
        // save filename on session
        session.setAttribute(uploadFile.getId(), fileToSave.getName());
        return fileToSave.getName();
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
