package web.admin.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import persistence.models.User;
import security.CurrentUserAttached;
import web.models.upload.RequestUploadAvatarFile;
import web.models.upload.UploadFileInfo;
import web.uploads.UploadAvatarStrategy;

/**
 * @author sergio
 */
@Controller("UserAvatarController")
@RequestMapping("/admin/users/avatar")
public class UserAvatarController {
    
    private static Logger logger = LoggerFactory.getLogger(UserAvatarController.class);
    
    @Autowired
    private UploadAvatarStrategy uploadAvatarStrategy;
    
    @PostMapping("/upload")
    public String upload(
            @RequestPart("avatar") MultipartFile avatarFile,
            @CurrentUserAttached User activeUser,
            HttpServletRequest request) throws IOException{
        
        RequestUploadAvatarFile uploadAvatar = new RequestUploadAvatarFile(activeUser, avatarFile.getSize(),
                avatarFile.getBytes(), avatarFile.getContentType(), avatarFile.getOriginalFilename());
        logger.info(uploadAvatar.toString());
        uploadAvatarStrategy.save(uploadAvatar);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable Long id){
        UploadFileInfo info = uploadAvatarStrategy.get(id);
        return ResponseEntity.ok()
            .contentLength(info.getSize())
            .contentType(MediaType.parseMediaType(info.getContentType()))
            .body(info.getContent());
    }
}
