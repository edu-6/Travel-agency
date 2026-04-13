/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reservaciones;
import com.mycompany.travels.rest.api.dtos.pasajeros.PasajeroResponse;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class ReservacionResponse {
    
    private int id;
    private String titular;
    private String nombrePaquete;
    private String estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaViaje;
    private double totalPagado;
    private double precioTotal;
    private int idEstado;
    private boolean cancelada;
    private boolean pagadaCompletamente;
    
    private ArrayList<PasajeroResponse> pasajeros;

    public ReservacionResponse(int id, String titular, String nombrePaquete, String estado, LocalDate fechaCreacion,
            LocalDate fechaViaje, double totalPagado, double precioTotal, int rs_id_estado) {
        this.id = id;
        this.titular = titular;
        this.nombrePaquete = nombrePaquete;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaViaje = fechaViaje;
        this.totalPagado = totalPagado;
        this.precioTotal = precioTotal;
        this.idEstado = rs_id_estado;
        
        this.cancelada = (idEstado == 3);
        this.pagadaCompletamente = (idEstado == 2);
    }

    public ReservacionResponse() {
    }
    
    

    public int getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    public ArrayList<PasajeroResponse> getPasajeros() {
        return pasajeros;
    }

    

    public void setId(int id) {
        this.id = id;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void setNombrePaquete(String nombrePaquete) {
        this.nombrePaquete = nombrePaquete;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaViaje(LocalDate fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public void setPasajeros(ArrayList<PasajeroResponse> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
    
    
    
    
    
    
    
    
    
}
