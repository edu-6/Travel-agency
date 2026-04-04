/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reservaciones;

import com.mycompany.travels.rest.api.dtos.pasajeros.PasajeroRequest;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class ReservacionRequest {
    
    private String idTitular;
    private int idPaquete;
    private int idAgenteCreador;
    private LocalDate fechaCreacion;
    private LocalDate fechaViaje;
    
    private String [] pasajeros;
    
    private ArrayList<PasajeroRequest> pasajerosRequest;

    public ReservacionRequest() {
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public int getIdAgenteCreador() {
        return idAgenteCreador;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }
    

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public void setIdAgenteCreador(int idAgenteCreador) {
        this.idAgenteCreador = idAgenteCreador;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaViaje(LocalDate fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public String[] getPasajeros() {
        return pasajeros;
    }

    public ArrayList<PasajeroRequest> getPasajerosRequest() {
        return pasajerosRequest;
    }
    
    
    



    public String getIdTitular() {
        return idTitular;
    }
    
    public void generarPasajerosRequest(int idReservacion){
        
        this.pasajerosRequest = new ArrayList();
        
        // agregar al titular como pasajero
        pasajerosRequest.add(new PasajeroRequest(idTitular, idReservacion));
        
        if(pasajeros != null){
            for (String pasajero : pasajeros) {
                pasajerosRequest.add(new PasajeroRequest(pasajero, idReservacion));
            }
        }
    }
    
    
    
    
    
    
    
    
}
