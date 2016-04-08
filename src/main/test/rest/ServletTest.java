package rest;

import main.AccountService;
import main.AccountServiceImpl;
import main.Context;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.Mockito.mock;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServletTest extends JerseyTest {

    private static String userID;

    @Override
    protected Application configure() {
        final Context context = new Context();
        context.put(AccountService.class, new AccountServiceImpl("Music_Quiz"));

        final ResourceConfig config = new ResourceConfig(Users.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        //noinspection AnonymousInnerClassMayBeStatic
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(context);
                bind(request).to(HttpServletRequest.class);
            }
        });

        return config;
    }

    @Before
    public void initialize() {
        Response resp = target("user").request().put(Entity.json("{\"login\":\"admin\",\"password\":\"admin\", \"email\":\"admin@email\"}"));
        String message = resp.readEntity(String.class);
        System.out.println(message);
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(message);
        int start = 0;
        while (matcher.find(start)) {
            userID = message.substring(matcher.start(), matcher.end());
            start = matcher.end();
        }
    }


    @Test
    public void testGetUser() {
        final Response resp = target("user").path(userID).request().get();
        System.out.print(resp.getStatus());
        if (resp.getStatus() == 200) {
            final String getResp = target("user").path(userID).request().get(String.class);
            String [] respString = getResp.split("\n");
            String email = "\t\"email\": \"admin@email\"";
            assertEquals(respString[3], email);
        }
        else fail();
    }

    @Test
    public void testGetUnrealUser() {
        final Response resp = target("user").path("0").request().get();
        assertEquals(401, resp.getStatus());
    }

    @Test
    public void testPutUser(){
        String login = "\t\"login\": \"tst\",";
        String password = "\t\"password\": \"tst\",";
        String email = "\t\"email\": \"tst@email\"";
        String testUser = '{' + login + password + email + '}';
        final Response resp = target("user").request().put(Entity.json(testUser));
        if(resp.getStatus() == 200){
            String message = resp.readEntity(String.class);
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(message);
            int start = 0;
            String value = "0";
            while (matcher.find(start)) {
                value = message.substring(matcher.start(), matcher.end());
                start = matcher.end();
            }
            final String getResp = target("user").path(value).request().get(String.class);
            String [] respString = getResp.split("\n");
            assertEquals(respString[3], email);
        }
        else fail();
    }

}
