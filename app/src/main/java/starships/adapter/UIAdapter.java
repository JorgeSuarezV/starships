package starships.adapter;

import edu.austral.ingsis.starships.ui.*;
import starships.colideables.Visitor;
import starships.colideables_state.InnerStateHandler;
import starships.collision.Collideable;
import starships.collision.CollisionHandler;
import starships.collision.Handler;
import starships.spawn.SpawnHandler;
import starships.spawn.StarshipInitializer;
import starships.state.GameState;

import java.util.Set;


public class UIAdapter {

    private final Visitor<ElementModel> adapterVisitor = new AdapterVisitor();
    private final StarshipInitializer starshipInitializer= new StarshipInitializer();
    private final Handler<Collision> collisionHandler = new CollisionHandler();
    private final Handler<TimePassed> spawnHandler = new SpawnHandler();
    private final Handler<TimePassed> innerStateHandler = new InnerStateHandler();



    public GameState initializeGame(Integer playerNumber){
        Set<Collideable> collideables = starshipInitializer.spawnStarships(playerNumber);
        return new GameState().addCollideables(collideables);
    }

    public GameState handle(Collision collision, GameState gameState){
        return collisionHandler.handle(collision, gameState);

    }

    public GameState handle(TimePassed timePassed, GameState gameState){
        return calculateUpdates(timePassed, gameState);
    }

    public GameState handle(OutOfBounds outOfBounds, GameState gameState){
        return gameState.removeCollideablesByIds(Set.of(outOfBounds.getId()));
    }

    public GameState handle(KeyPressed keyPressed, GameState gameState){
        return gameState.keyPressed(keyPressed.getKey());
    }

    public GameState handle(KeyReleased keyReleased, GameState gameState){
        return gameState.keyReleased(keyReleased.getKey());
    }

//    public void saveGame(String s) throws IOException {
//        FileManager.saveGame(s, collideableMap);
//    }

    public ElementModel transformResultToElementModel(Collideable collideables) {
        return collideables.accept(adapterVisitor);
    }
    private GameState calculateUpdates(TimePassed timePassed, GameState gameState) {
        GameState gameState2 = spawnHandler.handle(timePassed, gameState);
        return innerStateHandler.handle(timePassed, gameState2);
    }
}
