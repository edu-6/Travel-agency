/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db;

import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.Cancelacion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class CancelacionesDB {

    private static final String CANCELACION = "UPDATE reservacion  SET rs_id_estado = 4 where rs_id = ?";
    private static final String CREAR_CANCELACION = "INSERT INTO cancelacion"
            + " (cancelacion_fecha, cancelacion_id_reservacion, cancelacion_cantidad_rembolso)"
            + "VALUES (?, ?, ?)";

    public void registrarCancelacion(Cancelacion cancelacion, Connection conexion) throws ExceptionGenerica {
        try (PreparedStatement ps = conexion.prepareStatement(CREAR_CANCELACION)) {
            ps.setDate(1, Date.valueOf(cancelacion.getFechaCancelacion()));
            ps.setInt(2, cancelacion.getIdReservacion());
            ps.setDouble(3, cancelacion.getCantidadReembolsada());
            
            this.marcarComoCancelada(cancelacion.getIdReservacion());
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al registrar cancelación: " + e.getMessage());
        }
    }

    private void marcarComoCancelada(int idReservacion) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CANCELACION)) {
            ps.setInt(1, idReservacion);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("error al cancelar la reservación ");
        }
    }
}
