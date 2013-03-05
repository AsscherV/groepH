package be.kdg.groeph.service;

import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.TripUser;
import com.google.gson.Gson;
import flexjson.JSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

//@Transactional
//@Service("restService")
@Component
@Path("/rest")
public class RestService {
    @Autowired
    LoginService loginService;

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@QueryParam("Username") String Username,
                        @QueryParam("Password") String Password) {
        Gson gson = new Gson();

        TripUser user = loginService.loginUser(Username,Password);

        if(user.isNull()){
              return "";
        }   else {
            JSONSerializer serializer = new JSONSerializer();
            //return gson.toJson(user);
            //return serializer.include("trips").serialize(user);
            return serializer.serialize(user);
            //return gson.toJson(user, TripUser.class);
        }
    }
}
