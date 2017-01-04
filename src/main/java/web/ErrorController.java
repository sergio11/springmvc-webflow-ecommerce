package web;


import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import web.admin.exceptions.NotFoundException;

/**
 *
 * @author sergio
 */
@ControllerAdvice
public class ErrorController {
    
    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);
    
    @Autowired
    private HttpServletRequest request;
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidData(final ConstraintViolationException ex){
        String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
    
    @ExceptionHandler(NotFoundException.class)
    public String notfound(final NotFoundException notFoundException, final Model model) {
        return "redirect:global/errors/404";
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        throwable.printStackTrace();
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "redirect:global/errors/500";
    }
}
