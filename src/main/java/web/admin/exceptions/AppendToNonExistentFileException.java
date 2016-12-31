package web.admin.exceptions;

/**
 *
 * @author sergio
 */
public class AppendToNonExistentFileException extends RuntimeException {
    
    private String name;

    public AppendToNonExistentFileException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
