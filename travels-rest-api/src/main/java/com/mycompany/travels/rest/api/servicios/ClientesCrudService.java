/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.ClientesDB;
import com.mycompany.travels.rest.api.exceptions.EntidadDuplicadaException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.BusquedaUnitariaString;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.modelos.Cliente;

/**
 *
 * @author edu
 */
public class ClientesCrudService extends CrudService implements CreacionEntidad<Cliente>, EdicionEntidad<Cliente>,
        BusquedaUnitariaString<Cliente> {
    
    private final ClientesDB db = new ClientesDB();

    @Override
    public void crear(Cliente entidad) throws ExceptionGenerica {
        this.revisarDatosCorrectos(entidad);
        if(db.existeEntidad(entidad.getNombre())){
            throw new EntidadDuplicadaException("el identificador "+ entidad.getIdentificacion() + " ya está registrado");
        }
        
        if(db.existeAtributoRepetido(entidad.getCorreo(), db.getEXISTE_CORREO())){
            throw new EntidadDuplicadaException("el correo "+ entidad.getCorreo() + " ya está registrado");
        }
        if(db.existeAtributoRepetido(entidad.getTelefono(), db.getEXISTE_TELEFONO())){
            throw new EntidadDuplicadaException("el telefono "+ entidad.getTelefono() + " ya está registrado");
        }
        
        db.crear(entidad);
    }

    @Override
    public void editar(Cliente entidad) throws ExceptionGenerica {
        
        this.revisarDatosCorrectos(entidad);
        
        boolean telefonoRepetido = db.existeAtributoRepetido(entidad.getTelefono(), db.getEXISTE_TELEFONO());
        boolean correoRepetido = db.existeAtributoRepetido(entidad.getCorreo(), db.getEXISTE_CORREO());
        
        Cliente cliente = db.buscar(entidad.getIdentificacion());
        
    
        // si el telefono es el mismo per el id no
        if(telefonoRepetido && !cliente.getIdentificacion().equals(entidad.getIdentificacion())){
            throw new EntidadDuplicadaException("el correo "+ entidad.getCorreo() + " ya está registrado");
        }
        // si el correo es el mismo pero el id no
        if(correoRepetido && !cliente.getIdentificacion().equals(entidad.getIdentificacion())){
            throw new EntidadDuplicadaException("el telefono "+ entidad.getTelefono() + " ya está registrado");
        }
        
        db.editar(entidad);
    }

    @Override
    public Cliente buscar(String nombre) throws ExceptionGenerica {
        if(nombre == null || nombre.isBlank()){
            throw new   ExceptionGenerica("Busqueda vacia");
        }
        return db.buscar(nombre);
    }

}
