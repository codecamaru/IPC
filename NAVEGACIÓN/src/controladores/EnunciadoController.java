/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import model.Answer;
import model.Problem;

/**
 * FXML Controller class
 *
 * @author carolinaalbamaruganrubio
 */
public class EnunciadoController implements Initializable {

    PrincipalController objetoPrin;
    @FXML
    private TextArea enunciado;
    @FXML
    private ListView<String> lisviewRespuestas;
    Problem problema;
    List<Answer> answers;
    List<String> listaRespuestas;
    ObservableList<String> listaObservable;
    @FXML
    private Label resultado;
    private int aciertos;
    private int errores;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }    
    public void setControladorPrincipal(PrincipalController p){
        objetoPrin = p;
    }

    @FXML
    private void corregir(ActionEvent event) {
        int selectedIndex = lisviewRespuestas.getSelectionModel().getSelectedIndex();
        Answer selectedAnsw = answers.get(selectedIndex);
        if(selectedAnsw.getValidity()){
            // setear acierto en user
            aciertos++;
            objetoPrin.setAciertos(1);
            resultado.setText("¡Correcto!");
        }else{
            // setear error en user
            errores++;
            objetoPrin.setErrores(1);
            resultado.setText("¡Inténtalo de nuevo!");
        }
    }
    
    public void setProblema(Problem p){
        problema = p;
        enunciado.setText(problema.getText());
        listaRespuestas = new ArrayList<String>();
        answers = problema.getAnswers();
        for(int i = 0; i < answers.size(); i++){
            listaRespuestas.add(answers.get(i).getText());
        }
        listaObservable = FXCollections.observableList(listaRespuestas);
        lisviewRespuestas.setItems(listaObservable);
    }
    public void setEtiqueta(String s){
        resultado.setText(s);
    }
    public int getAciertos(){
        return aciertos;
    }
    public int getErrores(){
        return errores;
    }
}
