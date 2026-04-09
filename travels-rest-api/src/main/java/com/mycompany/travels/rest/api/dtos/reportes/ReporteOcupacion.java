/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reportes;

/**
 *
 * @author edu
 */
public class ReporteOcupacion {
    private String nombreDestino;
    private int cantidadReservaciones;

    public ReporteOcupacion(String nombreDestino, int cantidadReservaciones) {
        this.nombreDestino = nombreDestino;
        this.cantidadReservaciones = cantidadReservaciones;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

    public int getCantidadReservaciones() {
        return cantidadReservaciones;
    }

    public void setCantidadReservaciones(int cantidadReservaciones) {
        this.cantidadReservaciones = cantidadReservaciones;
    }
    
    
}
