package rest;

import main.AccountService;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by IlyaRogov on 01.03.16.
 */

@Singleton
@Path("/session")
public class Sessions {

    private AccountService accountService;

    public Sessions(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSessions() {
        final Collection<UserProfile> allSessions = accountService.getAllUsers();
        return Response.status(Response.Status.OK)./*entity(allSessions.toArray(new UserProfile[allSessions.size()])).*/build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isAuthenticated(UserProfile user, @Context HttpServletRequest request, @Context HttpHeaders headers) {
        final String SessionID = request.getSession().getId();
        String jsonStr200 = "{ \"id\": " + user.getID() +" }";
        if(accountService.isAuthenticated(SessionID, user)) {
            return Response.status(Response.Status.OK).build();
        }
        else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSession(UserProfile user, @Context HttpServletRequest request) {
        final String SessionID = request.getSession().getId();
        UserProfile currentUser = accountService.getUser(user.getID());
        String jsonStr200 = "{ \"id\": " + user.getID() +" }";
        if(currentUser != null && currentUser.getPassword().equals(user.getPassword())) {
            accountService.logIn(SessionID, user);
            return Response.status(Response.Status.OK).entity(jsonStr200).build();
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSession(@Context HttpServletRequest request) {
        final String SessionID = request.getSession().getId();
        accountService.logOut(SessionID);
        return Response.status(Response.Status.OK).build();
    }
}
