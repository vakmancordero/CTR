package ctr;

/**
 *
 * @author Arturh
 */
public class Result {
    
    private double real;
    private double noReal;

    public Result() {
    }
    
    public Result(double real, double noReal) {
        this.real = real;
        this.noReal = noReal;
    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        System.out.println(real);
        this.real = real;
    }

    public double getNoReal() {
        return noReal;
    }

    public void setNoReal(double noReal) {
        this.noReal = noReal;
    }
    
    public double getAbsoluteError() {
        return Math.abs(this.real - this.noReal);
    } 
    
    public double getRelativeError() {
        return getAbsoluteError() / this.real;
    }
    
}
