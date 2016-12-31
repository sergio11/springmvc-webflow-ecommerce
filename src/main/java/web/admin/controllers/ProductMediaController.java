package web.admin.controllers;

import java.io.IOException;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import web.admin.exceptions.AppendToNonExistentFileException;
import web.models.ErrorResponse;
import web.models.ProductMediaDeleteResponse;
import web.models.RequestUploadFile;
import web.models.ProductMediaUploadResponse;
import web.uploads.UploadProductsImagesStrategy;

/**
 *
 * @author sergio
 */
@RestController
@RequestMapping(value = "/admin/products/media", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductMediaController {
    
    private static Logger logger = LoggerFactory.getLogger(ProductMediaController.class);
 
    @Autowired
    private UploadProductsImagesStrategy uploadStrategy;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    
    @PostMapping("/upload")
    public ProductMediaUploadResponse upload(
            @RequestBody MultipartFile file,
            @RequestParam String fileid,
            @RequestParam(required = false, defaultValue = "-1") int chunks,
            @RequestParam(required = false, defaultValue = "-1") int chunk) throws IOException {
        
        RequestUploadFile uploadFile = new RequestUploadFile();
        uploadFile.setId(fileid);
        uploadFile.setBytes(file.getBytes());
        uploadFile.setContentType(file.getContentType());
        uploadFile.setOriginalName(file.getOriginalFilename());
        
        String fileName = null;
        if (chunks > 0 && chunk > 0) {
            //Need to append the bytes in this chunk
            fileName = uploadStrategy.appendBytes(uploadFile);
        } else {
            fileName = uploadStrategy.saveBytes(uploadFile);
        }
       
        return new ProductMediaUploadResponse(fileid, fileName, file.getContentType(), file.getSize());
    }
    
    @DeleteMapping("/delete/{name}")
    public ProductMediaDeleteResponse delete(@PathVariable String name) throws IOException{
        Boolean result = uploadStrategy.delete(name);
        return new ProductMediaDeleteResponse(name, result);
    }
    
    @ExceptionHandler(AppendToNonExistentFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse appendToFileError(AppendToNonExistentFileException e){
        String message = messageSource.getMessage("errors.upload.file.append", new Object[]{e.getName()}, Locale.getDefault());
        return new ErrorResponse(1000, message);
    }
}
