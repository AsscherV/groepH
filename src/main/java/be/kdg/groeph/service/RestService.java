package be.kdg.groeph.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
