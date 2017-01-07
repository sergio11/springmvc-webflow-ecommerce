package web.admin.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import persistence.models.User;
import persistence.repositories.UserRepository;
import security.CurrentUser;
import security.CurrentUserAttached;
import web.models.upload.RequestUploadAvatarFile;
import web.models.upload.UploadFileInfo;
import web.uploads.UploadAvatarStrategy;

/**
 * @author sergio
 */
@Controller("SelfUserAvatarController")
@RequestMapping("/admin/users/self/avatar")
public class SelfUserAvatarController {
    
    private static Logger logger = LoggerFactory.getLogger(SelfUserAvatarController.class);
    
    public static final String ATTRIBUTE_NAME = "user";
    
    @Autowired
    private UploadAvatarStrategy uploadAvatarStrategy;
    
    @RequestMapping(method = RequestMethod.GET)
    public String show(@CurrentUser User user, Model model) {
        if(!model.containsAttribute(ATTRIBUTE_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME, user);
        }
        String url = "/admin/users/self/avatar/upload";
        return String.format("admin/fragments/user/avatar::form(url='%s')", url);
    }
    
    @PostMapping("/upload")
    public String upload(
            @RequestPart("avatarfile") MultipartFile avatarFile,
            @CurrentUserAttached User activeUser) throws IOException{
        
        RequestUploadAvatarFile uploadAvatar = new RequestUploadAvatarFile(activeUser, avatarFile.getSize(),
                avatarFile.getBytes(), avatarFile.getContentType(), avatarFile.getOriginalFilename());
        logger.info(uploadAvatar.toString());
        uploadAvatarStrategy.save(uploadAvatar);
        return "redirect:/admin/users/self/avatar";
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
