package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionDB {
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/DATABASE-NAME";
    static final String USERNAME = "";
    static final String PASSWORD = "";

    public void cargarDatos(Pais pais) throws ClassNotFoundException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Class.forName("com.mysql.cj.jdbc.Driver");

        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            // Query para crear la base de datos en los archivos, aparece como query.sql. Crearla antes de ejecutar el main
            String sql = "INSERT INTO Pais (codigoPais, nombrePais, capitalPais, region, poblacion, latitud, longitud) VALUES (?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, pais.getCodigoPais());
            preparedStatement.setString(2, pais.getNombrePais());
            preparedStatement.setString(3, pais.getCapitalPais());
            preparedStatement.setString(4, pais.getRegion());
            preparedStatement.setInt(5, pais.getPoblacion());
            preparedStatement.setDouble(6, pais.getLatitud());
            preparedStatement.setDouble(7, pais.getLongitud());

            int filasInsertadas = preparedStatement.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Inserción exitosa: " + filasInsertadas + " fila(s) insertada(s).");
            } else {
                System.out.println("No se realizaron inserciones.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}
