/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db.reportes;

import com.mycompany.travels.rest.api.db.ConexionDB;
import com.mycompany.travels.rest.api.dtos.reportes.ReporteOcupacion;
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
public class ReporteOcupacionDB {
    
    private static final String OCUPACION_HISTORICA = 
        "SELECT d.destino_nombre, COUNT(r.rs_numero_reservacion) AS cantidad " +
        "FROM destino d " +
        "LEFT JOIN paquete p ON d.destino_id = p.paquete_id_destino " +
        "LEFT JOIN reservacion r ON p.paquete_id = r.rs_id_paquete " +
        "GROUP BY d.destino_nombre " +
        "ORDER BY cantidad DESC";

    private static final String OCUPACION_PERIODO = 
        "SELECT d.destino_nombre, COUNT(r.rs_numero_reservacion) AS cantidad " +
        "FROM destino d " +
        "JOIN paquete p ON d.destino_id = p.paquete_id_destino " +
        "JOIN reservacion r ON p.paquete_id = r.rs_id_paquete " +
        "WHERE r.rs_fecha_creacion >= ? AND r.rs_fecha_creacion <= ? " +
        "GROUP BY d.destino_nombre " +
        "ORDER BY cantidad DESC";

    public List<ReporteOcupacion> obtenerOcupacion() throws ExceptionGenerica {
        List<ReporteOcupacion> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(OCUPACION_HISTORICA)) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(extraer(rs));
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener ocupación histórica: " + e.getMessage());
        }
        return lista;
    }

    public List<ReporteOcupacion> obtenerOcupacionPeriodo(ReporteRequest request) throws ExceptionGenerica {
        List<ReporteOcupacion> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(OCUPACION_PERIODO)) {
            
            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(extraer(rs));
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener ocupación por periodo: " + e.getMessage());
        }
        return lista;
    }

    private ReporteOcupacion extraer(ResultSet rs) throws SQLException {
        return new ReporteOcupacion(
            rs.getString("destino_nombre"),
            rs.getInt("cantidad")
        );
    }
    
}
