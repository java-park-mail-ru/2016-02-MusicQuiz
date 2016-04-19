package rest;

import database.MusicDataSet;
import main.AccountService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by seven-teen on 19.04.16.
 */

@Singleton
@Path("/music")
public class MusicStream {

    @SuppressWarnings("unused")
    @Inject
    private main.Context context;

    @GET
    @Produces("audio/mpeg")
    @Path("{id}")
    public Response getStream(@PathParam("id") Long id) throws IOException {

        MusicDataSet track = context.get(AccountService.class).getTrack(id);
        if(track == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        String path = track.getPath();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            return Response.status(Response.Status.OK).entity(fis).build();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        finally {
            fis.close();
        }
    }
}
