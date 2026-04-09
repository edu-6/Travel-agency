/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reportes;

import com.mycompany.travels.rest.api.dtos.reportes.ventas.PasajeroReservacion;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class ReporteVenta {

    private int idReservacion;
    private double totalAPagar;
    private String nombreAgente;
    private String nombreTitular;
    private String idTitular;

    private ArrayList<PasajeroReservacion> pasajeros;

    public ReporteVenta(int idReservacion, double totalAPagar, String nombreAgente, String nombreTitular, String idTitular) {
        this.idReservacion = idReservacion;
        this.totalAPagar = totalAPagar;
        this.nombreAgente = nombreAgente;
        this.nombreTitular = nombreTitular;
        this.idTitular = idTitular;
    }

    

    public int getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public double getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(double totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public String getNombreAgente() {
        return nombreAgente;
    }

    public void setNombreAgente(String nombreAgente) {
        this.nombreAgente = nombreAgente;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public ArrayList<PasajeroReservacion> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(ArrayList<PasajeroReservacion> pasajeros) {
        this.pasajeros = pasajeros;
    }
    
    

}
