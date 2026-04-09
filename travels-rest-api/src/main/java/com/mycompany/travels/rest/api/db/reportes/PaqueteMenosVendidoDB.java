/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db.reportes;

import com.mycompany.travels.rest.api.db.ConexionDB;
import com.mycompany.travels.rest.api.dtos.reportes.ReportePaqueteMaxOMenosVendido;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.ReporteRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class PaqueteMenosVendidoDB {

    private static final String PAQUETE_MIN
            = "SELECT p.paquete_id, p.paquete_nombre, p.paquete_descripcion, d.destino_mejor_epoca "
            + "FROM reservacion r JOIN paquete p ON r.rs_id_paquete = p.paquete_id "
            + "JOIN destino d ON p.paquete_id_destino = d.destino_id "
            + "GROUP BY p.paquete_id, p.paquete_nombre, p.paquete_descripcion, d.destino_mejor_epoca "
            + "ORDER BY COUNT(r.rs_numero_reservacion) ASC LIMIT 1";

    private static final String PAQUETE_MIN_PERIODO
            = "SELECT p.paquete_id, p.paquete_nombre, p.paquete_descripcion, d.destino_mejor_epoca "
            + "FROM reservacion r JOIN paquete p ON r.rs_id_paquete = p.paquete_id "
            + "JOIN destino d ON p.paquete_id_destino = d.destino_id "
            + "WHERE r.rs_fecha_creacion >= ? AND r.rs_fecha_creacion <= ? "
            + "GROUP BY p.paquete_id, p.paquete_nombre, p.paquete_descripcion, d.destino_mejor_epoca "
            + "ORDER BY COUNT(r.rs_numero_reservacion) ASC LIMIT 1";

    public ReportePaqueteMaxOMenosVendido obtenerPeorPaquete() throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(PAQUETE_MIN)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extraerPaquete(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener el peor  paquete historicamente: " + e.getMessage());
        }
    }

    public ReportePaqueteMaxOMenosVendido obtenerPeorPaquetePeriodo(ReporteRequest request) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(PAQUETE_MIN_PERIODO)) {
            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extraerPaquete(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener el peor paquete  por periodo: " + e.getMessage());
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
}
