package web.uploads;

import web.models.upload.RequestUploadFile;
import web.models.upload.UploadFileInfo;
import web.uploads.exceptions.FileNotFoundException;
import web.uploads.exceptions.UploadFailException;

/**
 *
 * @author sergio
 */
public interface UploadStrategy<T, E extends RequestUploadFile> {
    T save(E fileinfo) throws UploadFailException;
    T append(E fileinfo, T id) throws FileNotFoundException, UploadFailException;
    T appendIfExists(E fileinfo, T id) throws UploadFailException;
    void delete(T id);
    UploadFileInfo get(T id) throws FileNotFoundException, UploadFailException;
}
