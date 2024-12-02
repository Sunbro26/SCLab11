/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionParser;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    // Datatype definition
    // Expression = Number(value:double) 
    //            + Variable(name:String) 
    //            + Addition(left:Expression, right:Expression)
    //            + Multiplication(left:Expression, right:Expression)
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
	public static Expression parse(String input) {
        try {
            // Create a character stream from the input string
            ANTLRInputStream inputStream = new ANTLRInputStream(input);

            // Initialize the lexer
            ExpressionLexer lexer = new ExpressionLexer(inputStream);
            lexer.reportErrorsAsExceptions(); // Report errors as exceptions

            // Tokenize the input
            TokenStream tokens = new CommonTokenStream(lexer);

            // Initialize the parser
            ExpressionParser parser = new ExpressionParser(tokens);
            parser.reportErrorsAsExceptions();

            // Parse the input and construct the parse tree
            ParseTree tree = parser.root();

            // Build the AST from the parse tree
            return buildAST(tree);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Invalid expression: " + input, e);
        }
    }

    private static Expression buildAST(ParseTree tree) {
        if (tree instanceof ExpressionParser.RootContext rootContext) {
            return buildAST(rootContext.sum());
        } else if (tree instanceof ExpressionParser.SumContext sumContext) {
            Expression left = buildAST(sumContext.primitive(0));
            for (int i = 1; i < sumContext.primitive().size(); i++) {
                Expression right = buildAST(sumContext.primitive(i));
                left = new Addition(left, right);
            }
            return left;
        } else if (tree instanceof ExpressionParser.PrimitiveContext primitiveContext) {
            if (primitiveContext.NUMBER() != null) {
                return new Number(Double.parseDouble(primitiveContext.NUMBER().getText()));
            } else if (primitiveContext.sum() != null) {
                return buildAST(primitiveContext.sum());
            }
        }
        throw new IllegalArgumentException("Unexpected parse tree: " + tree.getText());
    }
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    Expression differentiate(String variable);
}