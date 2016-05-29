package frontend;

import base.GameUser;
import base.WebSocketService;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 19.05.16.
 */
public class WebSocketServiceImpl implements WebSocketService {
    private final Map<Long, GameWebSocket> userSockets = new HashMap<>();

    @Override
    public void addUser(GameWebSocket user) {
        System.out.println("user was added in websocketservice!!!!    !" + user.getMyId());
        userSockets.put(user.getMyId(), user);
    }

    @Override
    public void removeUser(GameWebSocket user) {
        userSockets.remove(user.getMyId());
    }

    @Override
    public void notifyStartGame(GameUser user, Long sessionId, Long trackId, Set<String> answers, Long time) {
        System.out.println(user.getMyId() + "   SEND FIRST : " + userSockets.get(user.getMyId()));
        userSockets.get(user.getMyId()).sendFirstMes(sessionId, trackId, answers, time);
    }

    @Override
    public void notifyAnswer(GameUser user, String rightAns, Long idTrack, Set<String> answers) {
        userSockets.get(user.getMyId()).sendMes(rightAns, idTrack, answers);
    }

    @Override
    public void notifyGameOver(GameUser user, Boolean win, Integer points1, Integer points2) {
        userSockets.get(user.getMyId()).gameOver(win, points1, points2);
        userSockets.remove(user.getMyId());
    }

    @Override
    public void notifyError(GameUser user, String error) {
        userSockets.get(user.getMyId()).sendError(error);
    }
}
