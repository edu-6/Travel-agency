/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reportes;

import com.mycompany.travels.rest.api.dtos.reportes.paqueteMasVendido.DetalleReservacionPaquete;
import java.util.List;

/**
 *
 * @author edu
 */
public class ReportePaqueteMaxOMenosVendido {

    private int idPaquete;
    private String nombre;
    private String descripcion;
    private String mejorEpoca;

    private List<DetalleReservacionPaquete> reservaciones;

    public ReportePaqueteMaxOMenosVendido(int idPaquete, String nombre, String descripcion, String mejorEpoca) {
        this.idPaquete = idPaquete;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mejorEpoca = mejorEpoca;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMejorEpoca() {
        return mejorEpoca;
    }

    public void setMejorEpoca(String mejorEpoca) {
        this.mejorEpoca = mejorEpoca;
    }

    public List<DetalleReservacionPaquete> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<DetalleReservacionPaquete> reservaciones) {
        this.reservaciones = reservaciones;
    }
    
    

}
