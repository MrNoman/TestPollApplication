package com.testTaskPoll.util;

import com.testTaskPoll.PollApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExceptionHandlerController {

    private static final Logger LOG = LoggerFactory.getLogger(PollApplication.class);

    @ExceptionHandler(RestException.class)
    public @ResponseBody
    String handleException(RestException e) {
        LOG.error("Error: " + e.getMessage());
        return "Error: " + e.getMessage();
    }
}
