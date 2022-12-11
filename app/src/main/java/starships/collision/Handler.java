package starships.collision;

import starships.state.GameState;

public interface Handler<K> {

    GameState handle(K event, GameState gameState);
}
