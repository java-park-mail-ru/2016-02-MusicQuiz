package mechanics;

import base.GameMechanics;
import base.WebSocketService;
import main.AccountService;
import org.jetbrains.annotations.NotNull;

/**
 * Created by user on 19.05.16.
 */
public class GameMechanicsImpl implements GameMechanics{
    @NotNull
    private final WebSocketService webSocketService;

    @NotNull
    private final AccountService accountService;

    public GameMechanicsImpl(@NotNull WebSocketService webSocketService, @NotNull AccountService accountService) {
        this.webSocketService = webSocketService;
        this.accountService = accountService;
    }

    @Override
    public void addUser(@NotNull String user) {

    }

    @Override
    public void incrementScore(@NotNull String userName) {

    }

    @Override
    public void run() {

    }
}
