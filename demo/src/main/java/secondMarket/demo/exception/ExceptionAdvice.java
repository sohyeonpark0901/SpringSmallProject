package secondMarket.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({
            IllegalStateException.class
    })
    
    public String exception(Exception e){
        System.out.println(e);
        return "fail";
    }

}
