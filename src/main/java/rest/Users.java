package rest;

import main.AccountService;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by IlyaRogov on 29.02.16.
 */
@Singleton
@Path("/user")
public class Users {
    private AccountService accountService;

    public Users(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        final Collection<UserProfile> allUsers = accountService.getAllUsers();
        return Response.status(Response.Status.OK).entity(allUsers.toArray(new UserProfile[allUsers.size()])).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByName(@PathParam("id") Long id) {
        final UserProfile user = accountService.getUserByID(id);
        if(user == null){
            return Response.status(Response.Status.FORBIDDEN).build();
        }else {
            return Response.status(Response.Status.OK).entity(user).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserProfile user, @Context HttpHeaders headers){
        if(accountService.addUser(user)){
            return Response.status(Response.Status.OK).entity(user.getLogin()).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    /*@PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, UserProfile user) {

    }*/

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id, @Context HttpHeaders headers) {
        if(accountService.deleteUser(id)) {
            return Response.status(Response.Status.OK).build();
        }
        else
            return Response.status(Response.Status.FORBIDDEN).build();
    }
}