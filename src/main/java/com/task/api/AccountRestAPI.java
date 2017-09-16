package com.task.api;

import com.task.model.Account;
import com.task.service.AccountService;
import com.task.util.DataConflictException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/v1/accounts/account")
public class AccountRestAPI {

    @Autowired
    private AccountService accountService;

    @GET
    @Path("/{account_Id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam("account_Id") Integer account_Id) {
        Response response = Response.status(200)
                .entity(accountService.getAccount(account_Id))
                .build();
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createAccount(Account account) {
        accountService.createAccount(account);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccount() {
        Response response = Response.status(200)
                .entity(accountService.getAllAccount())
                .build();
        return response;
    }

    @PATCH
    @Path("/{account_Id}")
    @Consumes("application/json-patch+json")
    public void updateAccount(@PathParam("account_Id") Integer account_Id, Account account) {
        //Account id Mismatch.
        if (!account.getAccount_id().equals(account_Id)) {
            throw new DataConflictException("Account id Mismatch ");
        }
        //Account with account_id not fount.
        accountService.getAccount(account_Id);

        account.setAccount_id(account_Id);
        accountService.updateAccount(account);
    }

}
