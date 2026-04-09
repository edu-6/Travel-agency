/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db.reportes;

import com.mycompany.travels.rest.api.db.ConexionDB;
import com.mycompany.travels.rest.api.dtos.reportes.paqueteMasVendido.DetalleReservacionPaquete;
import com.mycompany.travels.rest.api.dtos.reportes.ReportePaqueteMaxOMenosVendido;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.ReporteRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author edu
 */
public class PaqueteMasVendidoDB {
private static final String PAQUETE_TOP = 
        "SELECT p.paquete_id, p.paquete_nombre, p.paquete_descripcion, d.destino_mejor_epoca " +
        "FROM reservacion r JOIN paquete p ON r.rs_id_paquete = p.paquete_id " +
        "JOIN destino d ON p.paquete_id_destino = d.destino_id " +
        "GROUP BY p.paquete_id, p.paquete_nombre, p.paquete_descripcion, d.destino_mejor_epoca " +
        "ORDER BY COUNT(r.rs_numero_reservacion) DESC LIMIT 1";

    private static final String PAQUETE_TOP_PERIODO = 
        "SELECT p.paquete_id, p.paquete_nombre, p.paquete_descripcion, d.destino_mejor_epoca " +
        "FROM reservacion r JOIN paquete p ON r.rs_id_paquete = p.paquete_id " +
        "JOIN destino d ON p.paquete_id_destino = d.destino_id " +
        "WHERE r.rs_fecha_creacion >= ? AND r.rs_fecha_creacion <= ? " +
        "GROUP BY p.paquete_id, p.paquete_nombre, p.paquete_descripcion, d.destino_mejor_epoca " +
        "ORDER BY COUNT(r.rs_numero_reservacion) DESC LIMIT 1";

    
    private static final String DETALLE = 
        "SELECT r.rs_numero_reservacion, p.paquete_nombre, c.cliente_nombre, r.rs_fecha_creacion, " +
        "r.rs_total_a_pagar, r.rs_total_pagado, e.estado_reservacion_nombre " +
        "FROM reservacion r JOIN paquete p ON r.rs_id_paquete = p.paquete_id " +
        "JOIN cliente c ON r.rs_id_titular = c.cliente_id " +
        "JOIN estado_reservacion e ON r.rs_id_estado = e.estado_reservacion_id " +
        "WHERE r.rs_id_paquete = ?";

    private static final String DETALLE_PERIODO = DETALLE + " AND r.rs_fecha_creacion >= ? AND r.rs_fecha_creacion <= ?";

    public ReportePaqueteMaxOMenosVendido obtenerPaqueteTop() throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(PAQUETE_TOP)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return extraerPaquete(rs);
            return null;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener paquete top histórico: " + e.getMessage());
        }
    }

    public ReportePaqueteMaxOMenosVendido obtenerPaqueteTopPeriodo(ReporteRequest request) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(PAQUETE_TOP_PERIODO)) {
            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return extraerPaquete(rs);
            return null;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener paquete top por periodo: " + e.getMessage());
        }
    }

    public List<DetalleReservacionPaquete> obtenerDetalles(int idPaquete) throws ExceptionGenerica {
        List<DetalleReservacionPaquete> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(DETALLE)) {
            ps.setInt(1, idPaquete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(extraerDetalle(rs));
            return lista;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener detalles del paquete: " + e.getMessage());
        }
    }

    public List<DetalleReservacionPaquete> obtenerDetallesPeriodo(int idPaquete, ReporteRequest request) throws ExceptionGenerica {
        List<DetalleReservacionPaquete> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(DETALLE_PERIODO)) {
            ps.setInt(1, idPaquete);
            ps.setDate(2, Date.valueOf(request.getFechaInicio()));
            ps.setDate(3, Date.valueOf(request.getFechaFinal()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(extraerDetalle(rs));
            return lista;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener detalles del paquete por periodo: " + e.getMessage());
        }
    }

    private ReportePaqueteMaxOMenosVendido extraerPaquete(ResultSet rs) throws SQLException {
        return new ReportePaqueteMaxOMenosVendido(
            rs.getInt("paquete_id"),
            rs.getString("paquete_nombre"),
            rs.getString("paquete_descripcion"),
            rs.getString("destino_mejor_epoca")
        );
    }

    private DetalleReservacionPaquete extraerDetalle(ResultSet rs) throws SQLException {
        return new DetalleReservacionPaquete(
            rs.getInt("rs_numero_reservacion"),
            rs.getString("paquete_nombre"),
            rs.getString("cliente_nombre"),
            rs.getDate("rs_fecha_creacion").toLocalDate(),
            rs.getDouble("rs_total_a_pagar"),
            rs.getDouble("rs_total_pagado"),
            rs.getString("estado_reservacion_nombre")
        );
    }
    
}
