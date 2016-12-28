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
public interface UploadStrategy<T, E> {
    T saveBytes(E object) throws IOException;
    T appendBytes(E object) throws IOException;
}
