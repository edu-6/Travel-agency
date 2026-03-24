/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.modelos;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class Cancelacion {
    private LocalDate fechaCancelacion;
    private double cantidadReembolsada;
    private int idReservacion;

    public Cancelacion(LocalDate fechaCancelacion, double cantidadReembolsada, int idReservacion) {
        this.fechaCancelacion = fechaCancelacion;
        this.cantidadReembolsada = cantidadReembolsada;
        this.idReservacion = idReservacion;
    }

    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }

    public double getCantidadReembolsada() {
        return cantidadReembolsada;
    }

    public int getIdReservacion() {
        return idReservacion;
    }
    
    
}
