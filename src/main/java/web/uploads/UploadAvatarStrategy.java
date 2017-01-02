package web.uploads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import persistence.models.Avatar;
import persistence.repositories.AvatarRepository;
import web.models.upload.RequestUploadAvatarFile;
import web.models.upload.UploadFileInfo;
import web.uploads.exceptions.FileNotFoundException;
import web.uploads.exceptions.UploadFailException;

/**
 * @author sergio
 */
@Component
public class UploadAvatarStrategy implements UploadStrategy<Long, RequestUploadAvatarFile>{
    
    private static Logger logger = LoggerFactory.getLogger(UploadAvatarStrategy.class);
    @Autowired
    private AvatarRepository avatarRepository;

    @Override
    public Long save(RequestUploadAvatarFile fileinfo) throws UploadFailException {
        Avatar avatar = new Avatar();
        avatar.setName(fileinfo.getOriginalName());
        avatar.setContentType(fileinfo.getContentType());
        avatar.setUser(fileinfo.getUser());
        avatar.setContent(fileinfo.getBytes());
        avatarRepository.save(avatar);
        return avatar.getId();
    }

    @Override
    public Long append(RequestUploadAvatarFile fileinfo, Long id) throws FileNotFoundException, UploadFailException {
        Avatar avatar = avatarRepository.findOne(id);
        if(avatar == null){
            throw new FileNotFoundException();
        }
        avatar.setContent(fileinfo.getBytes());
        avatarRepository.save(avatar);
        return avatar.getId();
    }

    @Override
    public Long appendIfExists(RequestUploadAvatarFile fileinfo, Long id) throws UploadFailException {
        Avatar avatar = avatarRepository.findOne(id);
        if(avatar == null){
            avatar = new Avatar();
            avatar.setName(fileinfo.getOriginalName());
            avatar.setContentType(fileinfo.getContentType());
            avatar.setUser(fileinfo.getUser());
            avatar.setContent(fileinfo.getBytes());
            avatarRepository.save(avatar);
        } else {
            avatar.setContent(fileinfo.getBytes());
        }
        avatarRepository.save(avatar);
        return avatar.getId();
    }

    @Override
    public void delete(Long id) {
        avatarRepository.delete(id);
    }

    @Override
    public UploadFileInfo get(Long id) throws FileNotFoundException, UploadFailException {
        Avatar avatar =  avatarRepository.findOne(id);
        if(avatar == null){
            throw new FileNotFoundException();
        }
        logger.info(avatar.toString());
        return new UploadFileInfo(avatar.getSize(), avatar.getContentType(), avatar.getContent());
    }
}
