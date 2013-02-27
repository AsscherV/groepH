package be.kdg.groeph.service;


import be.kdg.groeph.bean.RegisterBean;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.util.SHAEncryption;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;

import com.sun.jersey.api.client.WebResource;

import com.sun.jersey.api.client.config.DefaultClientConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.faces.bean.ManagedProperty;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.lang.reflect.Type;
import java.net.URI;


import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestRest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    UserDao userDao;

    @ManagedProperty(value = "#{restService}")
    @Autowired
    RestService restService;

    TripUser user;

    private String baseURI;

    @Before
    public void init() {
        user = UserMother.validUser3WithOutEncryptedPassword();
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        userDao.addUser(user);
    }

    @Test
    public void loginTrue() {
        TripUser object = getTripUser(user.getEmail(), user.getPassword());
        assertEquals("Login moet test zijn", user.getEmail(), object.getEmail());
    }

    @Test
    public void loginFalse() {
        TripUser object = getTripUser(user.getEmail(), user.getPassword());
        assertEquals("Email must be the same", user.getEmail(), object.getEmail());
    }


    @Test
    public void loginRest() {
        //Client client = Client.create(new DefaultClientConfig());
        //WebResource service = client.resource(getBaseURI());
        //String isValidUser = service.path("rest").path("login").queryParam("Username", user.getEmail()).queryParam("Password", user.getPassword()).accept(MediaType.APPLICATION_JSON).get(String.class);
        //System.out.println("teststring: " + isValidUser);
        //TripUser object = getTripUserFromResponse(isValidUser);
        //assertEquals("result van RestCall moet test zijn", user.getEmail(), object.getEmail());


        System.out.println("User: " + user.getEmail() + " Password:" + user.getPassword());
        String isValidUser2 = restService.login(user.getEmail(), user.getPassword());
        System.out.println("Json String: " + isValidUser2);
        TripUser object = getTripUserFromResponse(isValidUser2);
        System.out.println("Object Email: " + object.getEmail());

        assertEquals("result van RestCall moet test zijn", user.getEmail(), object.getEmail());

        TripUser tu = getTripUserFromResponse(isValidUser2);
        assertEquals("Emails need to be equal", user.getEmail(), tu.getEmail());
        System.out.println("User pass: " + user.getPassword());
        System.out.println("TU pass: " + tu.getPassword());

    }
   /*

   //TODO: add the serializing
    @Test
    public void loginRest2(){

        //WERKT MET ECHTE DATABANK
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI());

        //pppppppppp changed
        user.setPassword("g");    //GfFCsBjzB7/fHHAJ0VopQXyW2GeNKYLuvOSWGy5n7rEYqOux11twCHw+Zbx5NFDj/koQACvvotA45a7UeWk38g==
        user.setEmail("gunther.laurijssens@student.kdg.be");
        String isValidUser = service.path("rest").path("login").queryParam("Username", user.getEmail()).queryParam("Password", user.getPassword()).accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println("teststring: " + isValidUser);
        TripUser object = getTripUserFromResponse(isValidUser);
        assertEquals("result van RestCall moet test zijn", user.getEmail(), object.getEmail());
    }
    */

    /*
    @Test
    public void loginRest2(){

        //WERKT MET ECHTE DATABANK
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI());

        //pppppppppp changed
        user.setPassword("g");
        user.setEmail("gunther.laurijssens@student.kdg.be");

        JsonSerializer


        String isValidUser = service.path("rest").path("login").queryParam("Username", user.getEmail()).queryParam("Password", user.getPassword()).accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println("teststring: " + isValidUser);
        TripUser object = getTripUserFromResponse(isValidUser);
        assertEquals("result van RestCall moet test zijn", user.getEmail(), object.getEmail());
    }    */


    private TripUser getTripUserFromResponse(String validUser) {
        Gson gson = new GsonBuilder().create();
        Type rootType = new TypeToken<TripUser>() {
        }.getType();

        TripUser object = gson.fromJson(validUser, rootType);
        if (object == null) {
            throw new JsonSyntaxException("Error while parsing data from JSON: " + validUser);
        }
        return object;
    }

    private TripUser getTripUser(String UName, String Password) {
        String isValidUser = restService.login(UName, Password);
        return getTripUserFromResponse(isValidUser);
    }

    public static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/groepH-1.0/api").build();
    }


}
