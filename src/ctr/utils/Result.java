package ctr.utils;

import java.math.BigDecimal;

/**
 *
 * @author Arturh
 */
public class Result {
    
    private BigDecimal real;
    private BigDecimal noReal;

    public Result() {
    }
    
    public Result(BigDecimal real, BigDecimal noReal) {
        this.real = real;
        this.noReal = noReal;
    }

    public BigDecimal getReal() {
        return real;
    }

    public void setReal(BigDecimal real) {
        this.real = real;
    }

    public BigDecimal getNoReal() {
        return noReal;
    }

    public void setNoReal(BigDecimal noReal) {
        this.noReal = noReal;
    }
    
    public BigDecimal getAbsoluteError() {
        return this.real.subtract(this.noReal).abs();
    } 
    
    public BigDecimal getRelativeErr() {
        return BigDecimal.valueOf(getAbsoluteError().doubleValue() / real.doubleValue());
    }
    
}
