package starships.collision;

import edu.austral.ingsis.starships.ui.Collision;
import org.jetbrains.annotations.NotNull;
import starships.state.CollideableMap;
import starships.state.GameState;

import java.util.List;

public class CollisionHandler implements Handler<Collision>{


    @Override
    public GameState handle(Collision collision, GameState gameState){
        Collideable collideable1 = gameState.getCollideableMap().getColideable(collision.component1());
        Collideable collideable2 = gameState.getCollideableMap().getColideable(collision.component2());
        if (collideableNotFound(collideable1, collideable2)) return gameState;
        GameState gameState1 = collide(gameState, collideable1, collideable2);
        GameState gameState2 = collide(gameState1, collideable2, collideable1);
        if (gameEnded(gameState2.getCollideableMap())) gameOver(gameState2);
        return gameState2;
    }

    private boolean collideableNotFound(Collideable collideable1, Collideable collideable2) {
        return collideable1 == null || collideable2 == null;
    }

    @NotNull
    private GameState collide(GameState gameState, Collideable collideable1, Collideable collideable2) {
        CollisionResult result1 = collide(collideable1, collideable2);
        return calculateNewGameState(gameState, result1);
    }

    private GameState calculateNewGameState(GameState gameState, CollisionResult result1) {
        return gameState.addCollideables(result1.getCollideablesToAdd())
                .removeCollideables(result1.getCollideablesToRemove())
                .addPoints(result1.getPoints(), result1.getPlayerNumber());
    }

    private void gameOver(GameState gameState) {
        List<Integer> finalScores = gameState.getFinalScores();
        for (int i = 1; i <= finalScores.size(); i++) {
            System.out.println("Player " + i + ": " + finalScores.get(i-1));
        }
        System.exit(0);
    }

    private boolean gameEnded(CollideableMap collideableMap) {
        for (String s : collideableMap.getAcualCollideablesMap().keySet()) {
            if (s.startsWith("starship")) return false;
        }
        return true;
    }

    private CollisionResult collide(Collideable collideable1, Collideable collideable2) {
        return collideable1.accept(collideable2.getCollisionVisitor());
    }
}
