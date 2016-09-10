package ctr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author VakSF
 */
public class CTRController implements Initializable {
    
    @FXML
    private TextField firstTF, secondTF, kTF;
    
    @FXML
    private ComboBox<String> operationCB, methodCB;
    
    private int k;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initCB();
        this.k = 5;
    }
    
    private String toScientificNotation(double value) {
        
        String format = "0."; for (int i = 0; i < this.k; i++) format += "#"; format += "E0";
        
        DecimalFormat decimalFormat = new DecimalFormat(format);
        
        return decimalFormat.format(value);
        
    }
    
    private double truncate(double value) {
        
        double toReturn = new BigDecimal(
                String.valueOf(value)
        ).setScale(
                this.k, RoundingMode.DOWN
        ).stripTrailingZeros().doubleValue();
        
        return toReturn;
        
    }
    
    private double round(double value) {
        
        double result = value;
        double entero = Math.floor(value);
        
        result = (result - entero) * Math.pow(10, this.k);
        
        result = Math.round(result);
        
        result = (result / Math.pow(10, this.k)) + entero;
        
        return result;
    }
    
    private void initCB() {
        
        this.operationCB.getItems().addAll(
                "Suma",
                "Resta",
                "Multiplicacion",
                "Division"
        );
        
        this.methodCB.getItems().addAll(
                "Truncamiento",
                "Redondeo"
        );
        
        this.operationCB.getSelectionModel().selectFirst();
        this.methodCB.getSelectionModel().selectFirst();
        
    }
    
    @FXML
    private void calculate() {
        
        String kSt = this.kTF.getText();
        
        if (!kSt.isEmpty()) {
            
            this.k = Integer.parseInt(kSt);
            
        }
        
        String operation = this.operationCB.getSelectionModel().getSelectedItem();
        String method = this.methodCB.getSelectionModel().getSelectedItem();
        
        double first = Double.parseDouble(firstTF.getText());
        double second = Double.parseDouble(secondTF.getText());
        
        double no_real = 0;
        double real = 0;
        
        switch (operation.toLowerCase()) {
            
            case "suma":
                
                real = first + second;
                
                if (method.equalsIgnoreCase("truncamiento")) {
                    
                    no_real = truncate(truncate(first) + truncate(second));
                    
                } else {
                    
                    no_real = truncate(round(first) + round(second));
                    
                }
                
                break;
                
            case "resta":
                
                real = first - second;
                
                if (method.equalsIgnoreCase("truncamiento")) {
                    
                    no_real = truncate(truncate(first) - truncate(second));
                    
                } else {
                    
                    no_real = truncate(round(first) - round(second));
                    
                }
                
                break;
                
            case "multiplicacion":
                
                real = first * second;
                
                if (method.equalsIgnoreCase("truncamiento")) {
                    
                    no_real = truncate(truncate(first) * truncate(second));
                    
                } else {
                    
                    no_real = truncate(round(first) * round(second));
                    
                }
                
                break;
            
            case "division":
                
                real = first / second;
                
                if (method.equalsIgnoreCase("truncamiento")) {
                    
                    no_real = truncate(truncate(first) / truncate(second));
                    
                } else {
                    
                    no_real = truncate(round(first) / round(second));
                    
                }
                
                break;
            default:
                System.out.println("Opcion inválida");
        }
        
        double error_abosluto = Math.abs(real - no_real);
        double error_relativo = error_abosluto / Math.abs(real);
        
        new Alert(
                Alert.AlertType.INFORMATION, 
                "El resultado real es: " + real + 
                "\nEl resultado no real es: " + no_real + 
                "\nError absoluto: " + (error_abosluto) + 
                "\nError relativo: " + (error_relativo),
                ButtonType.OK
        ).showAndWait();
        
    }
    
}
