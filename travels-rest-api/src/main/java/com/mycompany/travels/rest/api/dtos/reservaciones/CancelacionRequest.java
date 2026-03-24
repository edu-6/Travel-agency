/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reservaciones;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class CancelacionRequest {
    private int idReservacion;
    private LocalDate fechaCancelacion;

    public CancelacionRequest() {
    }

    public CancelacionRequest(int idReservacion, LocalDate fechaCancelacion) {
        this.idReservacion = idReservacion;
        this.fechaCancelacion = fechaCancelacion;
    }
    
    

    public int getIdReservacion() {
        return idReservacion;
    }

    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }
    
    
}
