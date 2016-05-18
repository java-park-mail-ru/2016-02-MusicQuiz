package frontend;


import base.GameMechanics;
import base.WebSocketService;
import main.AccountService;
import org.eclipse.jetty.websocket.api.Session;
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

    public GameWebSocket(long myId, @NotNull GameMechanics gameMechanics,
                         @NotNull WebSocketService webSocketService, @NotNull AccountService accountService) {
        this.myId = myId;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
        this.accountService = accountService;
    }
}
