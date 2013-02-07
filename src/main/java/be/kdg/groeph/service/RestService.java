package be.kdg.groeph.service;



import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * Created with IntelliJ IDEA.
 * User: Frederik
 * Date: 6/02/13
 * Time: 11:12
 * To change this template use File | Settings | File Templates.
 */

@Path("/rest")
public class RestService {


    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)

    public String login(@QueryParam("Username") String Username,
                        @QueryParam("Password") String Password) {

        return Username;


    }
}
