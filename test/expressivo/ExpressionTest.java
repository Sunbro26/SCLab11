/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

public class ExpressionTest {

    @Test
    public void testSimpleNumbers() {
        assertEquals("5", Expression.parse("5").toString());
        assertEquals("123", Expression.parse("123").toString());
    }

    @Test
    public void testParentheses() {
        assertEquals("(3 + 4)", Expression.parse("(3 + 4)").toString());
        assertEquals("(1 + (2 + 3))", Expression.parse("1 + (2 + 3)").toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidExpressionMissingOperand() {
        Expression.parse("3 +");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidExpressionUnmatchedParentheses() {
        Expression.parse("(3 + 4");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCharacters() {
        Expression.parse("3 + x"); // Variables are not supported in this grammar
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyInput() {
        Expression.parse("");
    }
    
    @Test
    public void testDifferentiationOfConstants() {
        assertEquals("0", Expression.parse("5").differentiate("x").toString());
    }

    @Test
    public void testDifferentiationOfVariable() {
        assertEquals("1", Expression.parse("x").differentiate("x").toString());
        assertEquals("0", Expression.parse("y").differentiate("x").toString());
    }

    @Test
    public void testDifferentiationOfAddition() {
        assertEquals("(1 + 0)", Expression.parse("x + 5").differentiate("x").toString());
    }

    @Test
    public void testDifferentiationOfMultiplication() {
        assertEquals("((1 * x) + (x * 1))", Expression.parse("x * x").differentiate("x").toString());
    }
}
