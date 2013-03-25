package be.kdg.groeph.service;

import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    @Autowired
    TripService tripService;
    @Autowired
    CostService costService;
    @Autowired
    ParticipantsService participantsService;
    @Autowired
    UserService userService;
    @Autowired
    AccessoryService accessoryService;
    @Autowired
    WaypointService waypointService;

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@QueryParam("Username") String Username,
                        @QueryParam("Password") String Password) {
        Gson gson = new GsonBuilder().create();

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

    @GET
    @Path("/publicTrips")
    public String getAllPublicTrips() {
        JSONSerializer serializer = new JSONSerializer();
        return serializer.serialize(tripService.fetchAllPublicTrips());
    }

    @GET
    @Path("/organizedTrips")
    public String getAllOrganizedTrips(@QueryParam("tripUserEmail") String Email) {
        JSONSerializer serializer = new JSONSerializer();
        TripUser user = userService.getUserByEmail(Email);
        return serializer.serialize(tripService.getAllCreatedTripsByUser(user));
    }

    @GET
    @Path("/participatingTrips")
    public String getAllParticipatingTrips(@QueryParam("tripUserEmail") String Email){
        JSONSerializer serializer = new JSONSerializer();
        TripUser user = userService.getUserByEmail(Email);
        return serializer.serialize(tripService.getAllParticipatedTripsByUser(user));
    }

    @GET
    @Path("/getParticipantsByTrip")
    public String getAllParticipantsByTrip(@QueryParam("tripId") String tripId){
        JSONSerializer serializer = new JSONSerializer();
        Trip trip = tripService.getTripById(Integer.parseInt(tripId));
        return serializer.serialize(tripService.getParticipantsByTrip(trip));
    }

    @GET
    @Path("/getAccessoriesByTrip")
    public String getAllAccessoriesByTrip(@QueryParam("tripId") String tripId){
        JSONSerializer serializer = new JSONSerializer();
        Trip trip = tripService.getTripById(Integer.parseInt(tripId));
        return serializer.serialize(accessoryService.getAccessoriesByTrip(trip));
    }

    @GET
    @Path("/getCostsByTrip")
    public String getCostsByTrip(@QueryParam("tripId") String tripId){
        JSONSerializer serializer = new JSONSerializer();
        Trip trip = tripService.getTripById(Integer.parseInt(tripId));
        return serializer.serialize( costService.getCostsByTrip(trip));
    }
    @GET
    @Path("/getCostsByTripAndTripUser")
    public String getCostsByTripAndTripUser(@QueryParam("tripId") String tripId, @QueryParam("tripUserId") String tripUserId){
        JSONSerializer serializer = new JSONSerializer();
        Trip trip = tripService.getTripById(Integer.parseInt(tripId));
        TripUser tripUser = userService.getUserByEmail(tripUserId);
        return serializer.serialize(costService.getCostByTripByUser(trip,tripUser));
    }

    @GET
    @Path("/getCostForAUserByTripAndUser")
    public String getCostForAUserByTripAndUser(@QueryParam("tripId") String tripId, @QueryParam("tripUserId") String tripUserId){
        JSONSerializer serializer = new JSONSerializer();
        Trip trip = tripService.getTripById(Integer.parseInt(tripId));
        TripUser tripUser = userService.getUserByEmail(tripUserId);
        return serializer.serialize(costService.getCostForAUserByTripAndUser(trip,tripUser));
    }

    @GET
    @Path("/getAverageCostForATrip")
    public String getAverageCostForATrip(@QueryParam("tripId") String tripId){
        JSONSerializer serializer = new JSONSerializer();
        Trip trip = tripService.getTripById(Integer.parseInt(tripId));
        return serializer.serialize(costService.getAverageCostByTrip(trip));
    }

    @GET
    @Path("/getTotalCostForATrip")
    public String getTotalCostForATrip(@QueryParam("tripId") String tripId){
        JSONSerializer serializer = new JSONSerializer();
        Trip trip = tripService.getTripById(Integer.parseInt(tripId));
        return serializer.serialize(costService.getTotalCostByTrip(trip));
    }

    @GET
    @Path("/getWaypointsByTripId")
    public String getWaypointsByTripId(@QueryParam("tripId")String id) {
        JSONSerializer serializer = new JSONSerializer();
        Trip trip = tripService.getTripById(Integer.parseInt(id));

        return serializer.serialize(waypointService.getWaypointsByTrip(trip));
    }
}
