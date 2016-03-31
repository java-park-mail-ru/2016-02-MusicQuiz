package rest;

import main.AccountService;
import main.AccountServiceImpl;
import main.Context;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.security.auth.login.Configuration;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ServletTest extends JerseyTest {

    @Override
    protected Application configure() {
        final Context context = new Context();
        context.put(AccountService.class, new AccountServiceImpl());

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
        target("user").request().put(Entity.json("{\"login\":\"admin\",\"password\":\"admin\", \"email\":\"admin@email\"}"));
        target("user").request().put(Entity.json("{\"login\":\"guest\",\"password\":\"guest\", \"email\":\"guest@email\"}"));
    }

    @Test
    public void testGetAdminUser() {
        final String adminJson = target("user").path("0").request().get(String.class);
        assertEquals("{ \n\t\"id\": 0, \n\t\"login\": \"admin\", \n\t\"email\": \"admin@email\"\n}\n", adminJson);
    }

    @Test
    public void testGetNewUser() {
        final String adminJson = target("user").path("2").request().get(String.class);
        assertEquals("{}\n", adminJson);
    }
}
