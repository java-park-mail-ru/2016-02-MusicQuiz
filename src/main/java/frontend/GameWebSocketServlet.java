package frontend;

import main.Context;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.inject.Inject;

/**
 * Created by user on 19.05.16.
 */
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
