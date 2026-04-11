/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.ProveedorDB;
import com.mycompany.travels.rest.api.exceptions.EntidadDuplicadaException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.BuscarTodos;
import com.mycompany.travels.rest.api.interfaces.BusquedaUnitariaString;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.modelos.Proveedor;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class ProveedorCrudService extends CrudService implements CreacionEntidad<Proveedor>, EdicionEntidad<Proveedor>,
        BuscarTodos<Proveedor>, BusquedaUnitariaString<Proveedor> {

    private final ProveedorDB db = new ProveedorDB();

    @Override
    public void crear(Proveedor entidad) throws ExceptionGenerica {
        this.revisarDatosCorrectos(entidad);

        if (db.existeEntidad(entidad.getNombre())) {
            throw new EntidadDuplicadaException("ya existe " + entidad.getNombre());
        }

        int tipoServicio = entidad.getId_tipo_servicio();

        if (!(tipoServicio >= 1 && tipoServicio <= 5)) {
            throw new ExceptionGenerica("no existe el tipo de servicio con id " + tipoServicio);
        }

        db.crear(entidad);
    }

    @Override
    public void editar(Proveedor entidad) throws ExceptionGenerica {
        this.revisarDatosCorrectos(entidad);

        Proveedor prov = db.buscar(entidad.getNombre());
        if (prov != null && prov.getId() != entidad.getId()) {
            throw new EntidadDuplicadaException("ya existe " + entidad.getNombre());
        }

        db.editar(entidad);
    }

    @Override
    public ArrayList<Proveedor> buscarTodos() throws ExceptionGenerica {
        return db.buscarTodos();
    }

    @Override
    public Proveedor buscar(String nombre) throws ExceptionGenerica {
        if (nombre == null || nombre.isBlank()) {
            throw new ExceptionGenerica("Busqueda vacia");
        }

        Proveedor proveedor = db.buscar(nombre);
        if (proveedor == null) {
            throw new ExceptionGenerica("no se encontró el proveedor");
        }
        return proveedor;
    }
}
