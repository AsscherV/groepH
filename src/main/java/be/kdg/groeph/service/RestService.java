package be.kdg.groeph.service;

import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.TripUser;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/rest")
public class RestService {


    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@QueryParam("Username") String Username,
                        @QueryParam("Password") String Password) {

        Gson gson = new Gson();
        String response =  gson.toJson(new TripUser(Username,Password,new Date(2009,12,11),"1535464654", 'M',Username,Password,new Address("hiestraat","18","BE 2555","BCity"),new Date(2013,02,19),"MEMBER"));

        return response ;



    }
}
