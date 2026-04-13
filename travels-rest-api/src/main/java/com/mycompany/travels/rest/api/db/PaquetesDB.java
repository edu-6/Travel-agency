/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.db;

import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.BuscarTodos;
import com.mycompany.travels.rest.api.interfaces.BuscarVariosInt;
import com.mycompany.travels.rest.api.interfaces.BusquedaUnitariaString;
import com.mycompany.travels.rest.api.interfaces.CreacionReturnId;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.interfaces.ExisteEntidad;
import com.mycompany.travels.rest.api.interfaces.ExtraerEntidad;
import com.mycompany.travels.rest.api.modelos.Paquete;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PaquetesDB implements CreacionReturnId<Paquete>, EdicionEntidad<Paquete>,
        ExisteEntidad, BusquedaUnitariaString<Paquete>,
        ExtraerEntidad<Paquete>, BuscarTodos<Paquete>,
        BuscarVariosInt<Paquete> {

    private static final String CREAR = "INSERT INTO paquete "
            + "(paquete_nombre,"
            + "paquete_descripcion,"
            + "paquete_precio,"
            + "paquete_ganancia,"
            + "paquete_duracion,"
            + "paquete_capacidad_maxima,"
            + "paquete_id_destino,"
            + "paquete_activo) "
            + "VALUES (?,?,?,?,?,?,?,?)";

    private static final String EDITAR = "UPDATE paquete SET"
            + " paquete_nombre = ?,"
            + " paquete_descripcion = ?,"
            + " paquete_precio = ?,"
            + " paquete_ganancia= ?,"
            + " paquete_duracion = ?,"
            + " paquete_capacidad_maxima = ?,"
            + " paquete_id_destino = ?,"
            + " paquete_activo = ? "
            + " WHERE paquete_id = ?";

    private static final String EXISTE = "select paquete_id FROM paquete where paquete_nombre = ?";

    private static final String BUSCAR_UNO = "select paquete.*, destino_nombre FROM paquete"
            + " JOIN  destino ON paquete_id_destino = destino_id"
            + " WHERE paquete_nombre = ?";

    private static final String BUSCAR_UNO_POR_ID = "select paquete.*, destino_nombre FROM paquete"
            + " JOIN  destino ON paquete_id_destino = destino_id"
            + " WHERE paquete_id = ?";

    private static final String BUSCAR_TODOS = "select paquete.*, destino_nombre FROM paquete"
            + " JOIN  destino ON paquete_id_destino = destino_id";

    private static final String BUSCAR_POR_DESTINO = "select paquete.*, destino_nombre FROM paquete"
            + " JOIN  destino ON paquete_id_destino = destino_id where destino_id = ?";

    private static final String BUSCAR_PAQUETES_ACTIVOS = "select paquete.*, destino_nombre FROM paquete"
            + " JOIN  destino ON paquete_id_destino = destino_id where paquete_activo = 1";

    private static final String SUMAR_GASTOS = "SELECT SUM(servicio_paquete_costo) "
            + "FROM servicio_paquete "
            + "WHERE servicio_paquete_id_paquete = ?";

    private static final String OBTENER_PRECIO_PAQUETE = "select paquete_precio from paquete where paquete_id = ?";

    private static final String ACTUALIZAR_GANANCIA = "update paquete set paquete_ganancia = ? where paquete_id = ?";

    private static final String BUSCAR_SI_TIENE_ALTA_DEMANDA = "select rs_numero_reservacion from reservacion where rs_fecha_viaje >= ?"
            + " AND rs_id_paquete  = ? AND rs_cantidad_pasajeros >= ? AND rs_id_estado != 3";

    @Override
    public int crear(Paquete entidad) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getDescripcion());
            ps.setDouble(3, entidad.getPrecioVenta());
            ps.setDouble(4, entidad.getPrecioVenta()); // el mismo que el de la venta = ganancia
            ps.setInt(5, entidad.getDuracion());
            ps.setInt(6, entidad.getCapacidadMaxima());
            ps.setInt(7, entidad.getId_destino());
            ps.setBoolean(8, entidad.isActivo());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

            throw new ExceptionGenerica("falló al registrar paquete");

        } catch (SQLException e) {
            throw new ExceptionGenerica("falló al registrar paquete");
        }
    }

    @Override
    public void editar(Paquete entidad) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EDITAR)) {
            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getDescripcion());
            ps.setDouble(3, entidad.getPrecioVenta());
            ps.setDouble(4, entidad.getGanancia());
            ps.setInt(5, entidad.getDuracion());
            ps.setInt(6, entidad.getCapacidadMaxima());
            ps.setInt(7, entidad.getId_destino());
            ps.setBoolean(8, entidad.isActivo());
            ps.setInt(9, entidad.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("falló al actualizar paquete");
        }
    }

    @Override
    public boolean existeEntidad(String nombre) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EXISTE)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar paquete");
        }
    }

    @Override
    public Paquete buscar(String nombre) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_UNO)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extraer(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar paquete");
        }
    }

    public Paquete buscarPorId(int id) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_UNO_POR_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extraer(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar paquete");
        }
    }

    @Override
    public Paquete extraer(ResultSet rs) throws SQLException {
        return new Paquete(
                rs.getString("paquete_nombre"),
                rs.getString("paquete_descripcion"),
                rs.getString("destino_nombre"),
                rs.getInt("paquete_duracion"),
                rs.getInt("paquete_capacidad_maxima"),
                rs.getDouble("paquete_precio"),
                rs.getDouble("paquete_ganancia"),
                rs.getInt("paquete_id"),
                rs.getInt("paquete_id_destino"),
                rs.getBoolean("paquete_activo")
        );
    }

    @Override
    public ArrayList<Paquete> buscarTodos() throws ExceptionGenerica {
        return this.buscarTodos(BUSCAR_TODOS);
    }

    public ArrayList<Paquete> buscarPaquetesActivos() throws ExceptionGenerica {
        return this.buscarTodos(BUSCAR_PAQUETES_ACTIVOS);
    }

    private ArrayList<Paquete> buscarTodos(String sql) throws ExceptionGenerica {
        ArrayList<Paquete> lista = new ArrayList();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(extraer(rs));
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar paquetees");
        }
        return lista;
    }

    public double sumarGastosPaquete(int idPaquete) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(SUMAR_GASTOS)) {

            ps.setInt(1, idPaquete);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al sumar los gastos");
        }
    }

    public double obtenerPrecioPaquete(int idPaquete) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(OBTENER_PRECIO_PAQUETE)) {
            ps.setInt(1, idPaquete);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("precio_paquete");
                }
            }
            throw new ExceptionGenerica("Falló al buscar precio paquete");
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar precio pqeute");
        }
    }

    /**
     * Suma
     *
     * @param idPaquete
     * @param ganancia
     * @throws ExceptionGenerica
     */
    public void actualizarGanancia(int idPaquete, double ganancia) throws ExceptionGenerica {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ACTUALIZAR_GANANCIA)) {
            ps.setDouble(1, ganancia);
            ps.setInt(2, idPaquete);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionGenerica("falló al actualizar paquete");
        }
    }

    @Override
    public ArrayList<Paquete> buscarVariosInt(int param) throws ExceptionGenerica {
        ArrayList<Paquete> lista = new ArrayList();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_DESTINO)) {
            ps.setInt(1, param);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(extraer(rs));
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar paquetes por destino");
        }
        return lista;
    }

    public boolean paqueteTieneAltaDemanda(Paquete paquete) throws ExceptionGenerica {  
    /*
            private static final String BUSCAR_SI_TIENE_ALTA_DEMANDA = "select rs_numero_reservacion from reservacion where rs_fecha_viaje >= ?"
            + " AND rs_id_paquete  = ? AND rs_cantidad_pasajeros >= ? AND rs_id_estado != 3"
        */
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_SI_TIENE_ALTA_DEMANDA)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, paquete.getId());
            
            double porcentaje = paquete.getCapacidadMaxima()*80/100;
            ps.setDouble(3, porcentaje);
            try (ResultSet rs = ps.executeQuery()) {
               if(rs.next()) return true;
            }
        } catch (SQLException e) {
            throw new ExceptionGenerica("Falló al buscar la demanda del paquete");
        }
        return  false;

    }

}
