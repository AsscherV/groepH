package be.kdg.groeph.service;



import be.kdg.groeph.model.TripUser;
import com.sun.jersey.api.client.Client;

import com.sun.jersey.api.client.WebResource;

import com.sun.jersey.api.client.config.DefaultClientConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.net.URI;


import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})

public class TestRest extends AbstractTransactionalJUnit4SpringContextTests {

    private final String password = "def";
    private final String username = "test@test.com";
    private final String passwordFalse = "ddaeraeef";
    private final String usernameFalse = "fdqsfdsqfd@test.com";
    private String baseURI;


    @Test
    public void loginTrue() {
        RestService restService = new RestService();
        String isValidUser = restService.login(username, password);
        assertEquals("Login moet test zijn", username, isValidUser);

    }

    @Test
    public void loginFalse() {
        RestService restService = new RestService();

        String isValidUser = restService.login(usernameFalse, passwordFalse);
        assertEquals("Login moet test zijn", usernameFalse, isValidUser);
    }

    @Test
    public void loginRest() {


        Client client = Client.create(new DefaultClientConfig());
        WebResource service = client.resource(getBaseURI());

        String response = service.path("rest").path("login").queryParam("Username","test").queryParam("Password","test").accept(MediaType.APPLICATION_JSON).get(String.class);

        assertEquals("result van RestCall moet test zijn", "test", response.toString());


    }

    public static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/groepH-1.0/api").build();
    }
}
