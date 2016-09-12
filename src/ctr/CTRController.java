package ctr;

import ctr.utils.ConvertAndOperate;
import ctr.utils.Result;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 *
 * @author VakSF
 */
public class CTRController implements Initializable {
    
    @FXML
    private TextField firstTF, secondTF, kTF;
    
    @FXML
    private ComboBox<String> operationCB, methodCB;
    
    private ConvertAndOperate co_op;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initCB();
        this.co_op = new ConvertAndOperate();
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
        
        if (check(kTF, operationCB, methodCB, firstTF, secondTF)) {
            
            String operation = getValue(operationCB).toString();
            
            String method = getValue(methodCB).toString();
            
            int k = Integer.parseInt(getValue(kTF).toString());

            double first = Double.parseDouble(getValue(firstTF).toString());
            double second = Double.parseDouble(getValue(secondTF).toString());
            
            Result result = this.operate(first, second, operation, method, k);
            
            new Alert(
                    Alert.AlertType.INFORMATION, 
                    "El resultado real es: " + co_op.toScientificNotation(
                            result.getReal().doubleValue()
                    ) + 
                    "\n\nEl resultado no real es: " + co_op.toScientificNotation(
                            result.getNoReal().doubleValue()
                    ),
                    ButtonType.OK
            ).showAndWait();
            
            /*
            if (this.setClipboard(result.getReal().toString())) {
                
                new Alert(
                        Alert.AlertType.INFORMATION, 
                        "Se ha copiado el valor real de la "
                      + "operación al portapapeles",
                        ButtonType.OK
                ).showAndWait();
                
            }
            */            

            try {
                
                new Alert(
                        Alert.AlertType.INFORMATION, 
                        "Error absoluto: " + co_op.toScientificNotation(
                                result.getAbsoluteError().doubleValue()
                        ) + 
                        "\n\nError relativo: " + co_op.toScientificNotation(
                                result.getRelativeErr().doubleValue()
                        ),
                        ButtonType.OK
                ).showAndWait();
                
            } catch (NumberFormatException ex) {
                
                new Alert(Alert.AlertType.ERROR, "Sin resultados", ButtonType.OK).showAndWait();
            }
            
        } else {
            
            new Alert(
                    Alert.AlertType.ERROR, 
                    "Complete los campos faltantes", 
                    ButtonType.OK
            ).show();
            
        }
        
    }
    
    private Result operate(double first, double second, String operation, String method, int k) {
        
        Result result = new Result();
        
        co_op.setK(k);
        
        System.out.println(operation.toLowerCase());
        
        switch (operation.toLowerCase()) {
            
            case "suma":
                
                result.setReal(BigDecimal.valueOf(first + second));
                
                if (method.equalsIgnoreCase("truncamiento"))
                    result.setNoReal((
                            BigDecimal.valueOf(co_op.truncate(first) + co_op.truncate(second)) 
                    ));
                else 
                    result.setNoReal((
                            BigDecimal.valueOf(co_op.round(first) + co_op.round(second)) 
                    ));
                
                break;

            case "resta":
                
                System.out.println(first);
                System.out.println(second);
                
                result.setReal(BigDecimal.valueOf(first - second));
                
                if (method.equalsIgnoreCase("truncamiento"))
                    result.setNoReal((
                            BigDecimal.valueOf(co_op.truncate(first) - co_op.truncate(second)) 
                    ));
                else 
                    result.setNoReal((
                            BigDecimal.valueOf(co_op.round(first) - co_op.round(second)) 
                    ));
                
                break;

            case "multiplicacion":
                
                result.setReal(BigDecimal.valueOf(first * second));
                
                if (method.equalsIgnoreCase("truncamiento"))
                    result.setNoReal((
                            BigDecimal.valueOf(co_op.truncate(first) * co_op.truncate(second)) 
                    ));
                else 
                    result.setNoReal((
                            BigDecimal.valueOf(co_op.round(first) * co_op.round(second)) 
                    ));
                
                break;

            case "division":
                
                System.out.println(first  + "/" + second);
                
                result.setReal(BigDecimal.valueOf(first / second));
                
                if (method.equalsIgnoreCase("truncamiento"))
                    result.setNoReal((
                            BigDecimal.valueOf(co_op.truncate(first) / co_op.truncate(second)) 
                    ));
                else 
                    result.setNoReal((
                            BigDecimal.valueOf(co_op.round(first) / co_op.round(second)) 
                    ));
                
                break;

            default:

                System.out.println("try again..");
        }
        
        return result;
        
    }
    
    private boolean setClipboard(String text) {
        
        Clipboard clipboard = Clipboard.getSystemClipboard();
        
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        
        return clipboard.setContent(content);
        
    }
    
    private boolean check(Object... controls) {
        
        for (Object control : controls) {
            
            String className = control.getClass().getSimpleName();
            
            if (className.equals("TextField")) {
                
                if (((TextField)control).getText().isEmpty()) {
                    return false;
                }
                
            } else if (className.equals("ComboBox")) {
                
                if (((ComboBox)control).getSelectionModel().getSelectedIndex() == -1) {
                    return false;
                }

            } else if (true) {
                
                System.out.println("Another control");
                
            }
            
        }
        
        return true;
        
    }
    
    private Object getValue(Object control) {
        
        String className = control.getClass().getSimpleName();
        
        if (className.equals("TextField")) {
            
            return ((TextField)control).getText();
            
        } else if (className.equals("ComboBox")) {
            
            return ((ComboBox<String>)control).getSelectionModel().getSelectedItem();
            
        } else if (true) {
            
            System.out.println("Another control");
            
        }
        
        return null;
        
    }
    
}
