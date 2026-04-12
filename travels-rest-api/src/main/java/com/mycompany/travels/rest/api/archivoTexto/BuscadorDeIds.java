/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.archivoTexto;

import com.mycompany.travels.rest.api.db.ConexionDB;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class BuscadorDeIds {

    private static final String ID_PAIS_POR_NOMBRE = "select pais_id from pais where pais_nombre = ?";

    private static final String ID_DESTINO_POR_NOMBRE = "select destino_id from destino where destino_nombre = ?";
    
    private static final String ID_PAQUETE_POR_NOMBRE = "select paquete_id from paquete where paquete_nombre = ?";
    
    private static final String ID_PROVEEDOR_POR_NOMBRE = "select proveedor_id from proveedor where proveedor_nombre = ?";
    
    private static final String ID_NACIONALIDAD_POR_NOMBRE = "select nacionalidad_id from nacionalidad where nacionalidad_nombre = ?";
    
    private static final String ID_AGENTE_POR_NOMBRE = "select empleado_id from empleado where empleado_nombre = ? and empleado_id_rol = 1";
    

    public int buscarIdPais(String nombre) throws ExceptionGenerica {
        String columna = "pais_id";
        return this.buscarIdPorNombre(nombre, columna,ID_PAIS_POR_NOMBRE, "pais" );
    }

    public int buscarIdDestino(String nombre) throws ExceptionGenerica {
        String columna = "destino_id";
        return this.buscarIdPorNombre(nombre, columna,ID_DESTINO_POR_NOMBRE, "destino" );
    }

    public int buscarIdPaquete(String nombre) throws ExceptionGenerica {
        String columna = "paquete_id";
        return this.buscarIdPorNombre(nombre, columna,ID_PAQUETE_POR_NOMBRE, "paquete" );
    }

    public int buscarIdProveedor(String nombre) throws ExceptionGenerica {
        String columna = "proveedor_id";
        return this.buscarIdPorNombre(nombre, columna,ID_PROVEEDOR_POR_NOMBRE, "proveedor" );
    }
    public int buscarIdNacionalidad(String nombre) throws ExceptionGenerica {
        String columna = "nacionalidad_id";
        return this.buscarIdPorNombre(nombre, columna,ID_NACIONALIDAD_POR_NOMBRE, "nacionalidad" );
    }
    
    public int buscarIdAgente(String nombre) throws ExceptionGenerica {
        String columna = "empleado_id";
        return this.buscarIdPorNombre(nombre, columna,ID_AGENTE_POR_NOMBRE, "agente" );
    }

    private int buscarIdPorNombre(String nombre, String columna, String sql, String nombreEntidad) throws ExceptionGenerica {
        int id = -1;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(columna);
                }
            }
            return id;
        } catch (SQLException e) {
            throw new ExceptionGenerica("error al buscar id "+nombreEntidad+ " " + e.getMessage());
        }
    }

}
