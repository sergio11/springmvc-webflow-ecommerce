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
public class RequestUploadFile implements Serializable {
    
    private String id;
    private byte[] bytes;
    private String ext;
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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Override
    public String toString() {
        return "RequestUploadFile{" + "id=" + id + ", bytes=" + bytes + ", ext=" + ext + ", originalName=" + originalName + '}';
    }
}
