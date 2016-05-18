package base;

import frontend.GameWebSocket;

/**
 * Created by seven-teen on 17.05.16.
 */
 public interface WebSocketService {

        void addUser(frontend.GameWebSocket user);

        void notifyMyNewScore(GameUser user);

        void notifyEnemyNewScore(GameUser user);

        void notifyStartGame(GameUser user);

        void notifyGameOver(GameUser user, boolean win);
 }
