package rest;

import main.AccountService;


import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Created by IlyaRogov on 29.02.16.
 */
@Singleton
@Path("/user")
public class Users {
    @SuppressWarnings("unused")
    @Inject
    private main.Context context;


    @GET
    @Path("{id}")
    public Response getUserByID(@PathParam("id") Long id) {
        final AccountService accountService = context.get(AccountService.class);
        final UserProfile user = accountService.getUser(id);
        if(user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{}\n").build();
        }
        else {
            String jsonStr =  "{ \n\t\"id\": " + user.getID() +", " +
                    "\n\t\"login\": \"" + user.getLogin() + "\", " +
                    "\n\t\"email\": \"" + user.getEmail() + "\"\n}\n";
            return Response.status(Response.Status.OK).entity(jsonStr).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserProfile user){
        final AccountService accountService = context.get(AccountService.class);
        UserProfile currentUser = new UserProfile(user);
        String jsonStr = "{ \n\t\"id\": " + currentUser.getID() +"\n}\n";
        if(accountService.addUser(currentUser)){
            return Response.status(Response.Status.OK).entity(jsonStr).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("{}\n").build();
        }
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, UserProfile user, @Context HttpServletRequest request) {
        //создается новая сессия для каждого запроса. TODO
        final AccountService accountService = context.get(AccountService.class);
        final String SessionID = request.getSession().getId();
        UserProfile currentUser = accountService.getUserBySession(SessionID);
        if(currentUser == null || !((currentUser.getID()) == id)) {
            String jsonStr403 = "{ " +
                    "\n\t\"status\": 403, " +
                    "\n\t\"message\": \"Чужой юзер\" \n}\n";
            return Response.status(Response.Status.FORBIDDEN).entity(jsonStr403).build();
        }
        else {
            String jsonStr200 = "{\n\t\"id\": " + user.getID() +"\n}\n";
            accountService.updateUser(user, currentUser);
            return Response.status(Response.Status.OK).entity(jsonStr200).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserByID(@PathParam("id") Long id, @Context HttpServletRequest request) {
        //Аналогично. TODO
        final AccountService accountService = context.get(AccountService.class);
        final String SessionID = request.getSession().getId();
        UserProfile currentUser = accountService.getUserBySession(SessionID);
        if(currentUser != null && (currentUser.getID())==id){
            accountService.deleteUser(id);
            return Response.status(Response.Status.OK).entity("{}\n").build();
        }
        else {
            String jsonStr = "{ " +
                    "\n\t\"status\": 403," +
                    "\n\t\"message \": \"Чужой юзер\"" +
                    "\n}\n";
            return Response.status(Response.Status.FORBIDDEN).entity(jsonStr).build();
        }
    }
}
