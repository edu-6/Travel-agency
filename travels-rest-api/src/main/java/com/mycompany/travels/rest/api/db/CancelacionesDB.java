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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class CancelacionesDB {

    private static final String CANCELACION = "UPDATE reservacion  SET rs_id_estado = 3 where rs_numero_reservacion = ?";
    
    private static final String CREAR_CANCELACION = "INSERT INTO cancelacion"
            + " (cancelacion_fecha, cancelacion_id_reservacion, cancelacion_cantidad_rembolso)"
            + "VALUES (?, ?, ?)";

    private static final String BUSCAR_ESTADO_RESERVACION = "select rs_id_estado from reservacion where rs_numero_reservacion = ?";

    private static final String BUSCAR_FECHA_VIAJE = "SELECT rs_fecha_viaje from reservacion where rs_numero_reservacion = ?";
    
    
    private static final String BUSCAR_MONTO_PAGADO = "select rs_total_pagado from reservacion where rs_numero_reservacion = ?";

    public void registrarCancelacion(Cancelacion cancelacion) throws ExceptionGenerica {
        try (Connection conexion = ConexionDB.getConnection(); 
                PreparedStatement ps = conexion.prepareStatement(CREAR_CANCELACION)) {
            
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

    public int buscarEstadoReservacion(int idReservacion) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_ESTADO_RESERVACION)) {
            ps.setInt(1, idReservacion);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getInt("rs_id_estado");
                }
                return -1;
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("error al bsucar estado de reservacion " + e.getMessage());
        }
    }

    public LocalDate buscarFechaViaje(int idReservacion) throws ExceptionGenerica {
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(BUSCAR_FECHA_VIAJE)) {
            ps.setInt(1, idReservacion);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getDate("rs_fecha_viaje").toLocalDate();
                }
                throw new ExceptionGenerica(" no se encontró la fecha");
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al buscar fecha" + e.getMessage());
        }

    }
    
    public double obtenerTotalPagado(int idReservacion ) throws ExceptionGenerica {
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(BUSCAR_MONTO_PAGADO)) {
            ps.setInt(1, idReservacion);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getDouble("rs_total_pagado");
                }
                throw new ExceptionGenerica(" no se encontró la reservacion");
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error  al buscar reservación" + e.getMessage());
        }

    }

}
