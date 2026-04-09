/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reportes.agenteMasVentas;

import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class ReporteAgenteMasVentas {
   
    private String nombreAgente;
    private int idAgente;

    
    private ArrayList<ReservacionDetalle> reservaciones;
    
    public ReporteAgenteMasVentas(String nombreAgente, int idAgente) {
        this.nombreAgente = nombreAgente;
        this.idAgente = idAgente;
    }


    public ArrayList<ReservacionDetalle> getReservaciones() {
        return reservaciones;
    }



    public void setReservaciones(ArrayList<ReservacionDetalle> reservaciones) {
        this.reservaciones = reservaciones;
    }

    public String getNombreAgente() {
        return nombreAgente;
    }

    public void setNombreAgente(String nombreAgente) {
        this.nombreAgente = nombreAgente;
    }

    public int getIdAgente() {
        return idAgente;
    }

    public int setIdAgente(int idAgente) {
         return this.idAgente = idAgente;
    }
    
}
