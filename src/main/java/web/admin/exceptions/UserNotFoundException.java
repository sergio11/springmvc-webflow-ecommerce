package web.admin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author sergio
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="User not found")
public class UserNotFoundException extends NotFoundException {}
