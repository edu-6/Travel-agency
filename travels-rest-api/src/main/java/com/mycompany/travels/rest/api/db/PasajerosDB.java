/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db;

import com.mycompany.travels.rest.api.dtos.pasajeros.PasajeroRequest;
import com.mycompany.travels.rest.api.dtos.pasajeros.PasajeroResponse;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.BuscarVariosInt;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.interfaces.ExtraerEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PasajerosDB implements CreacionEntidad<PasajeroRequest>, BuscarVariosInt<PasajeroResponse>,
    ExtraerEntidad<PasajeroResponse>{

    private static final String CREAR = "INSERT INTO pasajero_reservacion"
            + " (pasajero_reservacion_id_reservacion, pasajero_reservaicon_id_cliente) VALUES (?, ?)";

    private static final String BUSCAR_POR_RESERVACION = "SELECT c.cliente_id, c.cliente_nombre, n.nacionalidad_nombre "
            + "FROM pasajero_reservacion pr "
            + "JOIN cliente c ON pr.pasajero_reservaicon_id_cliente = c.cliente_id "
            + "JOIN nacionalidad n ON c.cliente_id_nacionalidad = n.nacionalidad_id "
            + "WHERE pr.pasajero_reservacion_id_reservacion = ?";

    @Override
    public void crear(PasajeroRequest entidad) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(CREAR)) {
            pstmt.setInt(1, entidad.getId_reservacion());
            pstmt.setString(2, entidad.getIdentificacion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al agregar pasajero: " + e.getMessage());
        }
    }

    public void agregarPasajeros(ArrayList<PasajeroRequest> lista) throws ExceptionGenerica {
        for (PasajeroRequest pasajeroRequest : lista) {
            this.crear(pasajeroRequest);
        }
    }

    @Override
    public ArrayList<PasajeroResponse> buscarVariosInt(int idReservacion) throws ExceptionGenerica {
        ArrayList<PasajeroResponse> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(BUSCAR_POR_RESERVACION)) {
            
            pstmt.setInt(1, idReservacion);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al buscar pasajeros: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public PasajeroResponse extraer(ResultSet rs) throws SQLException {
        return new PasajeroResponse(
                rs.getString("cliente_id"),
                rs.getString("cliente_nombre"),
                rs.getString("nacionalidad_nombre")
        );
    }
}
