/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db;

import com.mycompany.travels.rest.api.dtos.reservaciones.ReservacionRequest;
import com.mycompany.travels.rest.api.dtos.reservaciones.ReservacionResponse;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.BuscarVariosString;
import com.mycompany.travels.rest.api.interfaces.CreacionReturnId;
import com.mycompany.travels.rest.api.interfaces.ExtraerEntidad;
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
public class ReservacionesDB implements CreacionReturnId<ReservacionRequest>, BuscarVariosString<ReservacionResponse>,
        ExtraerEntidad<ReservacionResponse> {

    private static final String CREAR = "INSERT INTO reservacion (rs_id_titular, rs_cantidad_pasajeros, rs_id_agente_creador, "
            + "rs_id_estado, rs_fecha_creacion, rs_fecha_viaje, rs_total_pagado, rs_id_paquete, rs_total_a_pagar) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?,?, ?)";

    private static final String BUSCAR_SIMPLE = "select reservacion.*, cliente_nombre,"
            + "estado_reservacion_nombre, cliente_id, empleado_nombre, paquete_nombre,paquete_precio"
            + " FROM reservacion"
            + " join cliente ON  cliente_id = rs_id_titular"
            + " join paquete ON rs_id_paquete = paquete_id"
            + " join empleado ON empleado_id = rs_id_agente_creador"
            + " join estado_reservacion ON rs_id_estado = estado_reservacion_id";

    private static final String BUSCAR_PRECIO_PAQUETE = "select paquete_precio from paquete where paquete_id = ?";

    private static final String BUSCAR_POR_ID = BUSCAR_SIMPLE + " where rs_numero_reservacion = ?";

    private static final String BUSCAR_POR_CLIENTE = BUSCAR_SIMPLE
            + " where rs_id_titular = ? ORDER BY rs_fecha_creacion DESC";

    private static final String BUSCAR_HOY = BUSCAR_SIMPLE
            + " where rs_fecha_creacion = ? ";

    private static final String EXISTE_VIAJE_EN_FECHA = "select rs_id_agente_creador FROM reservacion where rs_fecha_viaje = ? and rs_id_titular = ?";

    @Override
    public int crear(ReservacionRequest entidad) throws ExceptionGenerica {
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(CREAR, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getIdTitular());
            ps.setInt(2, (entidad.getPasajeros().length));
            ps.setInt(3, entidad.getIdAgenteCreador());
            ps.setInt(4, 1);
            ps.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
            ps.setDate(6, java.sql.Date.valueOf(entidad.getFechaViaje()));
            ps.setDouble(7, 0.0);
            ps.setInt(8, entidad.getIdPaquete());
            ps.setDouble(9, entidad.getTotalAPagar());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new ExceptionGenerica("No se pudo obtener el ID de la reservación.");
            }

        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al crear la reservación: " + e.getMessage());
        }
    }

    public boolean existeViajeEnEstaFecha(LocalDate fecha, String idTitular) throws ExceptionGenerica {
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(EXISTE_VIAJE_EN_FECHA)) {
            ps.setDate(1, Date.valueOf(fecha));
            ps.setString(2, idTitular);

            try (ResultSet rs = ps.executeQuery();) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al buscar fecha disponible" + e.getMessage());
        }
    }

    public ReservacionResponse buscarUnaPorID(int parametro) throws ExceptionGenerica {
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(BUSCAR_POR_ID)) {
            ps.setInt(1, parametro);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return extraer(rs);
                }
                throw new ExceptionGenerica(" no se encontró la reservación");
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al buscar la reservación " + e.getMessage());
        }
    }

    /**
     * Para buscar todas las reservaciones de un usuario
     *
     * @param parametro
     * @return
     * @throws ExceptionGenerica
     */
    @Override
    public ArrayList<ReservacionResponse> buscarPorString(String parametro) throws ExceptionGenerica {
        ArrayList<ReservacionResponse> lista = new ArrayList();
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(BUSCAR_POR_CLIENTE)) {
            ps.setString(1, parametro);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al buscar historial de reservaciones " + e.getMessage());
        }
    }

    /**
     * Para buscar todas las reservaciones de hoy
     *
     * @return
     * @throws ExceptionGenerica
     */
    public ArrayList<ReservacionResponse> buscarReservacionesHoy() throws ExceptionGenerica {
        ArrayList<ReservacionResponse> lista = new ArrayList();
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(BUSCAR_HOY)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al buscar reservaciones del día : " + e.getMessage());
        }
    }

    @Override
    public ReservacionResponse extraer(ResultSet rs) throws SQLException {
        return new ReservacionResponse(
                rs.getInt("rs_numero_reservacion"),
                rs.getString("cliente_nombre"),
                rs.getString("paquete_nombre"),
                rs.getString("estado_reservacion_nombre"),
                rs.getDate("rs_fecha_creacion").toLocalDate(),
                rs.getDate("rs_fecha_viaje").toLocalDate(),
                rs.getDouble("rs_total_pagado"),
                rs.getDouble("rs_total_a_pagar"),
                rs.getInt("rs_id_estado")
        );
    }

    public double obtenerPrecioPaquete(int idPaquete) throws ExceptionGenerica {
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(BUSCAR_PRECIO_PAQUETE)) {
            ps.setInt(1, idPaquete);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("paquete_precio");
            }
            
            throw new ExceptionGenerica(" no se encontró el paquete ");

        } catch (SQLException e) {
            throw new ExceptionGenerica("Error al buscar reservaciones del día : " + e.getMessage());
        }
    }

}
