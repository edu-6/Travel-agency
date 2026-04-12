/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.PaqueteServicioDB;
import com.mycompany.travels.rest.api.db.PaquetesDB;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.BuscarVariosInt;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.interfaces.EliminacionEntidad;
import com.mycompany.travels.rest.api.modelos.Paquete;
import com.mycompany.travels.rest.api.modelos.Paquete_servicio;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PaqueteServicioCService extends CrudService implements CreacionEntidad<Paquete_servicio>, EdicionEntidad<Paquete_servicio>,
         BuscarVariosInt<Paquete_servicio>, EliminacionEntidad{

    private final PaquetesDB paquetesDB = new PaquetesDB();
    private final PaqueteServicioDB db = new PaqueteServicioDB();

    @Override
    public void crear(Paquete_servicio entidad) throws ExceptionGenerica {
        this.revisarDatosCorrectos(entidad);
        db.crear(entidad);
    }
    
    

    @Override
    public void editar(Paquete_servicio entidad) throws ExceptionGenerica {
        this.revisarDatosCorrectos(entidad);
        db.editar(entidad);

    }

    public Paquete_servicio buscarPorId(int id) throws ExceptionGenerica {
        return db.buscarPorId(id);

    }

    @Override
    public ArrayList<Paquete_servicio> buscarVariosInt(int param) throws ExceptionGenerica {
        return db.buscarVariosInt(param);
    }

    @Override
    public void eliminar(int id) throws ExceptionGenerica {
        Paquete_servicio servicio = this.buscarPorId(id);
        double costoPaquete = servicio.getPrecio();
        db.eliminar(id);
        
        
        Paquete paquete = paquetesDB.buscarPorId(id);
        
        double gananciaPaquete = paquete.getGanancia();
        
        gananciaPaquete+= costoPaquete;
        
        // actualizar las ganancias
        paquetesDB.actualizarGanancia(paquete.getId(), gananciaPaquete);
        
        
    }

}
