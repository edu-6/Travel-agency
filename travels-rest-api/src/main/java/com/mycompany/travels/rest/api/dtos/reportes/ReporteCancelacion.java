/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.reportes;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class ReporteCancelacion {

    private int idReservacion;
    private LocalDate fechaCancelacion;
    private double montoReembolsado;
    private double perdida;

    public ReporteCancelacion(int idReservacion, LocalDate fechaCancelacion, double montoReembolsado, double perdida) {
        this.idReservacion = idReservacion;
        this.fechaCancelacion = fechaCancelacion;
        this.montoReembolsado = montoReembolsado;
        this.perdida = perdida;
    }

    public int getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDate fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public double getMontoReembolsado() {
        return montoReembolsado;
    }

    public void setMontoReembolsado(double montoReembolsado) {
        this.montoReembolsado = montoReembolsado;
    }

    public double getPerdida() {
        return perdida;
    }

    public void setPerdida(double perdida) {
        this.perdida = perdida;
    }

}
