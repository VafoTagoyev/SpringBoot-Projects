package com.whitehacker.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPagesController implements ErrorController {

    @RequestMapping(value="/error")
    public String errorHandler(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {

            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                return "error-pages/400-error";
            }

            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error-pages/403-error";
            }

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-pages/404-error";
            }

            if (statusCode == HttpStatus.REQUEST_TIMEOUT.value()) {
                return "error-pages/408-error";
            }

            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-pages/500-error";
            }

            if (statusCode == HttpStatus.BAD_GATEWAY.value()) {
                return "error-pages/502-error";
            }

            if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
                return "error-pages/503-error";
            }

            if (statusCode == HttpStatus.GATEWAY_TIMEOUT.value()) {
                return "error-pages/504-error";
            }
        }

        return "error/general-error-page.html";
    }

//    @Override
    public String getErrorPath() {
        return "/error";
    }
}