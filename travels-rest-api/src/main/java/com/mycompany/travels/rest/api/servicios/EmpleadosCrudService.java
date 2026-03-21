/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.EmpleadosDB;
import com.mycompany.travels.rest.api.exceptions.CamposVaciosException;
import com.mycompany.travels.rest.api.exceptions.DatosMuyLargosException;
import com.mycompany.travels.rest.api.exceptions.EntidadDuplicadaException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.modelos.Empleado;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class EmpleadosCrudService implements CreacionEntidad<Empleado>, EdicionEntidad<Empleado> {
    
    private final EmpleadosDB db = new EmpleadosDB();

    @Override
    public void crear(Empleado entidad) throws ExceptionGenerica {
        
        if(!entidad.datosCompletos()){
            throw new CamposVaciosException();
        }
        if(!entidad.datosTamañoCorrecto()){
            throw new DatosMuyLargosException();
        }
        if(db.existeEntidad(entidad.getNombre())){
            throw new EntidadDuplicadaException("El usuario "+ entidad.getNombre() + " ya existe!");
        }
        
        db.crear(entidad);
        
    }

    @Override
    public void editar(Empleado entidad) throws ExceptionGenerica {
        if(!entidad.datosCompletos()){
            throw new CamposVaciosException();
        }
        if(!entidad.datosTamañoCorrecto()){
            throw new DatosMuyLargosException();
        }
        
        // si hay un nombre igual, verificar que tengan el mismo ID
        Empleado empleado = db.buscar(entidad.getNombre());
        if(empleado != null && empleado.getId() != entidad.getId()){
            throw new EntidadDuplicadaException("El usuario "+ entidad.getNombre() + " ya existe!");
        }
        db.editar(entidad);
    }
    
    
    public ArrayList<Empleado> buscarEmpleadosPorRol(int rol) throws ExceptionGenerica{
        return db.buscarVariosInt(rol);
    }
    
    public Empleado buscarEmpleado(String nombre) throws ExceptionGenerica{
        if(nombre == null || nombre.isBlank()){
            throw new ExceptionGenerica("Busqueda vacia");
        }
        
        return db.buscar(nombre);
    }
    
}
