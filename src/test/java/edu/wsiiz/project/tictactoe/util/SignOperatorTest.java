package edu.wsiiz.project.tictactoe.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignOperatorTest {

    @Test
    public void testGetCharSign() {
        // Test the getCharSign method with different input values
        assertEquals('X', SignOperator.getCharSign(Sign.X));
        assertEquals('O', SignOperator.getCharSign(Sign.O));
        assertEquals(' ', SignOperator.getCharSign(Sign.SPACE));
    }

    @Test
    public void testGetIntSign() {
        // Test the getIntSign method with different input values
        assertEquals(88, SignOperator.getIntSign(Sign.X));
        assertEquals(79, SignOperator.getIntSign(Sign.O));
        assertEquals(32, SignOperator.getIntSign(Sign.SPACE));
    }

    @Test
    public void testGetOpponentSign() {
        // Test the getOpponentSign method with different input values
        assertEquals(Sign.O, SignOperator.getOpponentSign(Sign.X));
        assertEquals(Sign.X, SignOperator.getOpponentSign(Sign.O));
    }

}