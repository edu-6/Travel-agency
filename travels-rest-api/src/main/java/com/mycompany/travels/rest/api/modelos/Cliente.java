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
public class Cliente extends Entidad {
    private String nombre;
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String identificacion;
    private String nacionalidad;

    private int id_nacionalidad;

    //Para crear

    public Cliente(String nombre, String correo, String telefono, LocalDate fechaNacimiento, String identificacion, int id_nacionalidad) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.identificacion = identificacion;
        this.id_nacionalidad = id_nacionalidad;
    }
    

    //Para mostrar
    public Cliente(String id, String nombre, String correo, String telefono, LocalDate fechaNacimiento, String identificacion, String nacionalidad, int id_nacionalidad) {
        this.identificacion = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.identificacion = identificacion;
        this.nacionalidad = nacionalidad;
        this.id_nacionalidad = id_nacionalidad;
    }

    @Override
    public boolean datosCompletos() {
        return identificacion != null && !identificacion.isBlank()
                && nombre != null && !nombre.isBlank()
                && id_nacionalidad > 0
                && fechaNacimiento != null
                && telefono != null && !telefono.isBlank()
                && correo != null && !correo.isBlank();
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return identificacion.length() <= 20
                && nombre.length() <= 100
                && telefono.length() <= 20
                && correo.length() <= 50;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public int getId_nacionalidad() {
        return id_nacionalidad;
    }

}
