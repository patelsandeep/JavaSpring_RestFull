package com.task.util;

import com.task.model.ErrorMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author marembo (marembo2008@gmail.com)
 * @since Sep 14, 2017, 2:25:42 PM
 */
@Provider
public class DataConflictMapper implements ExceptionMapper<DataConflictException> {

    @Override
    public Response toResponse(DataConflictException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), 409);
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return Response.status(Response.Status.CONFLICT)
                .entity(errorMessage)
                .build();
    }

}
