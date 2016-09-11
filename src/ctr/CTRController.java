package ctr;

import java.net.URL;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initCB();
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

            double first = Double.parseDouble(getValue(secondTF).toString());
            double second = Double.parseDouble(getValue(firstTF).toString());
            
            Result result = this.operate(first, second, operation, method, k);

            new Alert(
                    Alert.AlertType.INFORMATION, 
                    "El resultado real es: " + result.getReal() + 
                    "\n\nEl resultado no real es: " + result.getNoReal(),
                    ButtonType.OK
            ).showAndWait();
            
            new Alert(
                    Alert.AlertType.INFORMATION, 
                    "Error absoluto: " + result.getAbsoluteError() + 
                    "\n\nError relativo: " + result.getRelativeError(),
                    ButtonType.OK
            ).showAndWait();
            
            
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
        
        ConvertAndOperate co_op = new ConvertAndOperate(k);
            
        switch (operation.toLowerCase()) {

            case "suma":
                
                result.setReal(first + second);
                
                if (method.equalsIgnoreCase("truncamiento"))
                    result.setNoReal(
                            co_op.truncate(
                                    co_op.truncate(first) + co_op.truncate(second) 
                            )
                    );
                else 
                    result.setNoReal(
                            co_op.truncate(
                                    co_op.round(first) + co_op.round(second) 
                            )
                    );
                
                break;

            case "resta":
                
                if (method.equalsIgnoreCase("truncamiento"))
                    result.setNoReal(
                            co_op.truncate(
                                    co_op.truncate(first) - co_op.truncate(second) 
                            )
                    );
                else 
                    result.setNoReal(
                            co_op.truncate(
                                    co_op.round(first) - co_op.round(second) 
                            )
                    );
                
                break;

            case "multiplicacion":
                
                if (method.equalsIgnoreCase("truncamiento"))
                    result.setNoReal(
                            co_op.truncate(
                                    co_op.truncate(first) * co_op.truncate(second) 
                            )
                    );
                else 
                    result.setNoReal(
                            co_op.truncate(
                                    co_op.round(first) * co_op.round(second) 
                            )
                    );
                
                break;

            case "division":
                
                if (method.equalsIgnoreCase("truncamiento"))
                    result.setNoReal(
                            co_op.truncate(
                                    co_op.truncate(first) / co_op.truncate(second) 
                            )
                    );
                else 
                    result.setNoReal(
                            co_op.truncate(
                                    co_op.round(first) / co_op.round(second) 
                            )
                    );
                
                break;

            default:

                System.out.println("try again..");
        }
        
        return result;
        
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
