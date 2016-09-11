package ctr.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author Arturh
 */
public class ConvertAndOperate {
    
    private int k;

    public ConvertAndOperate() {
    }
    
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
        
        String myFormat = "0."; for (int i = 0; i < this.k; i++) myFormat += "#"; myFormat += "E0";
        
        String[] splited = new DecimalFormat(myFormat).format(value).split("E");
        
        return splited[0] + "x10^" + splited[1];
        
    }
    
    public double truncate(double value) {
        
        return new BigDecimal(
                String.valueOf(value)
        ).setScale(
                this.k, RoundingMode.DOWN
        ).stripTrailingZeros().doubleValue();
        
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
