/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import DBAccess.NavegacionDAOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import javafx.stage.Stage;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author carolinaalbamaruganrubio
 */
public class VerEditarPerfilController implements Initializable {

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
    private Text excepcionesDeRegistro;
    
    Navegacion navegacion;
    User user;
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
        nickname.setDisable(true);
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

    public void setUser(User u){
        user = u;
        nickname.setText(user.getNickName());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        repPassword.setText(user.getPassword());
        birthDate.setValue(user.getBirthdate());
        if(user.getAvatar() == null){ avatar.setImage(null); }
        else{ avatar.setImage(user.getAvatar()); }
    }

    @FXML
    private void modificarDatos(ActionEvent event) throws NavegacionDAOException, FileNotFoundException {
        if(!password.getText().equals(repPassword.getText())){
            excepcionesDeRegistro.setText("Contraseñas no coinciden");
            ConfirmacionUsuario.setText("");
        }
        else if(!User.checkEmail(email.getText())){ 
            excepcionesDeRegistro.setText("Formato correo no válido"); 
            ConfirmacionUsuario.setText("");
        }
        else if(!User.checkPassword(password.getText())){
            excepcionesDeRegistro.setText("Formato contraseña no válido");
            ConfirmacionUsuario.setText("");
        }else{
        user.setPassword(password.getText());
        user.setBirthdate(birthDate.getValue());
        user.setEmail(email.getText());
        excepcionesDeRegistro.setText("");
        if(avatar.getImage() == null){ //user.setAvatar(null); 
            //String url = "."+File.separator+"resources"+File.separator+"default.png";
            //Image i = new Image(new FileInputStream(url));
           // File file = new File("default.png");
            //Image i = new Image(file.toURI().toString());
            InputStream resourceAsStream = User.class.getResourceAsStream("/resources/avatars/default.png"); //Con File.separator da error
            Image i = new Image(resourceAsStream);
            //this.avatar = new Image(resourceAsStream);
            avatar.imageProperty().setValue(i); 
            user.setAvatar(i);
            ConfirmacionUsuario.setText("Se ha guardado correctamente!");
        }
        else{ user.setAvatar(avatar.getImage()); 
        ConfirmacionUsuario.setText("Se ha guardado correctamente!");}}
    }

    @FXML
    private void cancel(ActionEvent event) {
        Node minodo = (Node) event.getSource();
        ((Stage)minodo.getScene().getWindow()).close();
    }

    @FXML
    private void editAvatar(ActionEvent event) {
        FileChooser fileChooser	=	new	FileChooser();	
	fileChooser.setTitle("Abrir	fichero");	
	fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif"));
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
    private void elimAvat(ActionEvent event) {
        avatar.setImage(null);
    }
    
}
