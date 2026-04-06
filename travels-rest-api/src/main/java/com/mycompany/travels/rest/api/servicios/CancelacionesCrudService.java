/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.CancelacionesDB;
import com.mycompany.travels.rest.api.dtos.cancelaciones.CancelacionRequest;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.Cancelacion;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author edu
 */
public class CancelacionesCrudService {
    private final CancelacionesDB db = new CancelacionesDB();
    
    public void cancelarReservacion(CancelacionRequest cancelacion) throws ExceptionGenerica{
        
        LocalDate fechaViaje = db.buscarFechaViaje(cancelacion.getIdReservacion());
        
        verificarQuenoEsteCancelada(cancelacion.getIdReservacion());
        verificarPlazoLimite(fechaViaje);
        
        long diasRestantes = obtenerDiasRestantes(fechaViaje);
        double totalPagado = db.obtenerTotalPagado(cancelacion.getIdReservacion());
        double reembolso = calcularReemboso(totalPagado, diasRestantes);
        
        Cancelacion canc = new Cancelacion(cancelacion.getFechaCancelacion(),
        reembolso, cancelacion.getIdReservacion());
        
        db.registrarCancelacion(canc);
    }
    
    
    
    private void verificarQuenoEsteCancelada(int idReservacion) throws ExceptionGenerica{
        int estadoReservacion = db.buscarEstadoReservacion(idReservacion);
        if(estadoReservacion ==3){ // si no se encuentra en el rango
            throw new ExceptionGenerica("la reservación ya está cancelada");
        }
    }
    
    
    private void verificarPlazoLimite(LocalDate fechaViaje) throws ExceptionGenerica{
        
        long dias = obtenerDiasRestantes(fechaViaje);
        if(dias <= 7){
            throw new ExceptionGenerica("no puede cancelar a ultima hora!");
        }
    }
    
    
    private long obtenerDiasRestantes(LocalDate fechaViaje){
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaViaje);
    }
    
    private double calcularReemboso(double totalPagado, long diasRestantes){
        double reembolso;
        
        if(diasRestantes > 30){
            return totalPagado;
        }else if(diasRestantes >= 15 && diasRestantes < 30){
            return totalPagado*70/100;    
        }else if(diasRestantes >= 7 && diasRestantes <= 14){
            return totalPagado*40/100;
        }
        
        return 0;
    }
    

    
}
