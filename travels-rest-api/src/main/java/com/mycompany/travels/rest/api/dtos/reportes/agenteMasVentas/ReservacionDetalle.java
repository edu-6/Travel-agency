/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reportes.agenteMasVentas;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class ReservacionDetalle {
    
    private int idReservacion;
    private double totalAPagar;
    private LocalDate fechaCreacion;

    public ReservacionDetalle(int idReservacion, double totalAPagar, LocalDate fechaCreacion) {
        this.idReservacion = idReservacion;
        this.totalAPagar = totalAPagar;
        this.fechaCreacion = fechaCreacion;
    }
    
    
    
    
    
}
