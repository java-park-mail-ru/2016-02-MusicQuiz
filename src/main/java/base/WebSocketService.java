package base;

import frontend.GameWebSocket;

import java.util.Set;

/**
 * Created by seven-teen on 17.05.16.
 */
public interface WebSocketService {

    void addUser(GameWebSocket user);

    void removeUser(GameWebSocket user);

    void answerUser(GameUser user, String ans);

    void notifyStartGame(GameUser user, long session_id, long track_id, Set<String> answers, long time);

    void notifyAnswer(GameUser user, String right_ans, long id_track, Set<String> answers);

    void notifyGameOver(GameUser user, boolean win, int points1, int points2);

    void notifyError(GameUser user, String error);
}
