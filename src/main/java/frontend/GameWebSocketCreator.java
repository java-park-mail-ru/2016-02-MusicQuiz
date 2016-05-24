package frontend;

import base.GameMechanics;
import base.WebSocketService;
import database.UsersDataSet;
import main.AccountService;
import main.Context;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by user on 19.05.16.
 */
public class GameWebSocketCreator implements WebSocketCreator {
    private final AccountService accountService;
    private final GameMechanics gameMechanics;
    private final WebSocketService webSocketService;

    public GameWebSocketCreator(Context context) {
        this.accountService = context.get(AccountService.class);
        this.gameMechanics = context.get(GameMechanics.class);
        this.webSocketService = context.get(WebSocketService.class);
    }

    @Override
    public GameWebSocket createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        final String sessionId = req.getHttpServletRequest().getSession().getId();
        long id;
        UsersDataSet  user = accountService.getUserBySession(sessionId);
        if(user == null)
            id = -1;
        else
            id = user.getID();
        return new GameWebSocket(id, gameMechanics, webSocketService, accountService);
    }
}
