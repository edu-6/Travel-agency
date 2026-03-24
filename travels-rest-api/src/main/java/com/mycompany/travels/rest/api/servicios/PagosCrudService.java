/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.PagosDB;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.interfaces.BuscarVariosInt;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.modelos.PagoReservacion;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PagosCrudService extends CrudService implements CreacionEntidad<PagoReservacion>, BuscarVariosInt<PagoReservacion> {
    
    private final PagosDB db = new PagosDB();

    @Override
    public void crear(PagoReservacion entidad) throws ExceptionGenerica {
        this.revisarDatosCorrectos(entidad);
        
        if(db.reservacionEstaPagada(entidad.getIdReservacion())){
            throw new ExceptionGenerica("La reservación ya fué pagada totalmente");
        }
        
        if(entidad.getCantidad() <= 0){
            throw new ExceptionGenerica("La cantidad debe ser mayor a 0  ");
            
        }
        
        double costoReservacion = db.obtenerCostoTotalPaquete(entidad.getIdReservacion());
        if(entidad.getCantidad() > costoReservacion){
            throw new ExceptionGenerica("la cantidad es mayor al precio total ");
        }else if(entidad.getCantidad() == costoReservacion){
            db.marcarComoPagada(entidad.getIdReservacion());
        }
        
        db.crear(entidad);
        
         
    }

    @Override
    public ArrayList<PagoReservacion> buscarVariosInt(int param) throws ExceptionGenerica {
        return db.buscarVariosInt(param);
    }
    
}
