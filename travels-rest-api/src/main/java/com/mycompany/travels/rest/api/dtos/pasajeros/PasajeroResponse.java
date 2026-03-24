/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.dtos.pasajeros;

/**
 *
 * @author edu
 */
public class PasajeroResponse {
    private String identificacion;
    private String nombre;
    private String nacionalidad;

    public PasajeroResponse(String identificacion, String nombre, String nacionalidad) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }
    
    
    
}
