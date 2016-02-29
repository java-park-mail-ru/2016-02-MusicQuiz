package main;

import rest.Users;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IlyaRogov on 29.02.16.
 */

@ApplicationPath("api")
public class RestApplication extends Application {
    @Override
    public Set<Object> getSingletons() {
        final HashSet<Object> objects = new HashSet<>();
        objects.add(new Users(new AccountService()));
        return objects;
    }
}
