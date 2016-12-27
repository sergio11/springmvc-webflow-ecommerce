/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.admin.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import web.models.Media;
import web.uploads.UploadProductsImagesStrategy;

/**
 *
 * @author sergio
 */
@RestController
@RequestMapping("/admin/products/uploads")
public class UploadController {
 
    @Autowired
    private UploadProductsImagesStrategy uploadStrategy;
    
    @PostMapping("/media")
    public void uploadMedia(
            HttpServletRequest request,
            @RequestBody MultipartFile file,
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "-1") int chunks,
            @RequestParam(required = false, defaultValue = "-1") int chunk) throws IOException {
        
        Media media = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("media") != null) {
            media = (Media)session.getAttribute("media");
        }else{
            media = new Media();
            media.setOriginalName(file.getOriginalFilename());
            media.setExt("png");
        }
        
        if (chunks > 0 && chunk > 0) {
            //Need to append the bytes in this chunk
            uploadStrategy.appendBytes(media, file.getBytes());
            if (chunk == chunks - 1)
                session.removeAttribute("media");
            else
                session.setAttribute("media", media);
        } else {
            uploadStrategy.saveBytes(media, file.getBytes());
            if (chunks <= 0)
                //no chunks were needed, all the bytes have been written out, upload is done
                session.removeAttribute("media");
            else
                session.setAttribute("media", media);
        }
    }
}
