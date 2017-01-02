package web.models.upload;

import java.io.Serializable;
import persistence.models.User;

/**
 * @author sergio
 */
public class RequestUploadAvatarFile extends RequestUploadFile implements Serializable {
    
    private User user;
    private Long size;

    public RequestUploadAvatarFile(User user, Long size, byte[] bytes, String contentType, String originalName) {
        super(bytes, contentType, originalName);
        this.user = user;
        this.size = size;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "RequestUploadAvatarFile{" + "user=" + user + ", size=" + size + '}';
    }
}
