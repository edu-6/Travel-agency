/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author edu
 */
public class ConexionDB {
    private static String URL = "jdbc:mysql://localhost:3306/travelsDB";
    private static String USERNAME = "adminedu";
    private static String PASSWORD = "12345";
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar con la base de datos"+e);  
        }
        return null;
    }
}
