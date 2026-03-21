/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db;

import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.exceptions.NotFoundException;
import com.mycompany.travels.rest.api.interfaces.BuscarVariosInt;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.interfaces.ExisteEntidad;
import com.mycompany.travels.rest.api.interfaces.ExtraerEntidad;
import com.mycompany.travels.rest.api.modelos.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.travels.rest.api.interfaces.BusquedaUnitariaString;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class EmpleadosDB implements CreacionEntidad<Empleado>, EdicionEntidad<Empleado>,
        ExisteEntidad, BusquedaUnitariaString<Empleado>, ExtraerEntidad<Empleado>,BuscarVariosInt<Empleado>{

    private static final String CREAR = "INSERT INTO empleado (empleado_nombre,empleado_contraseña,empleado_id_rol,empleado_activo) "
            + "VALUES (?,?,?,?)";
    private static final String EDITAR = "UPDATE empleado SET empleado_nombre = ?, empleado_contraseña = ?,"
            + "empleado_id_rol = ?, empleado_activo = ?  WHERE empleado_id = ?";
    
    private static final String EXISTE = "select empleado_id FROM empleado where empleado_nombre = ?";
    private static final String BUSCAR_UNO = "select empleado.*,rol_nombre FROM empleado"
            + " JOIN rol ON rol_id = empleado_id_rol  where empleado_nombre = ?";
    
    private static final String BUSCAR_POR_ROL = "SELECT empleado.*,rol_nombre FROM empleado"
            + " JOIN rol ON rol_id = empleado_id_rol  where rol_id = ?"
            ;
    @Override
    public void crear(Empleado entidad) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR)){
            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getContraseña());
            ps.setInt(3, entidad.getIdRol());
            ps.setBoolean(4, entidad.isActivo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("falló al registrar empleado");
        }
    }

    @Override
    public void editar(Empleado entidad) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EDITAR)){
            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getContraseña());
            ps.setInt(3, entidad.getIdRol());
            ps.setBoolean(4, entidad.isActivo());
            ps.setInt(5, entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("falló al actualizar empleado");
        }
    }

    @Override
    public boolean existeEntidad(String nombre) throws ExceptionGenerica {
         try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EXISTE)){
            ps.setString(1, nombre);
            ResultSet rs =  ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar empleado");
        }
    }


    @Override
    public Empleado buscar(String nombre) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_UNO)){
            ps.setString(1, nombre);
            ResultSet rs =  ps.executeQuery();
            if(rs.next()){
                return extraer(rs);
            }
            throw new NotFoundException("no se encontró el empleado");
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar empleado");
        }
    }

    @Override
    public Empleado extraer(ResultSet rs) throws SQLException {
        return new Empleado(
                rs.getString("empleado_nombre"),
                rs.getString("empleado_contraseña"),
                rs.getString("rol_nombre"),
                rs.getBoolean("empleado_activo"),
                rs.getInt("empleado_id"),
                rs.getInt("rol_id")
        );
    }

    @Override
    public ArrayList<Empleado> buscarVariosInt(int param) throws ExceptionGenerica {
        ArrayList<Empleado> lista = new ArrayList();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_ROL)) {

            ps.setInt(1, param);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(extraer(rs));
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar empleado");
        }
        return lista;
    }
}