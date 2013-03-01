package be.kdg.groeph.service;


import be.kdg.groeph.bean.RegisterBean;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.util.SHAEncryption;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;

import com.sun.jersey.api.client.WebResource;

import com.sun.jersey.api.client.config.DefaultClientConfig;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import static org.junit.Assert.assertTrue;

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
    public void dummyTest(){
        assertTrue(true);
    }

    /*
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



     //TODO: UNCOMMENT TO TEST THE REST SERVLET!!!
    @Test
    public void loginRest(){
        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI());
         //GEBRUIKT ECHTE DATABANK : DUS ZELF USER AANMAKEN DIE HIERONDER STAAT
        //TODO User aanmaken email: guntherlaurijssens@gmail.com  password: g
        user.setPassword("g");
        user.setEmail("guntherlaurijssens@gmail.com");

        String isValidUser = service.path("rest").path("login").queryParam("Username", user.getEmail()).queryParam("Password", user.getPassword()).accept(MediaType.APPLICATION_JSON).get(String.class);
        TripUser Tuser = getTripUserFromResponse(isValidUser);

        assertEquals("result van RestCall moet test zijn", user.getEmail(), Tuser.getEmail());
    }
     */


    private TripUser getTripUserFromResponse(String restValidUser) {
        TripUser Tuser = new JSONDeserializer<TripUser>().deserialize(restValidUser);
        if (Tuser == null) {
            throw new JsonSyntaxException("Error while parsing data from JSON: " + restValidUser);
        }
        return Tuser;
    }

    private TripUser getTripUser(String UName, String Password) {
        String restValidUser = restService.login(UName, Password);
        return getTripUserFromResponse(restValidUser);
    }

    public static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/groepH-1.0/api").build();
    }


}
