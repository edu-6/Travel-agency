/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reportes;

/**
 *
 * @author edu
 */
public class ReporteGanancia {
    
    private double ingreso;
    private double reembolsos;
    private double ganacia;
    
    

    public ReporteGanancia(double ingreso, double reembolsos, double ganacia) {
        this.ingreso = ingreso;
        this.reembolsos = reembolsos;
        this.ganacia = ganacia;
    }

    public double getIngreso() {
        return ingreso;
    }

    public void setIngreso(double ingreso) {
        this.ingreso = ingreso;
    }

    public double getReembolsos() {
        return reembolsos;
    }

    public void setReembolsos(double reembolsos) {
        this.reembolsos = reembolsos;
    }

    public double getGanacia() {
        return ganacia;
    }

    public void setGanacia(double ganacia) {
        this.ganacia = ganacia;
    }


    
    
    
}
