/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db.reportes;

import com.mycompany.travels.rest.api.db.ConexionDB;
import com.mycompany.travels.rest.api.dtos.reportes.ReproteAgenteMasGanancias;
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
public class AgenteConMasGanaciasDB {

    private static final String AGENTE_TOP_GANANCIA
            = "SELECT e.empleado_nombre, "
            + "((SELECT COALESCE(SUM(p.pago_cantidad), 0) FROM pago_reservacion p "
            + "  JOIN reservacion r2 ON p.pago_id_reservacion = r2.rs_numero_reservacion "
            + "  WHERE r2.rs_id_agente_creador = e.empleado_id) - "
            + " (SELECT COALESCE(SUM(c.cancelacion_cantidad_rembolso), 0) FROM cancelacion c "
            + "  JOIN reservacion r3 ON c.cancelacion_id_reservacion = r3.rs_numero_reservacion "
            + "  WHERE r3.rs_id_agente_creador = e.empleado_id)) AS ganancia_neta "
            + "FROM empleado e "
            + "WHERE e.empleado_id_rol = 1 "
            + "ORDER BY ganancia_neta DESC LIMIT 1";

    private static final String AGENTE_TOP_GANANCIA_PERIODO
            = "SELECT e.empleado_nombre, "
            + "((SELECT COALESCE(SUM(p.pago_cantidad), 0) FROM pago_reservacion p "
            + "  JOIN reservacion r2 ON p.pago_id_reservacion = r2.rs_numero_reservacion "
            + "  WHERE r2.rs_id_agente_creador = e.empleado_id "
            + "  AND p.pago_fecha >= ? AND p.pago_fecha <= ?) - "
            + " (SELECT COALESCE(SUM(c.cancelacion_cantidad_rembolso), 0) FROM cancelacion c "
            + "  JOIN reservacion r3 ON c.cancelacion_id_reservacion = r3.rs_numero_reservacion "
            + "  WHERE r3.rs_id_agente_creador = e.empleado_id "
            + "  AND c.cancelacion_fecha >= ? AND c.cancelacion_fecha <= ?)) AS ganancia_neta "
            + "FROM empleado e "
            + "WHERE e.empleado_id_rol = 1 "
            + "ORDER BY ganancia_neta DESC LIMIT 1";
    
    
    
    public ReproteAgenteMasGanancias obtenerAgenteTopGanancia() throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(AGENTE_TOP_GANANCIA)) {
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extraer(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener agente con más ganancia histórica: " + e.getMessage());
        }
    }

    public ReproteAgenteMasGanancias obtenerAgenteTopGananciaPeriodo(ReporteRequest request) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(AGENTE_TOP_GANANCIA_PERIODO)) {
            

            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            

            ps.setDate(3, Date.valueOf(request.getFechaInicio()));
            ps.setDate(4, Date.valueOf(request.getFechaFinal()));
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extraer(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener agente con más ganancia por periodo: " + e.getMessage());
        }
    }

    private ReproteAgenteMasGanancias extraer(ResultSet rs) throws SQLException {
        return new ReproteAgenteMasGanancias(
            rs.getString("empleado_nombre"),
            rs.getDouble("ganancia_neta")
        );
    }
}
