package rest;

import database.UsersDataSet;
import database.UsersDAO;
import main.AccountService;
import main.Context;
import org.hibernate.HibernateException;
import org.jboss.logging.Param;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.*;

import static java.util.Collections.sort;

/**
 * Created by user on 18.05.16.
 */
//TODO
@Singleton
@Path("/scores")
public class ScoreBoard {
    @Inject
    private Context context;

    @GET
    public Response getTop(@QueryParam("limit") int id) {
        if (id == 0)
            id = 10;
        final AccountService accountService = context.get(AccountService.class);
        List<UsersDataSet> users = accountService.getTopUsers(id);
        final JSONArray jsonArray = new JSONArray();
        for (UsersDataSet user : users) {
            JSONObject json = new JSONObject();
            json.put("login", user.getLogin());
            json.put("points", user.getPoints());
            jsonArray.put(json);
        }
        return Response.status(Response.Status.OK).entity(jsonArray.toString()).build();
    }

    @GET
    @Path("{id}")
    public Response getScoreByID(@PathParam("id") Long id) {
        final AccountService accountService = context.get(AccountService.class);
        try {
            final UsersDataSet user = accountService.getUser(id);
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("{}\n").build();
            } else {
                String jsonStr = "{ \n\t\"login\": " + user.getLogin() + ", " +
                        "\n\t\"points\": \"" + user.getPoints() + "\"\n}\n";
                return Response.status(Response.Status.OK).entity(jsonStr).build();
            }
        }
        catch(HibernateException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{}\n").build();
        }
    }
}
