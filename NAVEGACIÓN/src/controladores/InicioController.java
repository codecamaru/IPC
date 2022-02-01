/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author carolinaalbamaruganrubio
 */
public class InicioController implements Initializable {

    @FXML
    private Text excepcionesInicio;
    
    private boolean pulsadoRegistrar = false;
    private boolean seHaIniciado = false;
    private User user;
    
    Navegacion navegacion;
    String nombreUsuario;
    String contraseña;
    
    @FXML
    private TextField nickName;
    @FXML
    private PasswordField password;

    EstadisticasController est;
     FXMLLoader loader;
     Parent rootEstadisticas;
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
    }    


    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        /* comprueba que existe nombre */
        nombreUsuario = nickName.getText();
        contraseña = password.getText();
        boolean existe = navegacion.exitsNickName(nombreUsuario);
        if(!existe){
            excepcionesInicio.setText("No existe el usuario marcado");
        }
        else{
            if(navegacion.loginUser(nombreUsuario, contraseña) == null){
                excepcionesInicio.setText("Contraseña incorrecta");
            }
            else{
                user = navegacion.loginUser(nombreUsuario, contraseña);
                seHaIniciado = true;
                
                /*loader = new FXMLLoader(getClass().getResource("/vista/FXML_Estadisticas.fxml"));
                rootEstadisticas = loader.load();
                ((EstadisticasController)loader.getController()).setUser(user);*/
                
                Node minodo = (Node) event.getSource();
                ((Stage)minodo.getScene().getWindow()).close();
            }
        }
    }
    
    boolean isRegistrar(){
        return pulsadoRegistrar;
    }
    boolean isIniciar(){
        return seHaIniciado;
    }
    User getUser(){
        return user;
    }

    @FXML
    private void pulsadoRegistrar(ActionEvent event) {
        pulsadoRegistrar = true;
        Node minodo = (Node) event.getSource();
        ((Stage)minodo.getScene().getWindow()).close();
    }
    
}
