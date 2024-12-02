package expressivo;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a variable in an expression.
 * Immutable and case-sensitive.
 */
public class Variable extends AbstractExpression {
    private final String name;
    
    /**
     * Create a new Variable expression.
     * 
     * @param name the variable name (must be a non-empty string of letters)
     * @throws IllegalArgumentException if name is invalid
     */
    public Variable(String name) {
        if (name == null || !isValidVariableName(name)) {
            throw new IllegalArgumentException("Invalid variable name: " + name);
        }
        this.name = name;
        checkRep();
    }
    
    /**
     * Get the variable name.
     * 
     * @return the variable name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Check if a string is a valid variable name.
     * 
     * @param str the string to check
     * @return true if the string is a valid variable name
     */
    private static boolean isValidVariableName(String str) {
        return Pattern.matches("^[a-zA-Z]+$", str);
    }
    
    @Override
    protected void checkRep() {
        assert name != null : "Variable name cannot be null";
        assert isValidVariableName(name) : "Invalid variable name";
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Variable)) {
            return false;
        }
        Variable other = (Variable) obj;
        return this.name.equals(other.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public Expression differentiate(String variable) {
        if (this.name.equals(variable)) {
            return new Number(1); // Derivative of the variable with respect to itself is 1
        } else {
            return new Number(0); // Otherwise, 0
        }
    }
}
