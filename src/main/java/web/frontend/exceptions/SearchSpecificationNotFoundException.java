package web.frontend.exceptions;

import web.admin.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sergio
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Search Specification not found")
public class SearchSpecificationNotFoundException extends NotFoundException {}
