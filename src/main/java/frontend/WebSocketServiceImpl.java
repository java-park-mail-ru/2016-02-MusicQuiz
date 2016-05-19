package frontend;

import base.GameUser;
import base.WebSocketService;
import main.AccountService;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 19.05.16.
 */
public class WebSocketServiceImpl implements WebSocketService {
    private final Map<Long, GameWebSocket> userSockets = new HashMap<>();

    @Override
    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyId(), user);
    }

    @Override
    public void removeUser(GameWebSocket user) {
        userSockets.remove(user.getMyId());
    }

    @Override
    public void notifyMyNewScore(GameUser user) {
        userSockets.get(user.getMyId()).setMyScore(0);
    }

    @Override
    public void notifyEnemyNewScore(GameUser user) {
        userSockets.get(user.getMyId()).setOpponentScore(0);
    }

    @Override
    public void notifyStartGame(GameUser user) {
        //TODO
    }

    @Override
    public void notifyGameOver(GameUser user, boolean win) {
        //TODO
    }
}
