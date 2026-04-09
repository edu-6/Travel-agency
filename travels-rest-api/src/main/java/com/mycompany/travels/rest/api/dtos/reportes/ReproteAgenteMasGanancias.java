/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reportes;

/**
 *
 * @author edu
 */
public class ReproteAgenteMasGanancias {
    
    private String nombre;
    private double ganancia;

    public ReproteAgenteMasGanancias(String nombre, double ganancia) {
        this.nombre = nombre;
        this.ganancia = ganancia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }
    
    
    
}
