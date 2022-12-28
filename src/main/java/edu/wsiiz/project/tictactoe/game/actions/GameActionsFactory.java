package edu.wsiiz.project.tictactoe.game.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class GameActionsFactory {

    private Map<GameActionName, GameStrategy> strategies;
    @Autowired
    public GameActionsFactory(Set<GameStrategy> gameStrategySet) {
        createStrategy(gameStrategySet);
    }

    public GameStrategy findStrategy(GameActionName strategyName) {
        return strategies.get(strategyName);
    }
    private void createStrategy(Set<GameStrategy> strategySet) {
        strategies = new HashMap<>();
        strategySet.forEach(gameStrategy -> strategies.put(gameStrategy.getActionName(), gameStrategy));
    }
}
