/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db;

import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.exceptions.NotFoundException;
import com.mycompany.travels.rest.api.interfaces.BuscarVariosInt;
import com.mycompany.travels.rest.api.interfaces.BusquedaUnitariaString;
import com.mycompany.travels.rest.api.interfaces.CreacionReturnId;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.interfaces.EliminacionEntidad;
import com.mycompany.travels.rest.api.interfaces.ExtraerEntidad;
import com.mycompany.travels.rest.api.modelos.Paquete_servicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PaqueteServicioDB implements CreacionReturnId<Paquete_servicio>, EdicionEntidad<Paquete_servicio>,
         ExtraerEntidad<Paquete_servicio>,
        BuscarVariosInt<Paquete_servicio>, EliminacionEntidad {

    private static final String CREAR = "INSERT INTO servicio_paquete "
            + "(servicio_paquete_descripcion,"
            + "servicio_paquete_costo,"
            + "servicio_paquete_id_paquete,"
            + "servicio_paquete_id_proveedor) "
            + "VALUES (?,?,?,?)";

    private static final String EDITAR = "UPDATE servicio_paquete SET"
            + " servicio_paquete_descripcion = ?,"
            + " servicio_paquete_costo = ?,"
            + " servicio_paquete_id_paquete = ?,"
            + " servicio_paquete_id_proveedor= ?"
            + " WHERE servicio_paquete_id = ?";

    private static final String BUSCAR_TODOS_PAQUETE = "select servicio_paquete.*, proveedor_nombre, paquete_nombre FROM servicio_paquete"
            + " JOIN  proveedor ON servicio_paquete_id_proveedor = proveedor_id"
            + " JOIN  paquete ON servicio_paquete_id_paquete = paquete_id"
            + " WHERE servicio_paquete_id_paquete = ?";

    private static final String BUSCAR_UNO = "select servicio_paquete.*, proveedor_nombre, paquete_nombre FROM servicio_paquete"
            + " JOIN  proveedor ON servicio_paquete_id_proveedor = proveedor_id"
            + " JOIN  paquete ON servicio_paquete_id_paquete = paquete_id"
            + " where servicio_paquete_id = ?";
    
    
    private static final String ELIMINAR = "delete from servicio_paquete where servicio_paquete_id = ?";

    @Override
    public int crear(Paquete_servicio entidad) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getDescripcion());
            ps.setDouble(2, entidad.getPrecio());
            ps.setInt(3, entidad.getId_paquete());
            ps.setInt(4, entidad.getId_proveedor());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys();) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new ExceptionGenerica("falló al registrar servicio");

        } catch (SQLException e) {
            throw new ExceptionGenerica("falló al registrar servicio "+ e.getMessage());
        }
    }

    @Override
    public void editar(Paquete_servicio entidad) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EDITAR)) {
            ps.setString(1, entidad.getDescripcion());
            ps.setDouble(2, entidad.getPrecio());
            ps.setInt(3, entidad.getId_paquete());
            ps.setInt(4, entidad.getId_proveedor());
            ps.setInt(5, entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("falló al actualizar servicio "+ e.getMessage());
        }
    }
    
    @Override
    public ArrayList<Paquete_servicio> buscarVariosInt(int param) throws ExceptionGenerica {
        ArrayList<Paquete_servicio> lista = new ArrayList();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_TODOS_PAQUETE)) {
            ps.setInt(1, param);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(extraer(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar servicio "+ e.getMessage());
        }
    }
    

    public Paquete_servicio buscarPorId(int id) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_UNO)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extraer(rs);
            }
            throw new NotFoundException("no se encontró el servicio");
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar servicio "+ e.getMessage());
        }
    }

    @Override
    public Paquete_servicio extraer(ResultSet rs) throws SQLException {
        return new Paquete_servicio(
                rs.getString("servicio_paquete_descripcion"),
                rs.getDouble("servicio_paquete_costo"),
                rs.getInt("servicio_paquete_id_proveedor"),
                rs.getInt("servicio_paquete_id_paquete"),
                rs.getInt("servicio_paquete_id"),
                rs.getString("paquete_nombre"),
                rs.getString("proveedor_nombre")
        );
    }

    @Override
    public void eliminar(int id) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ELIMINAR)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al eliminar servicio "+ e.getMessage());
        }
        
    }
}
