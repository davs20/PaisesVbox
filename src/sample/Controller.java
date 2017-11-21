package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import conexion.conexion;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modelos.Ciudad;
import modelos.Pais;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField buscar;
    public ListView<String> paises;
    public ListView ciudades;
    public ListView descripcion;
    public Label error;
    public VBox desCiudades;
    public TextField poblacion;
    public TextField id;
    public TextField distrito;
    Statement comando = null;
    ResultSet registro;
    ObservableList<String> data = FXCollections.observableArrayList();
    ObservableList<String> data2 = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        conexion conexion = new conexion();
        try {

            registro = Main.comando.executeQuery("SELECT * FROM country");
            while (registro.next()) {
                data.addAll(registro.getString("Name"));
            }
            paises.setItems(data);
            System.out.println(paises.selectionModelProperty().get());


        } catch (SQLException e) {
            e.printStackTrace();
        }


            ciudades.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (!newValue.toString().isEmpty()) {
                        try {
                            ResultSet des = Main.comando.executeQuery("SELECT * FROM city WHERE  Name='" + newValue.toString() + "' ");
                            des.first();
                            poblacion.setText(des.getString("Population"));
                            id.setText(des.getString("ID"));
                            distrito.setText(des.getString("District"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


    }


    public void buscar(KeyEvent keyEvent) throws SQLException {

        error.setText(String.valueOf(keyEvent.getCode()));
        registro = Main.comando.executeQuery("SELECT Name FROM  country WHERE Name LIKE  '%" + buscar.getText().trim() + "%'");
        data2.clear();
        data.clear();
        while (registro.next()) {
            data.add(registro.getString("Name"));
        }
        registro.close();


    }

    public void ciudades(MouseEvent mouseEvent) {
        data2.clear();

        try {
            String pais = paises.getSelectionModel().getSelectedItem();
            String consulta = "SELECT Name FROM  city WHERE CountryCode=(SELECT Code FROM country WHERE country.Name='" + pais + "')";
            registro = Main.abrirConexion().executeQuery(consulta);
            while (registro.next()) {
                data2.add(registro.getString("Name"));
            }
            ciudades.setItems(data2);
            registro.close();
        } catch (SQLException e) {
            error.setText("No es posible ejecutar la consulta por" + e);

        }
    }

    public void descripcionCiudad() {


    }
}