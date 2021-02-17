package secondMarket.demo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({
            IllegalStateException.class,
            ImageException.class
    })
    public String exception(Exception e, Model model){

        model.addAttribute("message",e.getMessage());

        return "fail";
    }

}
