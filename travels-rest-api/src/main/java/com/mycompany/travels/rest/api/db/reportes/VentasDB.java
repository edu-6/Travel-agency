/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db.reportes;

import com.mycompany.travels.rest.api.db.ConexionDB;
import com.mycompany.travels.rest.api.dtos.reportes.ReporteVenta;
import com.mycompany.travels.rest.api.dtos.reportes.ventas.PasajeroReservacion;
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
public class VentasDB {

    private static final String RESERVACIONES_COMFIRMADAS = "select cliente_nombre, cliente_id, paquete_nombre,empleado_nombre, reservacion.* from reservacion"
            + " JOIN empleado ON rs_id_agente_creador = empleado_id"
            + " JOIN paquete ON rs_id_paquete = paquete_id"
            + " JOIN estado_reservacion ON estado_reservacion_id = rs_id_estado"
            + " JOIN cliente ON cliente_id = rs_id_titular"
            + " where rs_id_estado = 2";

    private static final String FILTRO_FECHA = " AND rs_fecha_creacion >= ? && rs_fecha_creacion <= ?";

    private static final String RESERVACIONES_CONFIRMADAS_PERIODO = RESERVACIONES_COMFIRMADAS + FILTRO_FECHA;

    private static final String PASAJEROS_POR_RESERVACION
            = "SELECT c.cliente_id, c.cliente_nombre, n.nacionalidad_nombre "
            + "FROM pasajero_reservacion pr "
            + "JOIN cliente c ON pr.pasajero_reservaicon_id_cliente = c.cliente_id "
            + "JOIN nacionalidad n ON c.cliente_id_nacionalidad = n.nacionalidad_id "
            + "WHERE pr.pasajero_reservacion_id_reservacion = ?";
    
    public ArrayList<ReporteVenta> obtenerReservacionesConfirmadas() throws ExceptionGenerica {

        ArrayList<ReporteVenta> lista = new ArrayList();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(RESERVACIONES_COMFIRMADAS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteVenta reporte = extraer(rs);
                this.agregarPasajerosReservacion(reporte);
                lista.add(reporte);
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("error al obtener las reservaciones confirmadas" + e.getMessage());
        }
        return lista;

    }
    

    public ArrayList<ReporteVenta> obtenerReservacionesConfirmadasPeriodo(ReporteRequest request) throws ExceptionGenerica {

        ArrayList<ReporteVenta> lista = new ArrayList();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(RESERVACIONES_CONFIRMADAS_PERIODO)) {
            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFinal()));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReporteVenta reporte = extraer(rs);
                this.agregarPasajerosReservacion(reporte);
                lista.add(reporte);
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("error al obtener las reservaciones confirmadas" + e.getMessage());
        }

        return lista;

    }

    public ReporteVenta extraer(ResultSet rs) throws SQLException {
        return new ReporteVenta(
                rs.getInt("rs_numero_reservacion"),
                rs.getDouble("rs_total_a_pagar"),
                rs.getString("empleado_nombre"),
                rs.getString("cliente_nombre"),
                rs.getString("cliente_id")
        );
    }
    
    
    private void agregarPasajerosReservacion(ReporteVenta reporte)throws ExceptionGenerica{
        reporte.setPasajeros(obtenerPasajerosPorReservacion(reporte.getIdReservacion()));
    }
    
    
    

    public ArrayList<PasajeroReservacion> obtenerPasajerosPorReservacion(int idReservacion) throws ExceptionGenerica {
        ArrayList<PasajeroReservacion> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(PASAJEROS_POR_RESERVACION)) {

            ps.setInt(1, idReservacion);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraerPasajero(rs));
                }
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener pasajeros de la DB: " + e.getMessage());
        }

        return lista;
    }


    private PasajeroReservacion extraerPasajero(ResultSet rs) throws SQLException {
        return new PasajeroReservacion(
                rs.getString("cliente_id"),
                rs.getString("cliente_nombre"),
                rs.getString("nacionalidad_nombre") 
        );
    }

}
