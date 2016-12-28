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
public class ErrorResponse implements Serializable {
    
    private Integer code;
    private String message;

    public ErrorResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
   
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" + "code=" + code + ", message=" + message + '}';
    }
}
