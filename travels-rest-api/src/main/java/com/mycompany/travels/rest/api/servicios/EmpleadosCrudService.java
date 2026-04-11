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
import com.mycompany.travels.rest.api.interfaces.EliminacionEntidad;
import com.mycompany.travels.rest.api.modelos.Empleado;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class EmpleadosCrudService extends CrudService implements CreacionEntidad<Empleado>, EdicionEntidad<Empleado>,
        EliminacionEntidad{
    
    private final EmpleadosDB db = new EmpleadosDB();

    @Override
    public void crear(Empleado entidad) throws ExceptionGenerica {
        
        if(entidad == null) throw new ExceptionGenerica("Error, se recibió un empleado vacio");
        
        if(entidad.getIdRol() <= 0 && entidad.getIdRol() >=4) throw new ExceptionGenerica("error, el rol"+ entidad.getId()+ " no existe");
        
        revisarDatosCorrectos(entidad);
        
        if(entidad.getContraseña().length() < 6){
            throw new ExceptionGenerica(" la contraseña debe ser de 6 o mas caracteres");
        }
        
        if(db.existeEntidad(entidad.getNombre())){
            throw new EntidadDuplicadaException("El usuario "+ entidad.getNombre() + " ya existe!");
        }
       
        db.crear(entidad);
        
    }

    @Override
    public void editar(Empleado entidad) throws ExceptionGenerica {
        
        if(!entidad.datosCompletosEditar()){
             throw new CamposVaciosException();
        }
        if(!entidad.datosTamañoCorrectoEditar()){
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
        
        Empleado empleado = db.buscar((nombre));
        if(empleado == null){
            throw new ExceptionGenerica(" no encontró el empleado");
        }
        return empleado;
    }

    @Override
    public void eliminar(int id) throws ExceptionGenerica {
        db.eliminar(id);
    }
    
}
