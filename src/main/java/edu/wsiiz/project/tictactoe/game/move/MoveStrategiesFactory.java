package edu.wsiiz.project.tictactoe.game.move;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class MoveStrategiesFactory {

    private Map<MoveStrategyName, MoveStrategy> strategies;
    @Autowired
    public MoveStrategiesFactory(Set<MoveStrategy> moveStrategySet) {
        createStrategy(moveStrategySet);
    }

    public MoveStrategy findStrategy(MoveStrategyName strategyName) {
        return strategies.get(strategyName);
    }
    private void createStrategy(Set<MoveStrategy> strategySet) {
        strategies = new HashMap<>();
        strategySet.forEach(moveStrategy ->strategies.put(moveStrategy.getMoveStrategyName(), moveStrategy));
    }
}
