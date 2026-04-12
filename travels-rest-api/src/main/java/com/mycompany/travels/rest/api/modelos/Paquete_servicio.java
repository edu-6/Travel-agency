/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.modelos;

/**
 *
 * @author edu
 */
public class Paquete_servicio extends Entidad {

    private String descripcion;

    private double precio;
    private int id_proveedor;
    private int id_paquete;
    private int id;

    private String nombrePaquete;
    private String nombreProveedor;

    public Paquete_servicio(String descripcion, double precio, int id_proveedor, int id_paquete) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.id_proveedor = id_proveedor;
        this.id_paquete = id_paquete;
    }

    public Paquete_servicio() {
    }
    
    
    // para archivo texto

    public Paquete_servicio(String descripcion, double precio, String nombrePaquete, String nombreProveedor) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.nombrePaquete = nombrePaquete;
        this.nombreProveedor = nombreProveedor;
    }
    
    
    

    /// para mostrar
    public Paquete_servicio(String descripcion, double precio, int id_proveedor, int id_paquete, int id, String nombrePaquete, String nombreProveedor) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.id_proveedor = id_proveedor;
        this.id_paquete = id_paquete;
        this.id = id;
        this.nombrePaquete = nombrePaquete;
        this.nombreProveedor = nombreProveedor;
    }

    @Override
    public boolean datosCompletos() {
        return this.descripcion != null && !this.descripcion.isBlank();
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return this.descripcion.length() <= 300;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public int getId_paquete() {
        return id_paquete;
    }

    public int getId() {
        return id;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setId_paquete(int id_paquete) {
        this.id_paquete = id_paquete;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
    
    
    
    
    
}
