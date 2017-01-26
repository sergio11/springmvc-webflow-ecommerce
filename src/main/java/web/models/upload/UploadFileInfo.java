package web.models.upload;

import java.io.Serializable;

/**
 * @author sergio
 */
public class UploadFileInfo implements Serializable {
    
    private Long size;
    private String contentType;
    private byte[] content;

    public UploadFileInfo(Long size, String contentType, byte[] content) {
        this.size = size;
        this.contentType = contentType;
        this.content = content;
    }
    
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
