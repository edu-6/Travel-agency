/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.pasajeros;

/**
 *
 * @author edu
 */
public class PasajeroRequest {
    private String identificacion;
    private int id_reservacion;

    public PasajeroRequest() {
    }

    public PasajeroRequest(String identificacion, int id_reservacion) {
        this.identificacion = identificacion;
        this.id_reservacion = id_reservacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public int getId_reservacion() {
        return id_reservacion;
    }
}
