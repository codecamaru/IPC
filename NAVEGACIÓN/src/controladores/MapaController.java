package controladores;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Maria
 */
public class MapaController implements Initializable {

    @FXML
    private ToggleButton dibujaPunto;
    private Shape punto;
    
    @FXML
    private ToggleButton dibujaLinea;
    private Line linea;
    @FXML
    private Line lineaGrosorLinea;
    @FXML
    private Slider grosorLinea;
    
    @FXML
    private ToggleButton dibujarArco;
    private Circle arco;
    private Double inicioXArc;
    @FXML
    private Line lineaGrosorArco;
    @FXML
    private Slider grosorArco;
    
    @FXML
    private ToggleButton poneTexto;
    private Text textoT;
    @FXML
    private ChoiceBox<String> tamañoTexto;
    private ObservableList list;
    
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ToggleButton activaTransportador;
    @FXML
    private ToggleButton activaEjes;
    @FXML
    private ScrollPane map_scrollPane;
    @FXML
    private Pane map_pane;
    @FXML
    private ImageView reglaTransportador;
    @FXML
    private Slider zoomSlider;
    Group zoom_group;
    @FXML
    private ToggleGroup dibujar;
    @FXML
    private ToggleGroup formaPunto;
    @FXML
    private RadioButton formaCirculo;
    @FXML
    private RadioButton formaCudrada;
    @FXML
    private RadioButton formaTriangular;
    @FXML
    private ToggleGroup activarReglas; 
    
    private double inicioXTrans;
    private double inicioYTrans;
    private double baseX;
    private double baseY;
    @FXML
    private Line lineaVertical;
    @FXML
    private Line lineaHorizontal;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        zoomSlider.setMin(0.5);
        zoomSlider.setMax(1.5);
        zoomSlider.setValue(1.0);
        zoomSlider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        Group contentGroup = new Group();
        zoom_group = new Group();
        contentGroup.getChildren().add(zoom_group);
        zoom_group.getChildren().add(map_scrollPane.getContent());
        map_scrollPane.setContent(contentGroup);
        
        lineaGrosorLinea.strokeWidthProperty().bind(grosorLinea.valueProperty());
        lineaGrosorArco.strokeWidthProperty().bind(grosorArco.valueProperty());
        
        reglaTransportador.visibleProperty().bind(activaTransportador.selectedProperty());
        
        lineaHorizontal.visibleProperty().bind(activaEjes.selectedProperty());
        lineaVertical.visibleProperty().bind(activaEjes.selectedProperty());
        
        dibujaLinea.disableProperty().bind(activaTransportador.selectedProperty());
        poneTexto.disableProperty().bind(activaTransportador.selectedProperty());
        dibujarArco.disableProperty().bind(activaTransportador.selectedProperty());
        
        list = FXCollections.observableArrayList();
        loadData();
        tamañoTexto.setValue("12px");
    }

    @FXML
    private void eliminarTodo(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialogo de confirmación");
        alert.setHeaderText("Eliminar todo");
        alert.setContentText("¿Estás seguro de que quieres eliminarlo todo?");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Eliminar");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            zoom_group.getChildren().remove(1, zoom_group.getChildren().size());
        }else{}
    }

    @FXML
    private void zoomIn(ActionEvent event) {
        double sliderVal = zoomSlider.getValue();
        zoomSlider.setValue(sliderVal += 0.1);
    }

    @FXML
    private void zoomOut(ActionEvent event) {
        double sliderVal = zoomSlider.getValue();
        zoomSlider.setValue(sliderVal + -0.1);
    }
    
    private void zoom(double scaleValue) {
        double scrollH = map_scrollPane.getHvalue();
        double scrollV = map_scrollPane.getVvalue();
        zoom_group.setScaleX(scaleValue);
        zoom_group.setScaleY(scaleValue);
        map_scrollPane.setHvalue(scrollH);
        map_scrollPane.setVvalue(scrollV);
    }

    @FXML
    private void puntoFinal(MouseEvent event) {
        if(dibujar.getSelectedToggle().equals(dibujaLinea)){
            linea.setEndX(event.getX());
            linea.setEndY(event.getY());
            event.consume();
        }else if(dibujar.getSelectedToggle().equals(dibujarArco)){
            double radio = Math.abs(event.getX()-inicioXArc);
            arco.setRadius(radio);
            event.consume();
           
        }
    }

    @FXML
    private void puntoInicial(MouseEvent event) {
        
        if(dibujar.getSelectedToggle().equals(dibujaLinea)){
            linea = new Line(event.getX(), event.getY(), event.getX(),event.getY());
            if(grosorLinea.getValue() != 0){
                linea.setStrokeWidth(grosorLinea.getValue());
            }else{linea.setStrokeWidth(5);}
            linea.setStroke(colorPicker.getValue());
            linea.setCursor(Cursor.HAND);
            
            zoom_group.getChildren().add(linea);
            linea.setOnContextMenuRequested(e -> {
                ContextMenu menuContext = new ContextMenu();
                
                ColorPicker seleccionarColor = new ColorPicker();
                Button aceptarColor = new Button("Aceptar Color");
                
                CustomMenuItem colores = new CustomMenuItem();
                CustomMenuItem boton = new CustomMenuItem();
                
                colores.setContent(seleccionarColor);
                boton.setContent(aceptarColor);
                colores.setHideOnClick(false);
                boton.setHideOnClick(true);
                MenuItem borrarItem = new MenuItem("Eliminar");
                
                menuContext.getItems().addAll(colores,boton,borrarItem);
                borrarItem.setOnAction(ev -> {
                    zoom_group.getChildren().remove((Node)e.getSource());
                    ev.consume();
                });
                boton.setOnAction(ev ->{
                    Line lineaCambio = (Line) e.getSource();
                    lineaCambio.setStroke(seleccionarColor.getValue());
                    ev.consume();
                });
                
                menuContext.show(linea, e.getX(), e.getY());
                e.consume();
            });
            event.consume();
            
        }else if(dibujar.getSelectedToggle().equals(dibujaPunto)){
            if(formaPunto.getSelectedToggle().equals(formaCirculo)){
                punto = new Circle(4);
                punto.setLayoutX(event.getX());
                punto.setLayoutY(event.getY());
            }else if(formaPunto.getSelectedToggle().equals(formaCudrada)){
                punto = new Rectangle(6,6);
                punto.setLayoutX(event.getX());
                punto.setLayoutY(event.getY());
            }else if(formaPunto.getSelectedToggle().equals(formaTriangular)){
                punto = new Polygon(event.getX(), event.getY()-4, event.getX()+4, event.getY()+4, event.getX()-4, event.getY()+4);
            }
            punto.setFill(colorPicker.getValue());
            punto.setCursor(Cursor.HAND);
            zoom_group.getChildren().add(punto);
            punto.setOnContextMenuRequested(e -> {
                ContextMenu menuContext = new ContextMenu();
                
                ColorPicker seleccionarColor = new ColorPicker();
                Button aceptarColor = new Button("Aceptar Color");
                
                CustomMenuItem colores = new CustomMenuItem();
                CustomMenuItem boton = new CustomMenuItem();
                
                colores.setContent(seleccionarColor);
                boton.setContent(aceptarColor);
                colores.setHideOnClick(false);
                boton.setHideOnClick(true);
                MenuItem borrarItem = new MenuItem("Eliminar");
                
                menuContext.getItems().addAll(colores,boton,borrarItem);
                borrarItem.setOnAction(ev -> {
                    zoom_group.getChildren().remove((Node)e.getSource());
                    ev.consume();
                });
                boton.setOnAction(ev ->{
                    Shape cambio = (Shape) e.getSource();
                    cambio.setFill(seleccionarColor.getValue());
                    ev.consume();
                });
                
                menuContext.show(punto, e.getX(), e.getY());
                e.consume();
            });
            event.consume();
                
        }else if(dibujar.getSelectedToggle().equals(dibujarArco)){
                arco = new Circle(1);
                arco.setFill(Color.TRANSPARENT);
                arco.setStrokeWidth(grosorArco.getValue());
                arco.setStroke(colorPicker.getValue());
                zoom_group.getChildren().add(arco);
                arco.setCenterX(event.getX());
                arco.setCenterY(event.getY());
                inicioXArc = event.getX();
                arco.setCursor(Cursor.HAND);
                
                arco.setOnContextMenuRequested(e -> {
                ContextMenu menuContext = new ContextMenu();
                
                ColorPicker seleccionarColor = new ColorPicker();
                Button aceptarColor = new Button("Aceptar Color");
                
                CustomMenuItem colores = new CustomMenuItem();
                CustomMenuItem boton = new CustomMenuItem();
                
                colores.setContent(seleccionarColor);
                boton.setContent(aceptarColor);
                colores.setHideOnClick(false);
                boton.setHideOnClick(true);
                MenuItem borrarItem = new MenuItem("Eliminar");
                
                menuContext.getItems().addAll(colores,boton,borrarItem);
                borrarItem.setOnAction(ev -> {
                    zoom_group.getChildren().remove((Node)e.getSource());
                    ev.consume();
                });
                boton.setOnAction(ev ->{
                    Circle cambio = (Circle) e.getSource();
                    cambio.setStroke(seleccionarColor.getValue());
                    ev.consume();
                });
                
                menuContext.show(arco, e.getX(), e.getY());
                e.consume();
            });
            event.consume();
                
        }else if(dibujar.getSelectedToggle().equals(poneTexto)){
                TextField texto = new TextField();
                zoom_group.getChildren().add(texto);
                texto.setLayoutX(event.getX());
                texto.setLayoutY(event.getY());
                texto.requestFocus();
                texto.setOnAction(e -> {
                    textoT = new Text (texto.getText());
                    textoT.setX(texto.getLayoutX());
                    textoT.setY(texto.getLayoutY());
                    textoT.setStyle("-fx-font-family: Gafata; -fx-font-size: " + tamañoTexto.getValue());
                    textoT.setFill(colorPicker.getValue());
                    textoT.setCursor(Cursor.HAND);
                    zoom_group.getChildren().add(textoT);
                    zoom_group.getChildren().remove(texto);
                    e.consume();
                });
               
                textoT.setOnContextMenuRequested(ev -> {
                    ContextMenu menuContext = new ContextMenu();

                    ColorPicker seleccionarColor = new ColorPicker();
                    Button aceptarColor = new Button("Aceptar Color");

                    CustomMenuItem colores = new CustomMenuItem();
                    CustomMenuItem boton = new CustomMenuItem();

                    colores.setContent(seleccionarColor);
                    boton.setContent(aceptarColor);
                    colores.setHideOnClick(false);
                    boton.setHideOnClick(true);
                    MenuItem borrarItem = new MenuItem("Eliminar");

                    menuContext.getItems().addAll(colores,boton,borrarItem);
                    borrarItem.setOnAction(eve -> {
                        zoom_group.getChildren().remove((Node)ev.getSource());
                        eve.consume();
                    });
                    boton.setOnAction(eve ->{
                        Text cambio = (Text) ev.getSource();
                        cambio.setFill(seleccionarColor.getValue());
                        eve.consume();
                    });
                
                    menuContext.show(textoT, ev.getX(), ev.getY());
                    ev.consume();
                });
            event.consume();
        } 
    }

    @FXML
    private void moverReglas(MouseEvent event) {
        if(activarReglas.getSelectedToggle().equals(activaTransportador)){
            double despX = event.getSceneX() - inicioXTrans;
            double despY = event.getSceneY() - inicioYTrans;
            reglaTransportador.setTranslateX(baseX + despX);
            reglaTransportador.setTranslateY(baseY+despY);
        event.consume();
        }else if(activarReglas.getSelectedToggle().equals(activaEjes)){
            double despX = event.getSceneX() - inicioXTrans;
            double despY = event.getSceneY() - inicioYTrans;
            lineaHorizontal.setTranslateX(baseX + despX);
            lineaVertical.setTranslateY(baseY+despY);
            event.consume();
        }
    }

    @FXML
    private void pressReglas(MouseEvent event) {
        if(activarReglas.getSelectedToggle().equals(activaTransportador)){
            inicioXTrans = event.getSceneX();
            inicioYTrans = event.getSceneY();
            baseX = reglaTransportador.getTranslateX();
            baseY = reglaTransportador.getTranslateY();
            event.consume();
        }else if(activarReglas.getSelectedToggle().equals(activaEjes)){
            inicioXTrans = event.getSceneX();
            inicioYTrans = event.getSceneY();
            if(event.getSource().equals(lineaVertical)){
                baseX = lineaVertical.getTranslateX();
                baseY = lineaVertical.getTranslateY();
            }else if(event.getSource().equals(lineaHorizontal)){
                baseX = lineaVertical.getTranslateX();
                baseY = lineaVertical.getTranslateY();
            }
            event.consume();
        }
    }
    
    private void loadData(){
        list.removeAll(list);
        String a = "12px";
        String b = "14 px";
        String c = "20px";
        String d = "28px";
        String e = "32px";
        String f = "40px";
        list.addAll(a,b,c,d,e,f);
        tamañoTexto.getItems().addAll(list);
        //https://www.youtube.com/watch?v=cQK2Yh_kayY
    }

}