package starships.state;

import java.util.HashMap;
import java.util.Map;

public class ScoreService {

    private final Map<Integer, Integer> scores;

    public ScoreService(Map<Integer, Integer> scores) {
        this.scores = scores;
    }

    ScoreService addPoints(Integer playerNumber, Integer points) {
        Map<Integer, Integer> newScores = new HashMap<>(scores);
        if (!scores.containsKey(playerNumber)) newScores.put(playerNumber, points);
        else newScores.put(playerNumber, points + scores.get(playerNumber));
        return new ScoreService(newScores);
    }

    Integer getPlayerScore(Integer playerNumber) {
        if (scores.get(playerNumber) == null) return 0;
        return scores.get(playerNumber);
    }

    Integer getPlayerQuantity() {
        return scores.keySet().size();
    }
}
