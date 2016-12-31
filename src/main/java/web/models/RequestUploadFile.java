package web.models;

import java.io.Serializable;

/**
 *
 * @author sergio
 */
public class RequestUploadFile implements Serializable {
    
    private String id;
    private byte[] bytes;
    private String contentType;
    private String originalName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Override
    public String toString() {
        return "RequestUploadFile{" + "id=" + id + ", bytes=" + bytes + ", ext=" + contentType + ", originalName=" + originalName + '}';
    }
}
