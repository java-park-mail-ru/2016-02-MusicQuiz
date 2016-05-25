package base;

import frontend.GameWebSocket;

import java.util.Set;

/**
 * Created by seven-teen on 17.05.16.
 */
public interface WebSocketService {

    void addUser(GameWebSocket user);

    void removeUser(GameWebSocket user);

    void notifyStartGame(GameUser user, Long session_id, Long track_id, Set<String> answers, Long time);

    void notifyAnswer(GameUser user, String right_ans, Long id_track, Set<String> answers);

    void notifyGameOver(GameUser user, Boolean win, Integer points1, Integer points2);

    void notifyError(GameUser user, String error);
}
