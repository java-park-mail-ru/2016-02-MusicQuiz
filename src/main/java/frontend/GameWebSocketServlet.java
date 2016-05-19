package frontend;

import main.Context;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

/**
 * Created by user on 19.05.16.
 */
@WebServlet(name = "GameWebSocketServlet", urlPatterns = {"/gameplay"})
public class GameWebSocketServlet extends WebSocketServlet{
    @Inject
    private final Context context;

    public GameWebSocketServlet(Context context) {
        this.context = context;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.setCreator(new GameWebSocketCreator(context));
    }
}
