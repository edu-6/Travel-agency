/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db.reportes;

import com.mycompany.travels.rest.api.db.ConexionDB;
import com.mycompany.travels.rest.api.dtos.reportes.agenteMasVentas.ReporteAgenteMasVentas;
import com.mycompany.travels.rest.api.dtos.reportes.agenteMasVentas.ReservacionDetalle;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.ReporteRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class AgenteConMasVentasDB {
    
    private static final String AGENTE_TOP =
    "SELECT r.rs_id_agente_creador, e.empleado_nombre " +
    "FROM reservacion r JOIN empleado e ON r.rs_id_agente_creador = e.empleado_id " +
    "GROUP BY r.rs_id_agente_creador, e.empleado_nombre " +
    "ORDER BY SUM(r.rs_total_a_pagar) DESC LIMIT 1";

private static final String AGENTE_TOP_PERIODO = 
    "SELECT r.rs_id_agente_creador, e.empleado_nombre " +
    "FROM reservacion r JOIN empleado e ON r.rs_id_agente_creador = e.empleado_id " +
    "WHERE r.rs_fecha_creacion >= ? AND r.rs_fecha_creacion <= ? " +
    "GROUP BY r.rs_id_agente_creador, e.empleado_nombre " +
    "ORDER BY SUM(r.rs_total_a_pagar) DESC LIMIT 1";

    private static final String DETALLE_RESERVACION = 
        "SELECT rs_numero_reservacion, rs_total_a_pagar, rs_fecha_creacion " +
        "FROM reservacion WHERE rs_id_agente_creador = ?";

    private static final String DETALLE_RESERVACION_PERIODO = 
        "SELECT rs_numero_reservacion, rs_total_a_pagar, rs_fecha_creacion " +
        "FROM reservacion WHERE rs_id_agente_creador = ? " +
        "AND rs_fecha_creacion >= ? AND rs_fecha_creacion <= ?";
    

    public ReporteAgenteMasVentas obtenerAgenteTop() throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(AGENTE_TOP)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extraerAgente(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener agente top histórico: " + e.getMessage());
        }
    }

    public ReporteAgenteMasVentas obtenerAgenteTopPeriodo(ReporteRequest request) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(AGENTE_TOP_PERIODO)) {
            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extraerAgente(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener agente top por periodo: " + e.getMessage());
        }
    }


    public ArrayList<ReservacionDetalle> obtenerDetalleReservacion(int idAgente) throws ExceptionGenerica {
        ArrayList<ReservacionDetalle> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(DETALLE_RESERVACION)) {
            ps.setInt(1, idAgente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(extraerDetalle(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener detalle histórico: " + e.getMessage());
        }
    }

    public ArrayList<ReservacionDetalle> obtenerDetalleReservacionPeriodo(int idAgente, ReporteRequest request) throws ExceptionGenerica {
        ArrayList<ReservacionDetalle> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(DETALLE_RESERVACION_PERIODO)) {
            ps.setInt(1, idAgente);
            ps.setDate(2, Date.valueOf(request.getFechaInicio()));
            ps.setDate(3, Date.valueOf(request.getFechaFinal()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(extraerDetalle(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener detalle por periodo: " + e.getMessage());
        }
    }


    private ReporteAgenteMasVentas extraerAgente(ResultSet rs) throws SQLException {
        return new ReporteAgenteMasVentas(
            rs.getString("empleado_nombre"), 
            rs.getInt("rs_id_agente_creador")
        );
    }

    private ReservacionDetalle extraerDetalle(ResultSet rs) throws SQLException {
        return new ReservacionDetalle(
            rs.getInt("rs_numero_reservacion"),
            rs.getInt("rs_total_a_pagar"),
            rs.getDate("rs_fecha_creacion").toLocalDate()
        );
    }

}
