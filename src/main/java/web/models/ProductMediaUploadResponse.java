/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.models;

import java.io.Serializable;

/**
 *
 * @author sergio
 */
public class ProductMediaUploadResponse implements Serializable {
    
    private String fileId;
    private String filename;
    private String contentType;
    private Long size;

    public ProductMediaUploadResponse(String fileId, String filename, String contentType, Long size) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
    }
    
    public ProductMediaUploadResponse(){}

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "UploadFileResponse{" + "filename=" + filename + ", contentType=" + contentType + ", size=" + size + '}';
    }
}
