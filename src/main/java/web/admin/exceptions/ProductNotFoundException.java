package web.admin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author sergio
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Product not found")
public class ProductNotFoundException extends NotFoundException {}
