/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db.reportes;

import com.mycompany.travels.rest.api.db.ConexionDB;
import com.mycompany.travels.rest.api.dtos.reportes.ReporteCancelacion;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.ReporteRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class ReproteCancelacionesDB {

    private static final String BUSCAR_CANCELACIONES = 
    "SELECT r.rs_numero_reservacion, r.rs_total_pagado, " +
    "c.cancelacion_fecha, c.cancelacion_cantidad_rembolso " +
    "FROM cancelacion c " +
    "JOIN reservacion r ON c.cancelacion_id_reservacion = r.rs_numero_reservacion";

    private static final String FILTRO_FECHA = " WHERE cancelacion_fecha >= ? AND cancelacion_fecha <= ? ";

    private static final String BUSCAR_CANCELACIONES_POR_FECHA = BUSCAR_CANCELACIONES + FILTRO_FECHA;

    public ArrayList<ReporteCancelacion> obtenerCancelaciones() throws ExceptionGenerica {

        ArrayList<ReporteCancelacion> lista = new ArrayList();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_CANCELACIONES)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(extraer(rs));
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("error al obtener cancelaciones generales" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ReporteCancelacion> obtenerCancelacionesPeriodo(ReporteRequest request) throws ExceptionGenerica {

        ArrayList<ReporteCancelacion> lista = new ArrayList();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_CANCELACIONES_POR_FECHA)) {
            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(extraer(rs));
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("errro al obtener cancelaciones por fechas" + e.getMessage());
        }
        return lista;
    }

    private ReporteCancelacion extraer(ResultSet rs) throws SQLException {
    int idReservacion = rs.getInt("rs_numero_reservacion");
    LocalDate fechaCancel = rs.getDate("cancelacion_fecha").toLocalDate();
    double reembolsado = rs.getDouble("cancelacion_cantidad_rembolso");
    double totalPagado = rs.getDouble("rs_total_pagado");
    
    
    
    double perdida = totalPagado - reembolsado;
    if(perdida ==0){
        perdida = totalPagado;
    }

    return new ReporteCancelacion(
            idReservacion,
            fechaCancel,
            reembolsado,
            perdida
    );
}

}
