/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.modelos;

/**
 *
 * @author edu
 */
public class Proveedor extends Entidad {

    private String nombre;
    private String tipoServicio;
    private String pais;
    private String contactos;
    private int id;
    private int id_pais;
    private int id_tipo_servicio;

    //Para la creación
    public Proveedor(String nombre, String contactos, int id_pais, int id_tipo_servicio) {
        this.nombre = nombre;
        this.contactos = contactos;
        this.id_pais = id_pais;
        this.id_tipo_servicio = id_tipo_servicio;
    }

    // para archivo texto
    public Proveedor(String nombre, String pais, int id_tipo_servicio) {
        this.nombre = nombre;
        this.pais = pais;
        this.id_tipo_servicio = id_tipo_servicio;
    }
    
    
    

    //para editar y mostrar
    public Proveedor(String nombre, String tipoServicio, String pais, String contactos, int id, int id_pais, int id_tipo_servicio) {
        this.nombre = nombre;
        this.tipoServicio = tipoServicio;
        this.pais = pais;
        this.contactos = contactos;
        this.id = id;
        this.id_pais = id_pais;
        this.id_tipo_servicio = id_tipo_servicio;
    }
    
    
    @Override
    public boolean datosCompletos() {
        return nombre != null && !nombre.isBlank();
    }

    @Override
    public boolean datosTamañoCorrecto() {
        boolean contactoValido = true;
        boolean nombreValido = nombre.length() <=30;
        
        if(contactos != null){
            contactoValido = contactos.length() <= 40;
        }
        return nombreValido && contactoValido;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public String getPais() {
        return pais;
    }

    public String getContactos() {
        return contactos;
    }

    public int getId() {
        return id;
    }

    public int getId_pais() {
        return id_pais;
    }

    public int getId_tipo_servicio() {
        return id_tipo_servicio;
    }

    
    
    
    

}
