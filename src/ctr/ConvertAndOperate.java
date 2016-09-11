package ctr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author Arturh
 */
public class ConvertAndOperate {
    
    private int k;
    
    public ConvertAndOperate(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }
    
    public String toScientificNotation(double value) {
        
        String format = "0."; for (int i = 0; i < this.k; i++) format += "#"; format += "E0";
        
        DecimalFormat decimalFormat = new DecimalFormat(format);
        
        return decimalFormat.format(value);
        
    }
    
    public double truncate(double value) {
        
        double toReturn = new BigDecimal(
                String.valueOf(value)
        ).setScale(
                this.k, RoundingMode.DOWN
        ).stripTrailingZeros().doubleValue();
        
        return toReturn;
        
    }
    
    public double round(double value) {
        
        double result = value;
        double entero = Math.floor(value);
        
        result = (result - entero) * Math.pow(10, this.k);
        
        result = Math.round(result);
        
        result = (result / Math.pow(10, this.k)) + entero;
        
        return result;
    }
    
}
