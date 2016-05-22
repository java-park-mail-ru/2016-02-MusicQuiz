package frontend;


import base.GameMechanics;
import base.WebSocketService;
import main.AccountService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import rest.UserAnswer;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

/**
 * Created by seven-teen on 17.05.16.
 */
public class GameWebSocket {

    private final long myId;
    @Nullable
    private Session session;
    @NotNull
    private final GameMechanics gameMechanics;
    @NotNull
    private final WebSocketService webSocketService;
    @NotNull
    private final AccountService accountService;
    @NotNull
    private int myScore;
    @NotNull
    private int opponentScore;

    public long getMyId() {
        return myId;
    }

    public GameWebSocket(long myId, @NotNull GameMechanics gameMechanics,
                         @NotNull WebSocketService webSocketService, @NotNull AccountService accountService) {
        this.myId = myId;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
        this.accountService = accountService;
    }

    public void sendFirstMes(long session_id, long track_id, Set<String> answers, long time) {
        try {
            final JSONObject jsonFirst = new JSONObject();
            jsonFirst.put("id_gamesession", session_id);
            jsonFirst.put("id_track", track_id);
            jsonFirst.put("answers", answers.toString());
            jsonFirst.put("time", time);
            if (session != null && session.isOpen()) {
                session.getRemote().sendString(jsonFirst.toString());
                session.close();
            }
        } catch (IOException | WebSocketException e) {
            e.printStackTrace();
        }
    }

    public void sendMes(String right_ans, long id_track, Set<String> answers) {
        try {
            final JSONObject jsonFirst = new JSONObject();
            jsonFirst.put("right_answer", right_ans);
            jsonFirst.put("id_track", id_track);
            jsonFirst.put("answers", answers.toString());
            if (session != null && session.isOpen()) {
                session.getRemote().sendString(jsonFirst.toString());
                session.close();
            }
        } catch (IOException | WebSocketException e) {
            e.printStackTrace();
        }
    }

    public void gameOver(boolean win, int points1, int points2) {
        try {
            final JSONObject jsonEndGame = new JSONObject();
            jsonEndGame.put("winner", win);
            jsonEndGame.put("your_points", points1);
            jsonEndGame.put("enemy_points", points2);
            if (session != null && session.isOpen()) {
                session.getRemote().sendString(jsonEndGame.toString());
                session.close();
            }
        } catch (IOException | WebSocketException e) {
            e.printStackTrace();
        }
    }

    public void sendError(String error) {
        try {
            final JSONObject jsonError = new JSONObject();
            jsonError.put("status", "error");
            jsonError.put("errorMessage", error);
            if (session != null && session.isOpen())
                session.getRemote().sendString(jsonError.toString());
        } catch (IOException | WebSocketException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketConnect
    public void onOpen(@NotNull Session session) {
        this.session = session;
        if (myId >= 0) {
            webSocketService.addUser(this);
            gameMechanics.addUser(myId);
        }
        else {
            sendError("unauthorized");
            session.close();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        final JSONObject getJson = new JSONObject(data);
        UserAnswer ans = new UserAnswer(getJson.getLong("id_gamesession"), getJson.getString("user_answer"));
        gameMechanics.choice(myId, ans);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        webSocketService.removeUser(this);
        gameMechanics.removeUser(myId);
    }
}
