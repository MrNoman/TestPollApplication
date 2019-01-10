package com.testTaskPoll.util;

import com.testTaskPoll.PollApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Ajax {
    private static final Logger LOG = LoggerFactory.getLogger(PollApplication.class);

    public static Map<String, Object> successResponse(Object object) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("result", "success");
        response.put("data", object);
        LOG.info("Ajax returned success response: " + response);
        return response;
    }

    public static Map<String, Object> emptyResponse() {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("result", "success");
        LOG.info("Ajax returned empty response: " + response);
        return response;
    }

    public static Map<String, Object> errorResponse(String errorMessage) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("result", "error");
        response.put("message", errorMessage);
        LOG.error("Ajax returned error response: " + response);
        return response;
    }
}
