/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.archivoTexto;

import com.mycompany.travels.rest.api.db.ConexionDB;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class BuscadorDeIds {

    private static final String ID_PAIS_POR_NOMBRE = "select pais_id from pais where pais_nombre = ?";

    public int buscarIdPais(String nombre) throws ExceptionGenerica {
        int id = -1;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ID_PAIS_POR_NOMBRE)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("pais_id");
                }
            }
            return id;
        } catch (SQLException e) {
            throw new ExceptionGenerica("error al buscar id pais " + e.getMessage());
        }
    }

}
