package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    private final static String usuario = "root";
    private final static String pass = "";
    private static String conexion = "jdbc:mysql://localhost:3306/world";
    private static Connection connection;
    public static Statement comando = null;


    @Override
    public void start(Stage primaryStage) throws Exception {
        abrirConexion();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 450, 480));
        primaryStage.show();

    }


    public static Statement abrirConexion() {

        try {
            connection = DriverManager.getConnection(conexion, usuario, pass);
            comando = connection.createStatement();
            return comando;
        } catch (SQLException e) {
            System.out.println("Malo");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
