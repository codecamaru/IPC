/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author carolinaalbamaruganrubio
 */
public class PrincipalController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button estadisticas;
    @FXML
    private Button escogerProblema;
    @FXML
    private Button mapa;
    @FXML
    private Button enunciado;
    @FXML
    private MenuItem verEditarPerfil;
    @FXML
    private MenuItem cerrarSesion;
    
    private Parent rootEstadisticas;
    private Parent rootEscogerUnProblema;
    private Parent rootMapa;
    private Parent rootRegistro;
  //  private Parent rootEnunciado;
    private Parent rootVerEditarPerfil;

    Navegacion navegacion;
    User user;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXML_Estadisticas.fxml"));
            rootEstadisticas = loader.load();
            loader = new FXMLLoader(getClass().getResource("/vista/FXML_EscogerProblema.fxml"));
            rootEscogerUnProblema = loader.load();
            loader = new FXMLLoader(getClass().getResource("/vista/FXML_Mapa.fxml"));
            rootMapa = loader.load();
            loader = new FXMLLoader(getClass().getResource("/vista/FXML_Registro.fxml"));
            rootRegistro = loader.load();
           // loader = new FXMLLoader(getClass().getResource("/vista/FXML_Enunciado.fxml"));
           // rootEnunciado = loader.load();
           
           borderPane.setCenter(rootMapa);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrarSesion.setDisable(true);
        verEditarPerfil.setDisable(true);
        estadisticas.setDisable(true);
        escogerProblema.setDisable(true);
        enunciado.setDisable(true);
        
    }    

    @FXML
    private void muestraVentana(ActionEvent event) {
            Parent root = null;
            Button button = (Button) event.getSource();
            if (button == estadisticas) {
                root = rootEstadisticas;
            }
            if (button == escogerProblema) {
                root = rootEscogerUnProblema;  
            }
            if (button == mapa) {
                root = rootMapa;    
            }
            borderPane.setCenter(root);
    }

    @FXML
    private void inicioRegistro(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXML_Inicio.fxml")); 
        Parent root = loader.load();
        InicioController controladorSecund = loader.getController();
        Scene scene = new Scene(root);
        Stage ventanaSecundaria = new Stage();
        ventanaSecundaria.setScene(scene);
        ventanaSecundaria.initModality(Modality.APPLICATION_MODAL);
        ventanaSecundaria.setTitle("Titulo Provisional");
        ventanaSecundaria.showAndWait();
        if(controladorSecund.isRegistrar()){
            borderPane.setCenter(rootRegistro);
        }
        else if(controladorSecund.isIniciar()){
            verEditarPerfil.setDisable(false);
            cerrarSesion.setDisable(false);
            estadisticas.setDisable(false);
            escogerProblema.setDisable(false);
            enunciado.setDisable(false);
            user = controladorSecund.getUser();
            System.out.println(user.toString());
        }
        
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        cerrarSesion.setDisable(true);
        verEditarPerfil.setDisable(true);
        estadisticas.setDisable(true);
        escogerProblema.setDisable(true);
        enunciado.setDisable(true);
        user = null;
    }

    @FXML
    private void verEditarPerfil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXML_VerEditarPerfil.fxml")); 
        Parent root = loader.load();
        VerEditarPerfilController controladorSecund = loader.getController();
        controladorSecund.setUser(user);
        Scene scene = new Scene(root);
        Stage ventanaSecundaria = new Stage();
        ventanaSecundaria.setScene(scene);
        ventanaSecundaria.initModality(Modality.APPLICATION_MODAL);
        ventanaSecundaria.setTitle("Titulo Provisional");
        ventanaSecundaria.showAndWait();
       
        
    }
    
    
    
    
}
