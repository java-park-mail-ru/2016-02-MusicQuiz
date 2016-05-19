package frontend;


import base.GameMechanics;
import base.WebSocketService;
import main.AccountService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public GameWebSocket(long myId, @NotNull GameMechanics gameMechanics,
                         @NotNull WebSocketService webSocketService, @NotNull AccountService accountService) {
        this.myId = myId;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
        this.accountService = accountService;
    }

    @OnWebSocketConnect
    public void onOpen(@NotNull Session session) {
        this.session = session;
        webSocketService.addUser(this);
        gameMechanics.addUser(myId);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        webSocketService.removeUser(this);
        gameMechanics.removeUser(myId);
    }

    public long getMyId() {
        return myId;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }
}
