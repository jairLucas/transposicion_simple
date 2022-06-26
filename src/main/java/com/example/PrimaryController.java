package com.example;

import java.util.ResourceBundle;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class PrimaryController implements Initializable{

    @FXML
    private TextArea textAreaSalida;
    @FXML
    private TextArea textAreaEntrada;
    
    private String texto;

    @FXML
    private void encriptar(ActionEvent event){
        //obtener datos del primer text area
        texto = textAreaEntrada.getText();
        //encriptar segun el algoritmo
        //poner el texto en el segundo textArea
        textAreaSalida.appendText(texto);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        textAreaEntrada.setWrapText(true);
        textAreaSalida.setWrapText(true);
        textAreaSalida.setEditable(false); 
    }
}
