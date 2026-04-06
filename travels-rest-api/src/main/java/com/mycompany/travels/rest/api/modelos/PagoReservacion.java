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
public class PagoReservacion extends Entidad {
    private String metodoPago;
    private double cantidad;
    private int idReservacion;
    private int id_metodo_pago;
    private int id;
    private LocalDate fechaPago;

    // para crear
    public PagoReservacion(double cantidad, int idReservacion, int id_metodo_pago, LocalDate fechaPago) {
        this.cantidad = cantidad;
        this.idReservacion = idReservacion;
        this.id_metodo_pago = id_metodo_pago;
        this.fechaPago = fechaPago;
    }

    public PagoReservacion(String metodoPago, double cantidad, LocalDate fechaPago) {
        this.metodoPago = metodoPago;
        this.cantidad = cantidad;
        this.fechaPago = fechaPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public double getCantidad() {
        return cantidad;
    }

    public int getIdReservacion() {
        return idReservacion;
    }

    public int getId_metodo_pago() {
        return id_metodo_pago;
    }

    public int getId() {
        return id;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    @Override
    public boolean datosCompletos() {
        return fechaPago != null;
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return true;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public void setId_metodo_pago(int id_metodo_pago) {
        this.id_metodo_pago = id_metodo_pago;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    
}
