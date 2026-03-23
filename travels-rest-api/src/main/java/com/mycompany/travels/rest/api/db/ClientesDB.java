/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db;

import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.exceptions.NotFoundException;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.interfaces.ExisteEntidad;
import com.mycompany.travels.rest.api.interfaces.ExtraerEntidad;
import com.mycompany.travels.rest.api.modelos.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.travels.rest.api.interfaces.BusquedaUnitariaString;

/**
 *
 * @author edu
 */
public class ClientesDB implements CreacionEntidad<Cliente>, EdicionEntidad<Cliente>,
        ExisteEntidad, BusquedaUnitariaString<Cliente>, ExtraerEntidad<Cliente>{

    private static final String CREAR = "INSERT INTO cliente"
            + " (cliente_id,"
            + "cliente_nombre,"
            + "cliente_id_nacionalidad,"
            + "cliente_fecha_nacimiento,"
            + "cliente_telefono,"
            + "cliente_correo)"
            + " VALUES (?,?,?,?,?,?)";
    private static final String EDITAR = "UPDATE cliente SET cliente_nombre = ?, cliente_id_nacionalidad = ?,"
            + "cliente_fecha_nacimiento = ?, cliente_telefono = ?, cliente_correo = ?  WHERE cliente_id = ?";
    
    private static final String EXISTE = "select cliente_id FROM cliente WHERE cliente_id= ?";
    
    private static final String BUSCAR_UNO = "select cliente.*, nacionalidad_nombre FROM cliente"
            + " JOIN nacionalidad ON cliente_id_nacionalidad  = nacionalidad_id WHERE  cliente_id = ?";
    
    private final String EXISTE_TELEFONO = "select cliente_nombre from cliente where cliente_telefono = ?";
    private final String EXISTE_CORREO = "select cliente_nombre from cliente where cliente_correo = ?";
    
    @Override
    public void crear(Cliente entidad) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR)){
            ps.setString(1, entidad.getIdentificacion());
            ps.setString(2, entidad.getNombre());
            ps.setInt(3, entidad.getId_nacionalidad());
            ps.setString(4, entidad.getTelefono());
            ps.setString(5, entidad.getCorreo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("falló al registrar cliente");
        }
    }

    @Override
    public void editar(Cliente entidad) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EDITAR)){
            ps.setString(1, entidad.getIdentificacion());
            ps.setString(2, entidad.getNombre());
            ps.setInt(3, entidad.getId_nacionalidad());
            ps.setString(4, entidad.getTelefono());
            ps.setString(5, entidad.getCorreo());
            ps.setString(6, entidad.getIdentificacion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("falló al actualizar cliente");
        }
    }

    @Override
    public boolean existeEntidad(String nombre) throws ExceptionGenerica {
         try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EXISTE)){
            ps.setString(1, nombre);
            ResultSet rs =  ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar cliente");
        }
    }


    @Override
    public Cliente buscar(String nombre) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_UNO)){
            ps.setString(1, nombre);
            ResultSet rs =  ps.executeQuery();
            if(rs.next()){
                return extraer(rs);
            }
            throw new NotFoundException("no se encontró el cliente");
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar cliente");
        }
    }

    @Override
    public Cliente extraer(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getString("cliente_id"),
                rs.getString("cliente_nombre"),
                rs.getString("cliente_correo"),
                rs.getString("cliente_telefono"),
                rs.getDate("cliente_fecha_nacimiento").toLocalDate(),
                rs.getString("cliente_identificacion"),
                rs.getString("nacionalidad_nombre"),
                rs.getInt("cliente_id_nacionalidad")
        );
    }
    
    
    public boolean existeAtributoRepetido(String atributo, String sql) throws ExceptionGenerica{
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, atributo);
            ResultSet rs =  ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar atributo repetido");
        }
    }

    public  String getEXISTE_TELEFONO() {
        return EXISTE_TELEFONO;
    }

    public  String getEXISTE_CORREO() {
        return EXISTE_CORREO;
    }
    
    
}