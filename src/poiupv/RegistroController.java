/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author carolinaalbamaruganrubio
 */
public class RegistroController implements Initializable {

    @FXML
    private Text excepcionesDeRegistro;
    
    Navegacion navegacion;
    @FXML
    private TextField nickname;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repPassword;
    @FXML
    private DatePicker birthDate;
    @FXML
    private ImageView avatar;
    @FXML
    private Label ConfirmacionUsuario;

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
        birthDate.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today.minusYears(12)) >= 0 );
            }
            };
        });
    }    

    @FXML
    private void registrarUsuario(ActionEvent event) throws NavegacionDAOException {
        if(navegacion.exitsNickName(nickname.getText())){
            excepcionesDeRegistro.setText("Qué lástima! Este nombre de usuario ya está en uso...");
        }
        else{
            String nombre = nickname.getText();
            String correo = email.getText();
            String contra = password.getText();
            LocalDate cumple = birthDate.getValue();
            Image foto = avatar.getImage();
            if(!User.checkNickName(nombre)){
                excepcionesDeRegistro.setText("Formato nickName no válido: debería tener entre 6 y 15\n" +
                "caracteres y contener letras mayúsculas, minúsculas o guiones ‘–‘ o ’_’");
            }
            else if(!User.checkEmail(correo)){ 
                excepcionesDeRegistro.setText("Formato correo no válido"); 
            } 
            else if(!User.checkPassword(contra)){
                excepcionesDeRegistro.setText("Formato contraseña no válido");
            }
            else if(!password.getText().equals(repPassword.getText())){
                excepcionesDeRegistro.setText("Contraseñas no coinciden");
            }
            else if(foto != null){
                User result = navegacion.registerUser(nombre, correo, contra, foto, cumple);
                ConfirmacionUsuario.setText("Se ha registrado correctamente!");
            }
            else{
                User result = navegacion.registerUser(nombre, correo, contra, cumple);
                ConfirmacionUsuario.setText("Se ha registrado correctamente!");
            }
        }
        
    }

    @FXML
    private void editAvatar(ActionEvent event) {
        	FileChooser fileChooser	=	new	FileChooser();	
		fileChooser.setTitle("Abrir	fichero");	
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());	
		if	(selectedFile	!=	null)	{	
                                Image image = new Image(selectedFile.toURI().toString());
                                avatar.setImage(image);
                                /* la idea de estas dos últimas líneas se han sacado de:
                                https://stackoverflow.com/questions/22710053/how-can-i-show-an-image-using-the-imageview-component-in-javafx-and-fxml
                                */
		}
    }

    @FXML
    private void elimAvatar(ActionEvent event) {
        avatar.setImage(null);
    }

    
}
