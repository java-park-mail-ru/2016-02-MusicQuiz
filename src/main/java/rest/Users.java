package rest;

import main.AccountService;

import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
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
    @Path("{id}")
    public Response getUserByID(@PathParam("id") Long id) {
        final UserProfile user = accountService.getUser(id);
        if(user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        else {
            String jsonStr =  "{ \"id\": " + user.getID() +", " +
                    "\"login\": \"" + user.getLogin() + "\", " +
                    "\"email\": \"" + user.getEmail() + "\" }";
            return Response.status(Response.Status.OK).entity(jsonStr).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserProfile user, @Context HttpHeaders headers){
        String jsonStr = "{ \"id\": " + user.getID() +" }";
        if(accountService.addUser(user)){
            return Response.status(Response.Status.OK).entity(jsonStr).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, UserProfile user,
                               @Context HttpHeaders headers, @Context HttpServletRequest request) {
        final String SessionID = request.getSession().getId();
        UserProfile updatingUser = accountService.getUserBySession(SessionID);
        if(updatingUser == null || !updatingUser.equals(user)) {
            String jsonStr403 = "{ " +
                    "\"status\": 403, " +
                    "\"message\": \"Чужой юзер\" }";
            return Response.status(Response.Status.FORBIDDEN).entity(jsonStr403).build();
        }
        else {
            String jsonStr200 = "{ \"id\": " + user.getID() +" }";
            accountService.updateUser(user, updatingUser);
            return Response.status(Response.Status.OK).entity(jsonStr200).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserByID(@PathParam("id") Long id, @Context HttpHeaders headers) {
        String jsonStr = "{ " +
                            "\"status\": 403, " +
                            "\"message\": \"Чужой юзер\"" +
                         " }";
        if(!accountService.deleteUser(id)) {
            return Response.status(Response.Status.OK).build();
        }
        else
            return Response.status(Response.Status.FORBIDDEN).entity(jsonStr).build();
    }
}
