package be.kdg.groeph.service;

import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.TripUser;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Service("restService")
@Transactional
@Path("/rest")
public class RestService {
    @Autowired
    LoginService loginService;

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@QueryParam("Username") String Username,
                        @QueryParam("Password") String Password) {


        /*

        Deze versie werkt nog niet met de back-end
        */
        Gson gson = new Gson();


        TripUser user = loginService.loginUser(Username,Password);
        if(user.isNull()){
              return "";
        }   else {
            return gson.toJson(user);
        }

        /*
        Gson gson = new Gson();
        String response =  gson.toJson(new TripUser(Username,Password,new Date(2009,12,11),"1535464654", 'M',Username,Password,new Address("hiestraat","18","BE 2555","BCity"),new Date(2013,02,19),"MEMBER"));

        return response ;
        */


    }
}
