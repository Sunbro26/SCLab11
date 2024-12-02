package expressivo;

import java.util.Objects;

/**
 * Represents an addition operation between two expressions.
 * Immutable and preserves the order and structure of operands.
 */
public class Addition extends AbstractExpression {
    private final Expression left;
    private final Expression right;
    
    /**
     * Create a new Addition expression.
     * 
     * @param left the left operand
     * @param right the right operand
     * @throws NullPointerException if either operand is null
     */
    public Addition(Expression left, Expression right) {
        this.left = Objects.requireNonNull(left, "Left operand cannot be null");
        this.right = Objects.requireNonNull(right, "Right operand cannot be null");
        checkRep();
    }
    
    /**
     * Get the left operand.
     * 
     * @return the left expression
     */
    public Expression getLeft() {
        return left;
    }
    
    /**
     * Get the right operand.
     * 
     * @return the right expression
     */
    public Expression getRight() {
        return right;
    }
    
    @Override
    protected void checkRep() {
        assert left != null : "Left expression cannot be null";
        assert right != null : "Right expression cannot be null";
    }
    
    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Addition)) {
            return false;
        }
        Addition other = (Addition) obj;
        return this.left.equals(other.left) && 
               this.right.equals(other.right);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash("+", left, right);
    }
    
    @Override
    public Expression differentiate(String variable) {
        return new Addition(left.differentiate(variable), right.differentiate(variable));
    }
}
