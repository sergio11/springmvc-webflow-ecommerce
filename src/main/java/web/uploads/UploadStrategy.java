/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.uploads;

import java.io.IOException;

/**
 *
 * @author sergio
 */
public interface UploadStrategy<T> {
    T saveBytes(T object, byte[] bytes) throws IOException;
    T appendBytes(T object, byte[] bytes) throws IOException;
}
