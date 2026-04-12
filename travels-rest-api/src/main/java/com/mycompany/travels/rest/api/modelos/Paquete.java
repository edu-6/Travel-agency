/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.modelos;

/**
 *
 * @author edu
 */
public class Paquete extends Entidad {

    private String nombre;
    private String descripcion;
    private String destino;
    private int duracion;
    private int capacidadMaxima;
    private double precioVenta;
    private double ganancia;
    private int id;
    private int id_destino;
    private boolean activo;
    
    public Paquete(){
        
    }

    // para crear
    public Paquete(String nombre, String descripcion, int duracion, int capacidadMaxima, double precioVenta, int id_destino, boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.capacidadMaxima = capacidadMaxima;
        this.precioVenta = precioVenta;
        this.id_destino = id_destino;
        this.activo = activo;
    }
    
    //para archivo texto

    public Paquete(String nombre, String destino, int duracion, int capacidadMaxima, double precioVenta) {
        this.nombre = nombre;
        this.destino = destino;
        this.duracion = duracion;
        this.capacidadMaxima = capacidadMaxima;
        this.precioVenta = precioVenta;
        this.activo = true;
    }
    

    // para  mostrar/ buscar
    public Paquete(String nombre, String descripcion, String destino, int duracion, int capacidadMaxima,
            double precioVenta, double ganancia, int id, int id_destino, boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.destino = destino;
        this.duracion = duracion;
        this.capacidadMaxima = capacidadMaxima;
        this.precioVenta = precioVenta;
        this.id = id;
        this.id_destino = id_destino;
        this.ganancia = ganancia;
        this.activo = activo;
    }

    @Override
    public boolean datosCompletos() {
        return nombre != null && !nombre.isBlank()
                && duracion > 0
                && capacidadMaxima > 0;
    }

    @Override
    public boolean datosTamañoCorrecto() {
        boolean obligatoriosValido = false;
        boolean opcionalValido = true;
        
        obligatoriosValido = nombre.length() <= 200;
        
        if(descripcion != null){
            opcionalValido = descripcion.length() <=300;
        }
        return obligatoriosValido && opcionalValido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDestino() {
        return destino;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public double getGanancia() {
        return ganancia;
    }

    public int getId() {
        return id;
    }

    public int getId_destino() {
        return id_destino;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }

    public void setId_destino(int id_destino) {
        this.id_destino = id_destino;
    }
    
    
    

}
