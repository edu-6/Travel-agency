/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reportes.paqueteMasVendido;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class DetalleReservacionPaquete {
    private int idReservacion;
    private String nombrePaquete;
    private String nombreTitular;
    private LocalDate fechaCreacion;
    private double totalAPagar;
    private double totalPagado;
    private String estado;

    public DetalleReservacionPaquete(int idReservacion, String nombrePaquete, String nombreTitular, LocalDate fechaCreacion, double totalAPagar, double totalPagado, String estado) {
        this.idReservacion = idReservacion;
        this.nombrePaquete = nombrePaquete;
        this.nombreTitular = nombreTitular;
        this.fechaCreacion = fechaCreacion;
        this.totalAPagar = totalAPagar;
        this.totalPagado = totalPagado;
        this.estado = estado;
    }

    public int getIdReservacion() {
        return idReservacion;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public double getTotalAPagar() {
        return totalAPagar;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public void setNombrePaquete(String nombrePaquete) {
        this.nombrePaquete = nombrePaquete;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setTotalAPagar(double totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
