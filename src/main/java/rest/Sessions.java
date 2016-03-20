package rest;

import main.AccountService;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isAuthenticated(@Context HttpServletRequest request, @Context HttpHeaders headers) {
        final String SessionID = request.getSession().getId();
        UserProfile user = accountService.getUserBySession(SessionID);
        if(user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        else {
            String jsonStr200 = "{ \"id\": " + user.getID() + " }";
            return Response.status(Response.Status.OK).entity(jsonStr200).build();
        }
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSession(UserProfile user, @Context HttpHeaders headers,  @Context HttpServletRequest request) {
        final String SessionID = request.getSession().getId();
        UserProfile currentUser = accountService.getUser(user.getID());
        if(currentUser != null && currentUser.getPassword().equals(user.getPassword())) {
            String jsonStr200 = "{ \"id\": " + user.getID() +" }";
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
