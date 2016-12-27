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
public class Media implements Serializable {
    
    private String originalName;
    private String name;
    private String ext;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "Media{" + "originalName=" + originalName + ", name=" + name + ", ext=" + ext + '}';
    }
}
