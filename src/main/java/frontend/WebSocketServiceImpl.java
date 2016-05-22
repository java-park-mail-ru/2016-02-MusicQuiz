package frontend;

import base.GameUser;
import base.WebSocketService;
import main.AccountService;
import org.jetbrains.annotations.NotNull;

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
        userSockets.put(user.getMyId(), user);
    }

    @Override
    public void removeUser(GameWebSocket user) {
        userSockets.remove(user.getMyId());
    }

    @Override
    public void answerUser(GameUser user, String ans) {

    }

    @Override
    public void notifyStartGame(GameUser user, long session_id, long track_id, Set<String> answers, long time) {
        userSockets.get(user.getMyId()).sendFirstMes(session_id, track_id, answers, time);
    }

    @Override
    public void notifyAnswer(GameUser user, String right_ans, long id_track, Set<String> answers) {
        userSockets.get(user.getMyId()).sendMes(right_ans, id_track, answers);
    }

    @Override
    public void notifyGameOver(GameUser user, boolean win, int points1, int points2) {
        userSockets.get(user.getMyId()).gameOver(win, points1, points2);
        userSockets.remove(user.getMyId());
    }

    @Override
    public void notifyError(GameUser user, String error) {
        userSockets.get(user.getMyId()).sendError(error);
    }
}
