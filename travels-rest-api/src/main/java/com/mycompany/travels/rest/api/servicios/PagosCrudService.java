/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.PagosDB;
import com.mycompany.travels.rest.api.dtos.reservaciones.ReservacionResponse;
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
    private final ReservacionesCrudService reservacionesService = new ReservacionesCrudService();

    @Override
    public void crear(PagoReservacion entidad) throws ExceptionGenerica {
        this.revisarDatosCorrectos(entidad);
        
        //lanza exception si no la encuentra
        ReservacionResponse reservacion = reservacionesService.buscarUnaPorID(entidad.getIdReservacion());
        
        if(entidad.getFechaPago().isAfter(reservacion.getFechaViaje())){
            throw new ExceptionGenerica("No se puede pagar después de la fecha de viaje !");
        }
        
        if(entidad.getFechaPago().isBefore(reservacion.getFechaCreacion())){
            throw new ExceptionGenerica("Error en la logica de fechas, está realizando un pago cuando la reservación no existia!");
        }
        
        if(db.reservacionEstaPagada(entidad.getIdReservacion())){
            throw new ExceptionGenerica("La reservación ya fué pagada totalmente");
        }
        
        if(entidad.getCantidad() <= 0){
            throw new ExceptionGenerica("La cantidad debe ser mayor a 0  ");
        }
        
        double cantidadPago = entidad.getCantidad();
        double totalPagado = db.obtenerTotalPagado(entidad.getIdReservacion());
        double costoReservacion = db.obtenerCostoTotalReservacion(entidad.getIdReservacion());
        double pagoRestante = costoReservacion-totalPagado;
        
        if(cantidadPago > costoReservacion){
            throw new ExceptionGenerica("la cantidad es mayor al precio total ");
        }
        
        if(cantidadPago > pagoRestante){
            throw new ExceptionGenerica("la cantidad es mayor a lo que queda por pagar restante:  "+ pagoRestante);
        }
        
        // realizar pago
        db.crear(entidad);
        
       
        pagoRestante = pagoRestante -cantidadPago;
        
        
        //cambios de estado
        int estado = 1;
        if(pagoRestante > 0 && pagoRestante < costoReservacion){ // ya emepzó a pagar
            estado = 2;
        }
        
        db.cambiarEstadoReservacion(estado, entidad.getIdReservacion());
        
        totalPagado+=cantidadPago;
        db.actualizarTotalPagado(totalPagado, entidad.getIdReservacion());

        
    }

    @Override
    public ArrayList<PagoReservacion> buscarVariosInt(int param) throws ExceptionGenerica {
        return db.buscarVariosInt(param);
    }
    
}
