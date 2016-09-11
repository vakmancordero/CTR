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
        
        String result = splited[0]; String exponent = splited[1];
        
        //String addition = "•10";
        String addition = "x10";
        
        if (exponent.length() > 1) {
            
            addition += "<sup>" 
                            + exponent.substring(1, exponent.length())
                     +  "<sup>";
        }
        
        return result + addition;
        
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
