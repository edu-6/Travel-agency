/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.DestinosDB;
import com.mycompany.travels.rest.api.exceptions.EntidadDuplicadaException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.BuscarTodos;
import com.mycompany.travels.rest.api.interfaces.BusquedaUnitariaString;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.interfaces.EliminacionEntidad;
import com.mycompany.travels.rest.api.modelos.Destino;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class DestinosCrudService extends CrudService implements CreacionEntidad<Destino>, EdicionEntidad<Destino>,
        BusquedaUnitariaString<Destino>,
         BuscarTodos<Destino>, EliminacionEntidad {
    
    private final DestinosDB db = new DestinosDB();

    @Override
    public void crear(Destino entidad) throws ExceptionGenerica {
        this.revisarDatosCorrectos(entidad);
        
        if(db.existeEntidad(entidad.getNombre())){
            throw new EntidadDuplicadaException("ya existe el destino: "+ entidad.getNombre());
        }
        
        
        if (entidad.getId_pais() <= 0) {
            throw new ExceptionGenerica("no existe el pais " + entidad.getId_pais());
        }
        
        
        db.crear(entidad);
    }

    @Override
    public void editar(Destino entidad) throws ExceptionGenerica {
        this.revisarDatosCorrectos(entidad);
        Destino destino = db.buscar(entidad.getNombre());
        if(destino != null && destino.getId() != entidad.getId()){
            throw new EntidadDuplicadaException("ya existe el destino: "+ entidad.getNombre());
        }
        db.editar(entidad);
    }

    @Override
    public Destino buscar(String nombre) throws ExceptionGenerica {
        if(nombre == null || nombre.isBlank()){
            throw new ExceptionGenerica("Busqueda vacia");
        }
        
        Destino destino = db.buscar(nombre);
        if(destino == null){
            throw new ExceptionGenerica("No se encontró el destino");
        }
        
        return destino;
    }
    

    @Override
    public ArrayList<Destino> buscarTodos() throws ExceptionGenerica {
        return db.buscarTodos();
    }

    @Override
    public void eliminar(int id) throws ExceptionGenerica {
        db.eliminar(id);
    }

}
