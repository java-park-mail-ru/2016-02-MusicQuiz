package rest;

import database.UsersDataSet;
import database.UsersDAO;
import main.AccountService;
import org.hibernate.HibernateException;
import org.jboss.logging.Param;

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
@Singleton
@Path("/scores")
public class ScoreBoard {
    @Inject
    private main.Context context;

    @GET
    public Response getTop(@QueryParam("limit") Long id) {
        final AccountService accountService = context.get(AccountService.class);
        Collection<UsersDataSet> users = accountService.getAllUsers();
        List<UsersDataSet> list= new ArrayList<UsersDataSet>(users);
        Collections.sort(list);
        int i=0;
        String jsonStr = "{ \n\t";
        for (Iterator<UsersDataSet> iterator=list.iterator(); iterator.hasNext() && (i<id); i++) {
            UsersDataSet user = iterator.next();
            jsonStr += "{ \n\t\t\"login\": " + user.getLogin() + ", " +
                    "\n\t\t\"points\": \"" + user.getPoints() + "\"\n\t}";
            if (iterator.hasNext())
                jsonStr += ",\n\t";
        }
        jsonStr += "\n}";
        return Response.status(Response.Status.OK).entity(jsonStr).build();
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
