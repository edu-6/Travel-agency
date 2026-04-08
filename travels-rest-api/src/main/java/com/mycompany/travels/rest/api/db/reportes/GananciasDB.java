/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db.reportes;

import com.mycompany.travels.rest.api.db.ConexionDB;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.ReporteRequest;
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
public class GananciasDB {

       private static final String INGRESOS = "select  SUM(pago_cantidad)"
            + " as ingresos from pago_reservacion";
    
    private static final String EGRESOS = "select SUM(cancelacion_cantidad_rembolso)"
            + " as egresos from cancelacion ";
    
    private static final String INGRESOS_FILTRO_FECHA = INGRESOS + " where pago_fecha  >= ? and pago_fecha <= ?";
    private static final String ENGRESOS_FILTRO_FECHA = EGRESOS + " WHERE cancelacion_fecha >= ? AND cancelacion_fecha <= ?";
    
    
    
    
    
    public double obtenerIngresos() throws ExceptionGenerica{
        try(Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INGRESOS)) {
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
                return rs.getDouble("ingresos");
            }
            return 0;
        } catch (SQLException e) {
            throw new ExceptionGenerica(" error al obtener ingresos "+ e.getMessage());
        }
    }
    
    
    public double obtenerEgresos() throws ExceptionGenerica{
        try(Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EGRESOS)) {
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
                return rs.getDouble("egresos");
            }
            return 0;
        } catch (SQLException e) {
            throw new ExceptionGenerica(" error al obtener egresos "+ e.getMessage());
        }
    }
    
    
    public double obtenerIngresosPeriodo(ReporteRequest request) throws ExceptionGenerica{
        try(Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INGRESOS_FILTRO_FECHA)) {
            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
                return rs.getDouble("ingresos");
            }
            return 0;
        } catch (SQLException e) {
            throw new ExceptionGenerica(" error al obtener ingresos por periodo "+ e.getMessage());
        }
    }
    
    
    public double obtenerEgresosPeriodo(ReporteRequest request) throws ExceptionGenerica{
        try(Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ENGRESOS_FILTRO_FECHA)) {
            
            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            
            
            ResultSet rs =ps.executeQuery();
            
            if(rs.next()){
                return rs.getDouble("egresos");
            }
            return 0;
        } catch (SQLException e) {
            throw new ExceptionGenerica(" error al obtener egresos por periodo "+ e.getMessage());
        }
    }
    

}
