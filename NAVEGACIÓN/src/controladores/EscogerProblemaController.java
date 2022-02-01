/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import model.Navegacion;
import model.Problem;

/**
 * FXML Controller class
 *
 * @author carolinaalbamaruganrubio
 */
public class EscogerProblemaController implements Initializable {

    @FXML
    private ListView<Problem> lisviewProblemas;
    List<Problem> listaProblemas;
    Navegacion navegacion;
    private Parent rootEnunciado;
    private BorderPane bp;
    PrincipalController objetoPrin;
    ObservableList<Problem> listaObservable;
    @FXML
    private CheckBox checky;
    FXMLLoader loader;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
           navegacion = Navegacion.getSingletonNavegacion();
        }
        catch(NavegacionDAOException e){}
           loader = new FXMLLoader(getClass().getResource("/vista/FXML_Enunciado.fxml"));
        try {
           rootEnunciado = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(EscogerProblemaController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        listaProblemas = navegacion.getProblems();
        listaObservable = FXCollections.observableList(listaProblemas);
        lisviewProblemas.setItems(listaObservable);
    }    

    @FXML
    private void mostrarProblema(ActionEvent event) {
        
        if(!checky.isSelected()){
            // mostramos problema aleatorio en Enunciado
            Problem selectedProblem = lisviewProblemas.getSelectionModel().getSelectedItem();
            ((EnunciadoController)loader.getController()).setProblema(selectedProblem);
            ((EnunciadoController)loader.getController()).setEtiqueta("");
            ((EnunciadoController)loader.getController()).setControladorPrincipal(objetoPrin);
            // seteo en centro el enunciado
            bp = objetoPrin.getBorderPane();
            bp.setCenter(rootEnunciado);
            objetoPrin.enableEnunciado();
            objetoPrin.setRootEnunciado(rootEnunciado);
        }else{
            // mostramos problema seleccionado en Enunciado
            Problem selectedProblem = listaProblemas.get((int)(Math.random()*(3 - 0) + 0));
            ((EnunciadoController)loader.getController()).setProblema(selectedProblem);
            ((EnunciadoController)loader.getController()).setEtiqueta("");
            ((EnunciadoController)loader.getController()).setControladorPrincipal(objetoPrin);
            // seteo en centro el enunciado
            bp = objetoPrin.getBorderPane();
            bp.setCenter(rootEnunciado);
            objetoPrin.enableEnunciado();
            objetoPrin.setRootEnunciado(rootEnunciado);
        }
    }
    public void setControladorPrincipal(PrincipalController p){
        objetoPrin = p;
    }
    
}
