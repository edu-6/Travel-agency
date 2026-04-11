/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.modelos;

/**
 *
 * @author edu
 */
public class Empleado extends Entidad {
    private String nombre;
    private String contraseña;
    private String nombreRol;
    private boolean activo;
    private int id;
    private int idRol;

    // para la creación
    public Empleado(String nombre, String contraseña, boolean activo, int idRol) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.activo = activo;
        this.idRol = idRol;
    }

    // para la edicion ID
    public Empleado(String nombre, String contraseña, String nombreRol, boolean activo, int id, int idRol) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.nombreRol = nombreRol;
        this.activo = activo;
        this.id = id;
        this.idRol = idRol;
    }
    
    
    @Override
    public boolean datosCompletos() {
        return nombre != null
               && !nombre.isBlank()
               && contraseña != null
               && !contraseña.isBlank();
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return nombre.length() <=100
               && contraseña.length() <= 50;
    }
    
    public boolean datosCompletosEditar() {
        return nombre != null
               && !nombre.isBlank();
    }

    
    public boolean datosTamañoCorrectoEditar() {
        return nombre.length() <=100;
    }

    public String getNombreRol() {
        return nombreRol;
    }
    

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public boolean isActivo() {
        return activo;
    }

    public int getId() {
        return id;
    }

    public int getIdRol() {
        return idRol;
    }
}
