/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db;

import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.BuscarVariosInt;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.interfaces.ExtraerEntidad;
import com.mycompany.travels.rest.api.modelos.PagoReservacion;
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
public class PagosDB implements CreacionEntidad<PagoReservacion>, BuscarVariosInt<PagoReservacion>,
        ExtraerEntidad<PagoReservacion> {

    private static final String REGISTRAR_PAGO = "INSERT INTO "
            + "pago_reservacion (pago_cantidad, pago_id_reservacion, pago_fecha, pago_id_metodo) "
            + "VALUES (?, ?, ?, ?)";

    private static final String BUSCAR_POR_RESERVACION = "SELECT m.metodo_pago_nombre, p.pago_cantidad, p.pago_fecha "
            + "FROM pago_reservacion p "
            + "INNER JOIN metodo_pago m ON p.pago_id_metodo = m.metodo_pago_id "
            + "WHERE p.pago_id_reservacion = ?";

    private static final String OBTENER_COSTO_POR_RESERVACION
            = "SELECT paquete_precio FROM reservacion"
            + " JOIN paquete ON paquete_id = rs_id_paquete WHERE rs_numero_reservacion = ?";

    private static final String MARCAR_PAGADA = "UPDATE reservacion SET rs_id_estado = 4 WHERE rs_numero_reservacion = ?";

    private static final String ES_PAGADA = "SELECT *FROM reservacion WHERE rs_numero_reservacion = ? AND rs_id_estado = 4";
    
    
    private static final String OBTENER_TOTAL_PAGADO = "select rs_total_pagado from reservacion where rs_numero_reservacion = ?";
    
    private static final String CAMBIAR_ESTADO_RESERVACION = "UPDATE reservacion set rs_id_estado = ? where rs_numero_reservacion = ?";
    
    
    private static final String ACTUALIZAR_TOTAL_PAGADO = "UPDATE reservacion set rs_total_pagado = ? where rs_numero_reservacion = ?";

    @Override

    public void crear(PagoReservacion entidad) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(REGISTRAR_PAGO)) {

            ps.setDouble(1, entidad.getCantidad());
            ps.setInt(2, entidad.getIdReservacion());
            ps.setDate(3, Date.valueOf(entidad.getFechaPago()));
            ps.setInt(4, entidad.getId_metodo_pago());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al registrar pago: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<PagoReservacion> buscarVariosInt(int idReservacion) throws ExceptionGenerica {
        ArrayList<PagoReservacion> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_RESERVACION)) {

            ps.setInt(1, idReservacion);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
            return lista;

        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al buscar los pagos de la reservación: " + e.getMessage());
        }
    }

    @Override
    public PagoReservacion extraer(ResultSet rs) throws SQLException {
        return new PagoReservacion(
                rs.getString("metodo_pago_nombre"),
                rs.getDouble("pago_cantidad"),
                rs.getDate("pago_fecha").toLocalDate()
        );
    }

    public double obtenerCostoTotalReservacion(int idReservacion) throws ExceptionGenerica {

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(OBTENER_COSTO_POR_RESERVACION)) {

            ps.setInt(1, idReservacion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("paquete_precio");
                }
                throw new ExceptionGenerica("No se encontró  el costo del paquete ");
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al obtener el costo del paquete: " + e.getMessage());
        }
    }

    public void marcarComoPagada(int idReservacion) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(MARCAR_PAGADA)) {

            ps.setInt(1, idReservacion);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al actualizar el estado de la reservación: " + e.getMessage());
        }
    }

    public boolean reservacionEstaPagada(int idReservacion) throws ExceptionGenerica {

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ES_PAGADA)) {
            ps.setInt(1, idReservacion);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al verificar el estado de la reservación: " + e.getMessage());
        }
    }
    
    public void cambiarEstadoReservacion(int idEstado, int idReservacion) throws ExceptionGenerica {

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CAMBIAR_ESTADO_RESERVACION)) {
            ps.setInt(1, idEstado);
            ps.setInt(2, idReservacion);
            
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al cambiar estado de la reservación: " + e.getMessage());
        }
    }
    
    
    public double obtenerTotalPagado( int idReservacion) throws ExceptionGenerica {

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(OBTENER_TOTAL_PAGADO)) {
    
            ps.setInt(1, idReservacion);
            
            ResultSet rs  =ps.executeQuery();
            if(rs.next()){
                return rs.getDouble("rs_total_pagado");
            }
            
             throw new ExceptionGenerica("No se encontró el total pagado de la reservación");

        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al cambiar estado de la reservación: " + e.getMessage());
        }
    }
    
    
    public void actualizarTotalPagado(double totalPagado, int idReservacion) throws ExceptionGenerica{
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ACTUALIZAR_TOTAL_PAGADO)) {
            ps.setDouble(1, totalPagado);
            ps.setInt(2, idReservacion);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al cambiar estado de la reservación: " + e.getMessage());
        }
        
    }

}
