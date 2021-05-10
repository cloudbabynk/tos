package net.huadong.tech.base.bean;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import javax.servlet.http.HttpServletRequest;
import net.huadong.idev.hdmessagecode.HdMessageCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({HdRunTimeException.class})
    @ResponseBody
    public HdMessageCode defaultErrorHandler(HttpServletRequest req, HdRunTimeException e) throws Exception {
        log.error(req.getRequestURI() + "============" + e.getMessage());
        return e.genMsg();
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public HdMessageCode defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        HdMessageCode result;
        if (e.getCause() != null && e.getCause().getCause() instanceof HdRunTimeException) {
            result = new HdMessageCode();
            result.setCode("-1");
            result.setKey("-1");
            result.setMessage(e.getCause().getCause().getMessage());
            return result;
        } else {
            log.error(req.getRequestURI() + "============" + e.getMessage());
            e.printStackTrace();
            result = new HdMessageCode();
            result.setCode("-1");
            result.setKey("-1");
            result.setMessage(e.getMessage());
            return result;
        }
    }
}
