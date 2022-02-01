/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author carolinaalbamaruganrubio
 */
public class EstadisticasController implements Initializable {

    @FXML
    private PieChart pie;
    @FXML
    private BarChart<String, Number> bar;
    
    PrincipalController objetoPrin;
    User user;
    

    private final ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<String, Number>> barData; 
    
    private XYChart.Series<String,Number> serie; 
    @FXML
    private DatePicker fecha;
    int aciertos;
    int fallos;
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
    public void inicializa(){
        List<Session> sesiones = user.getSessions();
        fecha.setValue(LocalDate.of(2000, 3,19));
           
        /*LocalDate l = LocalDate.of(1950, 3,19);
        int aciertos = numAciertos(l,sessions);
        int errores = numErrores(l,sessions);
        
        
        PieChart.Data holaMaria = new PieChart.Data("Aciertos",aciertos);
        PieChart.Data holaCarol = new PieChart.Data("Nada de esto fue un error,uoo",errores);
        
        pieData.addAll(holaMaria,holaCarol);
        pie = new PieChart(pieData);
        pie.setTitle("PORFA FUNCIONA");
        
        /*barData = FXCollections.observableArrayList();
        for(int i = 0; i < sessions.size(); i++){
            XYChart.Series<String,Number> s = new XYChart.Series<>();
            s.setName(sessions.get(i).toString());
            s.getData().addAll(new XYChart.Data<>("Aciertos",sessions.get(i).getHits()), new XYChart.Data<>("Errores",sessions.get(i).getFaults()));
            barData.add(s);
        }
        bar.setData(barData);
        /*serie = new XYChart.Series<>(barData);
        serie.setData(barData);
        bar.getData().add(serie);
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        bar = new BarChart<>(xAxis,yAxis);
        bar.setTitle("Resultados por sesiones");
        xAxis.setLabel("Sesiones");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003"); // Leyenda
        //for()
        series1.getData().add(new XYChart.Data(sessions.get(0).toString(),sessions.get(2).getHits()));*/
    }
    public void setUser(User u){
        user = u;
    }
    
    protected int numAciertos(LocalDate l, List<Session> lis){
        int res = 0;
        for(int i = 0; i < lis.size(); i++){
            if(l.compareTo(lis.get(i).getLocalDate()) <= 0){
                res += lis.get(i).getHits();
            }
        }
        System.out.print(res);
        return res;
    }
    protected int numErrores(LocalDate l, List<Session> lis){
        int res = 0;
        for(int i = 0; i < lis.size(); i++){
            if(l.compareTo(lis.get(i).getLocalDate()) <= 0){
                res += lis.get(i).getFaults();
            }
        }
        System.out.print(res);
        return res;
    }

    @FXML
    private void fechaEst(ActionEvent event) {
    }
}
