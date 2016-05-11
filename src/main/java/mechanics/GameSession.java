package mechanics;

import database.MusicDAO;
import database.MusicDataSet;
import database.UsersDataSet;
import main.AccountService;
import main.AccountServiceImpl;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by user on 21.04.16.
 */
public class GameSession {
    @SuppressWarnings("unused")
    @Inject
    private main.Context context;

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private final long gameSessionID;
    private final long startTime;
    private final long endTime;
    @NotNull
    private final UsersDataSet firstPlayer;
    private int firstPoints=0;
    @NotNull
    private final UsersDataSet secondPlayer;
    private int secondPoints=0;
    private ArrayList<MusicDataSet> tracks = new ArrayList<MusicDataSet>(5);

    public GameSession(@NotNull UsersDataSet firstPlayer, @NotNull UsersDataSet secondPlayer) {
        this.gameSessionID = ID_GENERATOR.getAndIncrement();
        this.startTime = Clock.systemDefaultZone().millis();
        this.endTime = this.startTime+60000;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        final AccountService accountService = context.get(AccountService.class);
        this.tracks = accountService.getTracks();
    }

    public long getSessionTime() {
        return Clock.systemDefaultZone().millis() - startTime;
    }


}
