package expressivo;

import java.util.Objects;

/**
 * Represents a numeric constant in an expression.
 * Immutable and comparable by numeric value.
 */
public class Number extends AbstractExpression {
    private final double value;
    
    /**
     * Create a new Number expression.
     * 
     * @param value the numeric value
     */
    public Number(double value) {
        this.value = value;
        checkRep();
    }
    
    /**
     * Get the numeric value of this expression.
     * 
     * @return the numeric value
     */
    public double getValue() {
        return value;
    }
    
    @Override
    protected void checkRep() {
        // Ensure value is a valid number (not NaN or infinite)
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }
    }
    
    @Override
    public String toString() {
        // Remove .0 for whole numbers to improve readability
        return value == (long) value ? 
            String.valueOf((long) value) : 
            String.valueOf(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Number)) {
            return false;
        }
        Number other = (Number) obj;
        return Double.compare(this.value, other.value) == 0;
    }
    
    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
    
    @Override
    public Expression differentiate(String variable) {
        return new Number(0); // Derivative of a constant is 0
    }
}
