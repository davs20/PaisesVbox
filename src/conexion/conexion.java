package conexion;

import java.sql.*;

public class conexion {
    private final static String usuario = "root";
    private final static String pass = "";
    private static String conexion = "jdbc:mysql://localhost:3306/world";
    private Connection connection;


    public Connection iniciarConexion() {

        try {
            connection = DriverManager.getConnection(conexion, usuario, pass);
            return connection;
        } catch (SQLException e) {
            System.out.println("hola");
            e.printStackTrace();
        }
        return null;
    }

}
