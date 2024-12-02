package expressivo;

/**
 * Abstract base class for Expression implementations to provide common functionality
 * and ensure immutability and structural equality.
 */
abstract class AbstractExpression implements Expression {
    
    /**
     * Checks rep invariant for the expression.
     * Subclasses must override to provide specific rep invariant checks.
     * 
     * @throws RuntimeException if rep invariant is violated
     */
    protected abstract void checkRep();
    
    @Override
    public abstract int hashCode();
    
    @Override
    public abstract boolean equals(Object obj);
    
    @Override
    public abstract String toString();
}
