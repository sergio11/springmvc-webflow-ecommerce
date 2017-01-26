package web.models;

import java.io.Serializable;
/**
 *
 * @author sergio
 */
public class ProductMediaDeleteResponse implements Serializable {
   
    private String filename;
    private Boolean result;

    public ProductMediaDeleteResponse(String filename, Boolean result) {
        this.filename = filename;
        this.result = result;
    }
    
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ProductMediaDeleteResponse{" + "filename=" + filename + ", result=" + result + '}';
    }
}
