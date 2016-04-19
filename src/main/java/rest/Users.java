package rest;

import database.UsersDataSet;
import main.AccountService;
import org.jetbrains.annotations.Nullable;


import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.servlet.http.Cookie;


/**
 * Created by IlyaRogov on 29.02.16.
 */
@Singleton
@Path("/user")
public class Users {
    @SuppressWarnings("unused")
    @Inject
    private main.Context context;

    @Nullable
    private static String getCookie(HttpServletRequest request){
        String sessionID = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("MusicQuiz")) {
                    sessionID = cookie.getValue();
                    break;
                }
            }
        }
        return sessionID;
    }

    @GET
    @Path("{id}")
    public Response getUserByID(@PathParam("id") Long id) {
        final AccountService accountService = context.get(AccountService.class);
        final UsersDataSet user = accountService.getUser(id);
        if(user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{}\n").build();
        }
        else {

            String jsonStr =  "{ \n\t\"id\": " + user.getID() +", " +
                    "\n\t\"login\": \"" + user.getLogin() + "\", " +
                    "\n\t\"email\": \"" + user.getEmail() + "\", " +
                    "\n\t\"points\": \"" + user.getPoints() + "\"\n}\n";
            return Response.status(Response.Status.OK).entity(jsonStr).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UsersDataSet user){
        if(user.getEmail().isEmpty() || user.getLogin().isEmpty() || user.getPassword().isEmpty()){
            return Response.status(Response.Status.FORBIDDEN).entity("{}\n").build();
        }
        final AccountService accountService = context.get(AccountService.class);
        UsersDataSet currentUser = new UsersDataSet(user);
        if(accountService.addUser(currentUser)){
            String jsonStr = "{ \n\t\"id\": " + currentUser.getID() +"\n}\n";
            return Response.status(Response.Status.OK).entity(jsonStr).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("{}\n").build();
        }
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, UsersDataSet user, @Context HttpServletRequest request) {
        final AccountService accountService = context.get(AccountService.class);
        final String sessionID = getCookie(request);
        if(sessionID == null || !accountService.isAuthorized(sessionID)) {
            String jsonStr403 = "{ " +
                    "\n\t\"status\": 403, " +
                    "\n\t\"message\": \"Чужой юзер\" \n}\n";
            return Response.status(Response.Status.FORBIDDEN).entity(jsonStr403).build();
        }
        else {
            UsersDataSet currentUser = accountService.getUserBySession(sessionID);
            if(currentUser != null){
                accountService.updateUser(currentUser, user);
                String jsonStr200 = "{\n\t\"id\": " + currentUser.getID() +"\n}\n";
                return Response.status(Response.Status.OK).entity(jsonStr200).build();
            }
            return Response.status(Response.Status.OK).entity("").build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserByID(@PathParam("id") Long id, @Context HttpServletRequest request) {
        final AccountService accountService = context.get(AccountService.class);
        final String sessionID = getCookie(request);
        if(sessionID != null && accountService.isAuthorized(sessionID)) {
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
