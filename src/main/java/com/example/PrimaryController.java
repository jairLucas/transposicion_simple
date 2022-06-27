package com.example;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable{

    @FXML
    private TextArea c_entrada;
    @FXML
    private TextArea c_salida;
    @FXML
    private TextArea d_salida;
    @FXML
    private TextArea d_entrada;
    @FXML
    private TextField c_clave;
    @FXML
    private TextField d_clave;
    
    private String texto_cifrar;
    private String clave_cifrar;
    private String texto_descifrar;
    private String clave_descifrar;
    

    @FXML
    private void encriptar(ActionEvent event){
        //obtener datos del primer text area
        c_salida.setText("");
        texto_cifrar = c_entrada.getText();
        clave_cifrar = c_clave.getText();
        //encriptar segun el algoritmo
        texto_cifrar = preprocesar(texto_cifrar);
        clave_cifrar = preprocesar(clave_cifrar);
        String[][] matriz = insertar_to_matriz(texto_cifrar, clave_cifrar);
        HashMap<String,String> hashmap = convertir_matriz_to_diccionario(matriz);
        String cadena_cifrada = transponer_columnas(hashmap, clave_cifrar);
        cadena_cifrada = cadena_cifrada.replaceAll("\\*", "");
        //poner el texto en el segundo textArea
        c_salida.appendText(cadena_cifrada);
    }


    @FXML
    private void desencriptar(ActionEvent event){
        //obtener datos del primer text area
        d_salida.setText("");
        texto_descifrar = d_entrada.getText();
        clave_descifrar = d_clave.getText();
        //encriptar segun el algoritmo
        clave_descifrar = preprocesar(clave_descifrar);
        HashMap<String, String> hashmap = obtener_hashmap_descifrado(texto_descifrar, clave_descifrar);
        String resultado = transponer_columnas_descifrar(hashmap, clave_descifrar);
        resultado = obtener_mensaje_descifrado(resultado, clave_descifrar);
        resultado = resultado.replaceAll("\\*", "");
        //poner el texto en el segundo textArea
        d_salida.appendText(resultado);
    }

    public String obtener_mensaje_descifrado(String txt, String clave){
        String resultado = "";
        txt = preprocesar(txt);
        txt = preparar_texto_descifrar(txt, clave);
        System.out.println(txt);
        char c;
        int salto = get_altura_matriz(txt, clave)-1;
        int temp = 0;
        for(int i=0; i<salto; i++){
            temp = i;
            for(int j=0; j<clave.length(); j++){
                c = txt.charAt(temp);
                resultado += Character.toString(c);
                temp = temp+salto; 
            } 
        }
        return resultado;
    }

    public String preparar_texto_descifrar(String txt, String clave){
        String cadena_arreglada = preprocesar(txt);
        int tamh = clave.length();
        int tamv = get_altura_matriz(txt, clave);
        int tam_matriz = (tamh*tamv)-clave.length();
        if(tam_matriz>txt.length()){
            int diferencia = tam_matriz-txt.length();
            for(int i=0; i<diferencia; i++){
                cadena_arreglada += "*";
            }
        }
        return cadena_arreglada;
    }

    public String transponer_columnas_descifrar(HashMap<String,String> hashmap, String clave){
        String resultado = "";
        String temp = "";
        char c;
        for(int i=0; i<clave.length(); i++){
            c = clave.charAt(i);
            temp = Character.toString(c);
            temp = hashmap.get(temp);
            resultado += temp;
            temp = "";
        }
        return resultado;
    }

    public HashMap<String,String> obtener_hashmap_descifrado(String txt, String clave){
        String[] txt_arr = txt.split(" ");
        String temp = "";
        char c;
        HashMap<String, String> hashmap = new HashMap<String, String>();
        clave = transponer_clave(clave);
        for(int i=0; i<clave.length(); i++){
            c = clave.charAt(i); 
            temp = Character.toString(c);
            temp = hashmap.put(temp, txt_arr[i]); 
            temp = "";
        }
        System.out.println(hashmap);
        return hashmap;
    }

    public String preprocesar(String txt){
        txt = txt.replaceAll(" ", "");
        txt = txt.toUpperCase();
        return txt;
    }

    public String transponer_columnas(HashMap<String,String> hashmap, String clave){
        String resultado = "";
        String temp = "";
        char c;
        clave = transponer_clave(clave);
        for(int i=0; i<clave.length(); i++){
            c = clave.charAt(i);
            temp = Character.toString(c);
            temp = hashmap.get(temp); 
            resultado += temp + " ";
            temp = "";
        }
        return resultado;
    }

    public String transponer_clave(String clave){
        String resultado = "";
        char[] arr_clave = clave.toCharArray();
        Arrays.sort(arr_clave);
        resultado = new String(arr_clave);
        return resultado;
    }

    public HashMap<String, String> convertir_matriz_to_diccionario(String [][] matriz){
        HashMap<String,String> hashmap = new HashMap<String, String>();
        String temp = "";
        for(int i=0; i<matriz[0].length; i++){
            for(int j=0; j<matriz.length; j++){
                if(j!=0){
                    temp += matriz[j][i]; 
                }
            }
            hashmap.put(matriz[0][i], temp);
            temp = "";
        }
        return hashmap;
    }

    public String completar_cadena(String txt, String clave){
        String cadena_arreglada = clave+txt;
        int longitud_total = txt.length()+clave.length();
        int tamh = clave.length();
        int tamv = get_altura_matriz(txt, clave);
        int tam_matriz = tamh*tamv;
        if(tam_matriz>longitud_total){
            int diferencia = tam_matriz-longitud_total;
            for(int i=0; i<diferencia; i++){
                cadena_arreglada += "*";
            }
        }
        return cadena_arreglada;
    }

    public String [][] insertar_to_matriz(String txt, String clave){
        int tamh = clave.length();
        int tamv = get_altura_matriz(txt, clave);
        String clave_txt = completar_cadena(txt, clave);
        String [][] matriz = new String[tamv][tamh];
        int k = 0;
        for(int i=0; i<tamv; i++){
            for (int j=0; j<tamh; j++){
                matriz[i][j] = String.valueOf(clave_txt.charAt(k));
                k++;
            }   
        }  
        return matriz;
    }

    public void printh(String[][] matriz){
        for(int i=0; i<matriz.length; i++){
            for(int j=0; j<matriz[i].length; j++){
                System.out.print(matriz[i][j]);
            }
            System.out.println(" ");
        }
    }

    public void printv(String[][] matriz){
        for(int i=0; i<matriz[0].length; i++){
            for(int j=0; j<matriz.length; j++){
                System.out.print(matriz[j][i]);
            }
            System.out.println(" ");
        }     
    }

    /**
     * Calcula la altura de la matriz
     * @param txt
     * @param clave
     * @return
     */
    public int get_altura_matriz(String txt, String clave){
        int longitud_total = txt.length() + clave.length();
        int tamv = 0;
        if(txt.length()%clave.length()==0){
            tamv = (longitud_total/clave.length());
        }else{
            tamv = (longitud_total/clave.length())+1;
        }
        return tamv;
    }

    public HashMap<String, Integer> obtener_alfabeto(){
        HashMap<String, Integer> diccionario = new HashMap<String, Integer>();
        diccionario.put("A", 1);
        diccionario.put("B", 2);
        diccionario.put("C", 3);
        diccionario.put("D", 4);
        diccionario.put("E", 5);
        diccionario.put("F", 6);
        diccionario.put("G", 7);
        diccionario.put("H", 8);
        diccionario.put("I", 9);
        diccionario.put("J", 10);
        diccionario.put("K", 11);
        diccionario.put("L", 12);
        diccionario.put("M", 13);
        diccionario.put("N", 14);
        diccionario.put("O", 15);
        diccionario.put("P", 16);
        diccionario.put("Q", 17);
        diccionario.put("R", 18);
        diccionario.put("S", 19);
        diccionario.put("T", 20);
        diccionario.put("U", 21);
        diccionario.put("V", 22);
        diccionario.put("W", 23);
        diccionario.put("X", 24);
        diccionario.put("Y", 25);
        diccionario.put("Z", 26);
        return diccionario;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        c_entrada.setWrapText(true);
        c_salida.setWrapText(true);
        d_entrada.setWrapText(true);
        d_salida.setWrapText(true);
        c_salida.setEditable(false); 
        d_salida.setEditable(false);
    }
}
