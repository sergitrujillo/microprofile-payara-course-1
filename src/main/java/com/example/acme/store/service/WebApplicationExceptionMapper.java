package com.example.acme.store.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper
        implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(final WebApplicationException exception) {

        Error error = new Error();
        error.message = exception.getMessage();
        return Response
                .status(exception.getResponse().getStatus())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(error)
                .build();
    }

    public class Error {
        public String message;
    }

}