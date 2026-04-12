/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.paquete;

import com.mycompany.travels.rest.api.modelos.Paquete;
import com.mycompany.travels.rest.api.modelos.Paquete_servicio;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PaqueteGeneral {
    
    private Paquete paquete;
    private ArrayList<Paquete_servicio> servicios;
    private ArrayList<Paquete_servicio> nuevosServicios;

    public PaqueteGeneral(Paquete paquete, ArrayList<Paquete_servicio> servicios) {
        this.paquete = paquete;
        this.servicios = servicios;
    }
    
    
    
    
    

    public PaqueteGeneral() {
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public ArrayList<Paquete_servicio> getServicios() {
        return servicios;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public void setServicios(ArrayList<Paquete_servicio> servicios) {
        this.servicios = servicios;
    }

    public ArrayList<Paquete_servicio> getNuevosServicios() {
        return nuevosServicios;
    }

    public void setNuevosServicios(ArrayList<Paquete_servicio> nuevosServicios) {
        this.nuevosServicios = nuevosServicios;
    }
    
    
    
    
    
}
