package rest;

import database.UsersDataSet;
import main.AccountService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by IlyaRogov on 01.03.16.
 */

@Singleton
@Path("/session")
public class Sessions {
    @SuppressWarnings("unused")
    @Inject
    private main.Context context;



    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isAuthenticated(@Context HttpServletRequest request) {
        final AccountService accountService = context.get(AccountService.class);
        final String sessionID = Helper.getCookie(request);
        if(sessionID != null && accountService.isAuthorized(sessionID)) {
            UsersDataSet user = accountService.getUserBySession(sessionID);
            if(user != null) {
                String jsonStr200 = "{\n\t\"id\": " + user.getID() + "\n}\n";
                return Response.status(Response.Status.OK).entity(jsonStr200).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSession(UsersDataSet user, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        final AccountService accountService = context.get(AccountService.class);
        final String sessionID = request.getSession().getId();
        UsersDataSet currentUser = accountService.getUserByEmail(user.getEmail());
        if(currentUser != null && currentUser.getPassword().equals(user.getPassword())) {
            String jsonStr200 = "{\n\t\"id\": " + currentUser.getID() +"\n}\n";
            accountService.logIn(sessionID, currentUser);
            Cookie cookie = new Cookie("MusicQuiz", sessionID);
            response.addCookie(cookie);
            return Response.status(Response.Status.OK).entity(jsonStr200).build();
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).entity("{}\n").build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSession(@Context HttpServletRequest request) {
        final String sessionID = Helper.getCookie(request);
        final AccountService accountService = context.get(AccountService.class);
        if(sessionID != null && accountService.isAuthorized(sessionID)) {
            accountService.logOut(sessionID);
        }
        return Response.status(Response.Status.OK).entity("{}\n").build();
    }
}
