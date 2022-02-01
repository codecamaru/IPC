/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
import model.Session;
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
    private Parent rootEnunciado;
    private Parent rootVerEditarPerfil;

    Navegacion navegacion;
    public User user;
    int aciertos;
    int errores;
    
    FXMLLoader loader;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loader = new FXMLLoader(getClass().getResource("/vista/FXML_Estadisticas.fxml"));
            rootEstadisticas = loader.load();
            ((EstadisticasController)loader.getController()).setControladorPrincipal(this);
            loader = new FXMLLoader(getClass().getResource("/vista/FXML_EscogerProblema.fxml"));
            rootEscogerUnProblema = loader.load();
            ((EscogerProblemaController)loader.getController()).setControladorPrincipal(this);
            loader = new FXMLLoader(getClass().getResource("/vista/FXML_Mapa.fxml"));
            rootMapa = loader.load();
            loader = new FXMLLoader(getClass().getResource("/vista/FXML_Registro.fxml"));
            rootRegistro = loader.load();
            ((RegistroController)loader.getController()).setControladorPrincipal(this);
            loader = new FXMLLoader(getClass().getResource("/vista/FXML_Enunciado.fxml"));
            rootEnunciado = loader.load();
            ((EnunciadoController)loader.getController()).setControladorPrincipal(this);
           
           borderPane.setCenter(rootMapa);
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrarSesion.setDisable(true);
        verEditarPerfil.setDisable(true);
        estadisticas.setVisible(false);
        escogerProblema.setVisible(false);
        enunciado.setVisible(false);
        
    }    

    @FXML
    private void muestraVentana(ActionEvent event) throws IOException {
            Parent root = null;
            Button button = (Button) event.getSource();
            if (button == estadisticas) {
                root = rootEstadisticas;
                 loader = new FXMLLoader(getClass().getResource("/vista/FXML_Estadisticas.fxml"));
                 rootEstadisticas = loader.load();
                 ((EstadisticasController)loader.getController()).setUser(user);
                ((EstadisticasController)loader.getController()).inicializa();
            }
            if (button == escogerProblema) {
                root = rootEscogerUnProblema;  
            }
            if (button == mapa) {
                root = rootMapa;    
            }
            if (button == enunciado) {
                root = rootEnunciado;    
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
            estadisticas.setVisible(true);
            escogerProblema.setVisible(true);
            //enunciado.setDisable(false);
            user = controladorSecund.getUser();
            System.out.println(user.toString());
            borderPane.setCenter(rootMapa);
        }
        
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws NavegacionDAOException {
        cerrarSesion.setDisable(true);
        verEditarPerfil.setDisable(true);
        estadisticas.setVisible(false);
        escogerProblema.setVisible(false);
        enunciado.setVisible(false);
        user.addSession(new Session(LocalDateTime.now(),aciertos,errores));
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
    
    public Parent getMapa(){
        return rootMapa;
    }
    public BorderPane getBorderPane(){
        return borderPane;
    }
    public void botonCancelar(){
        borderPane.setCenter(rootMapa);
    }
    public User getUser(){
        return user;
    }
    public void enableEnunciado(){
        enunciado.setVisible(true);
    }
    public void setRootEnunciado(Parent p){
        rootEnunciado = p;
    }
    public void setRootMapa(Parent p){
        rootMapa = p;
    }
    public void setAciertos(int i){
        aciertos += i;
    }
    public void setErrores(int i){
        errores += i;
    }
}
