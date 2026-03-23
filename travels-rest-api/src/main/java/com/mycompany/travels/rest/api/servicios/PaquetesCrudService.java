/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.PaquetesDB;
import com.mycompany.travels.rest.api.exceptions.EntidadDuplicadaException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.BuscarTodos;
import com.mycompany.travels.rest.api.interfaces.BusquedaUnitariaString;
import com.mycompany.travels.rest.api.interfaces.CreacionReturnId;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.modelos.Paquete;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PaquetesCrudService extends CrudService implements CreacionReturnId<Paquete>, EdicionEntidad<Paquete>,
        BusquedaUnitariaString<Paquete>, BuscarTodos<Paquete> {

    private final PaquetesDB db = new PaquetesDB();

    @Override
    public int crear(Paquete entidad) throws ExceptionGenerica {

        this.revisarDatosCorrectos(entidad);

        if (db.existeEntidad(entidad.getNombre())) {
            throw new EntidadDuplicadaException("ya existe el paquete:  " + entidad.getNombre());
        }

        return db.crear(entidad);
    }

    @Override
    public void editar(Paquete entidad) throws ExceptionGenerica {
        this.revisarDatosCorrectos(entidad);

        //revisar repetido
        Paquete paquete = db.buscar(entidad.getNombre());
        if (paquete != null && paquete.getId() != entidad.getId()) {
            throw new EntidadDuplicadaException("ya existe el paquete:  " + entidad.getNombre());
        }
        
        // revisar ganancias correctas
        double precioVenta = db.obtenerPrecioPaquete(entidad.getId());
        double gastos = db.sumarGastosPaquete(entidad.getId());
        double ganancia = precioVenta - gastos;

        if (ganancia <= 0) {
            throw new ExceptionGenerica("El precio actual no compensa los gastos!  ganancia: " + ganancia);
        }

        entidad.setGanancia(ganancia); // poner nueva ganancia
        db.editar(entidad);
    }

    @Override
    public Paquete buscar(String nombre) throws ExceptionGenerica {
        if (nombre == null || nombre.isBlank()) {
            throw new ExceptionGenerica("Busqueda vacia");
        }
        
        return db.buscar(nombre);
    }

    @Override
    public ArrayList<Paquete> buscarTodos() throws ExceptionGenerica {
        return db.buscarTodos();
    }
    
    public ArrayList<Paquete> buscarPaquetesActivos() throws ExceptionGenerica {
        return db.buscarPaquetesActivos();
    }
    
    
    

}
