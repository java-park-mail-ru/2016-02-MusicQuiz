package main;

import frontend.GameWebSocket;
import frontend.GameWebSocketImpl;
import frontend.GameWebSocketServlet;
import mechanics.GameMechanicsImpl;
import mechanics.GameSession;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.Handler;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import rest.MusicStream;
import rest.ScoreBoard;
import rest.Sessions;
import rest.Users;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by IlyaRogov on 29.02.16.
 */
public class Main {

    private static int port = 0;

    @SuppressWarnings("OverlyBroadThrowsClause")
    public static void main(String[] args) throws Exception {
        loadProperties();

        final Server server = new Server(port);
        final ServletContextHandler contextHandler = new ServletContextHandler(server, "/api/", ServletContextHandler.SESSIONS);

        final Context context = new Context();
        context.put(AccountService.class, new AccountServiceImpl("hibernate.cfg.xml"));

        final GameWebSocket webSocketService = new GameWebSocketImpl();
        context.put(GameWebSocket.class, webSocketService);
        final GameSession gameMechanics = new GameMechanicsImpl(webSocketService, context.get(AccountService.class));
        context.put(GameSession.class, gameMechanics);

        contextHandler.addServlet(new ServletHolder(new GameWebSocketServlet(context)), "/gameplay");

        final ResourceConfig config = new ResourceConfig(MusicStream.class, Users.class, Sessions.class, ScoreBoard.class);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(context);
            }
        });

        final ServletHolder servletHolder = new ServletHolder(new ServletContainer(config));

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
        resourceHandler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resourceHandler, contextHandler });
        server.setHandler(handlers);

        contextHandler.addServlet(servletHolder, "/*");
        server.start();
        server.join();
    }

    private static void loadProperties() throws  IOException {
        try (final FileInputStream fis = new FileInputStream("src/main/resources/server.properties")) {
            final Properties properties = new Properties();
            properties.load(fis);
            port = Integer.parseInt(properties.getProperty("port"));
            System.out.println("host: " + properties.getProperty("host"));
            System.out.println("port: " + properties.getProperty("port"));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}