/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.archivoTexto;

import com.mycompany.travels.rest.api.dtos.reservaciones.ReservacionRequest;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.Cliente;
import com.mycompany.travels.rest.api.modelos.Destino;
import com.mycompany.travels.rest.api.modelos.Empleado;
import com.mycompany.travels.rest.api.modelos.PagoReservacion;
import com.mycompany.travels.rest.api.modelos.Paquete;
import com.mycompany.travels.rest.api.modelos.Paquete_servicio;
import com.mycompany.travels.rest.api.modelos.Proveedor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author edu
 */
public class ConversorAObjetos {

    private static final int PARAMETROS_USUARIO = 3;
    private static final int PARAMETROS_DESTINO = 3;
    private static final int PARAMETROS_PROVEEDOR = 3;
    private static final int PARAMETROS_PAQUETE = 5;
    private static final int PARAMETROS_SERVICIO_PAQUETE = 4;
    private static final int PARAMETROS_CLIENTE = 6;
    private static final int PARAMETROS_RESERVACION = 4;
    private static final int PARAMETROS_PAGO = 4;

    public Empleado convertirAUsuario(String instruccion) throws ExceptionGenerica {

        String[] parametros = separarYQuitarComillas(instruccion);

        if (!(parametros.length == PARAMETROS_USUARIO)) {
            throw new ExceptionGenerica("Error, la instruccion USUARIO tiene parametros incompletos");
        }

        try {
            String nombre = parametros[0];
            String contraseña = parametros[1];
            int idRol = Integer.parseInt(parametros[2]);
            boolean activo = true;

            return new Empleado(nombre, contraseña, activo, idRol);

        } catch (IllegalArgumentException e) {
            throw new ExceptionGenerica("Error en los datos de la instrucción USUARIO: " + e.getMessage());
        }
    }

    public Destino convertirADestino(String instruccion) throws ExceptionGenerica {

        String[] parametros = separarYQuitarComillas(instruccion);

        if (!(parametros.length == PARAMETROS_DESTINO)) {
            throw new ExceptionGenerica("Error, la instruccion DESTINO no tiene la cantidad exacta de parametros necesarios");
        }

        try {
            String nombre = parametros[0];
            String nombrePais = parametros[1];
            String descripcion = parametros[2];

            return new Destino(nombre, descripcion, nombrePais);

        } catch (IllegalArgumentException e) {
            throw new ExceptionGenerica("Error en los parametros de DESTINO: " + e.getMessage());
        }
    }

    public Proveedor convertirAProveedor(String instruccion) throws ExceptionGenerica {

        String[] parametros = separarYQuitarComillas(instruccion);
        for (String parametro : parametros) {
            System.out.println(parametro);
        }

        if (!(parametros.length == PARAMETROS_PROVEEDOR)) {
            throw new ExceptionGenerica("Error, la instruccion PROVEEDOR no tiene la cantidad exacta de parametros necesarios");
        }

        try {
            String nombre = parametros[0];
            int tipoServicio = Integer.valueOf(parametros[1]);
            String nombrePais = parametros[2];

            return new Proveedor(nombre, nombrePais, tipoServicio);

        } catch (IllegalArgumentException e) {
            throw new ExceptionGenerica("Error en los parametros de PROVEEDOR: " + e.getMessage());
        }
    }

    public Paquete convertirAPaquete(String instruccion) throws ExceptionGenerica {
        String[] parametros = separarYQuitarComillas(instruccion);

        if (!(parametros.length == PARAMETROS_PAQUETE)) {
            throw new ExceptionGenerica("Error, la instruccion PAQUETE no tiene la cantidad exacta de parametros necesarios");
        }

        try {

            String nombre = parametros[0];
            String nombreDestino = parametros[1];
            int duracion = Integer.valueOf(parametros[2]);
            double precioVenta = Double.valueOf(parametros[3]);
            int cupoMaximo = Integer.valueOf(parametros[4]);

            return new Paquete(nombre, nombreDestino, duracion, cupoMaximo, precioVenta);

        } catch (IllegalArgumentException e) {
            throw new ExceptionGenerica("Error en los parametros de PAQUETE: " + e.getMessage());
        }
    }

    public Paquete_servicio convertirAPaqueteServicio(String instruccion) throws ExceptionGenerica {
        String[] parametros = separarYQuitarComillas(instruccion);

        if (!(parametros.length == PARAMETROS_SERVICIO_PAQUETE)) {
            throw new ExceptionGenerica("Error, la instruccion SERVICIO_PAQUETE no tiene la cantidad exacta de parametros necesarios");
        }

        try {
            String nombrePaquete = parametros[0];
            String nombreProveedor = parametros[1];
            String descripcion = parametros[2];
            double costo = Double.valueOf(parametros[3]);

            return new Paquete_servicio(descripcion, costo, nombrePaquete, nombreProveedor);

        } catch (IllegalArgumentException e) {
            throw new ExceptionGenerica("Error en los parametros de SERVICIO_PAQUETE: " + e.getMessage());
        }
    }

    public Cliente convertirACliente(String instruccion) throws ExceptionGenerica {
        String[] parametros = separarYQuitarComillas(instruccion);

        if (!(parametros.length == PARAMETROS_CLIENTE)) {
            throw new ExceptionGenerica("Error, la instruccion CLIENTE no tiene la cantidad exacta de parametros necesarios");
        }

        try {
            String identificacion = parametros[0];
            String nombre = parametros[1];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaNacimiento = LocalDate.parse(parametros[2], formatter);
            int telefonoInt = Integer.valueOf(parametros[3]);
            String telefono = String.valueOf(telefonoInt);
            String correo = parametros[4];
            String nacionalidad = parametros[5];

            return new Cliente(nombre, correo, telefono, fechaNacimiento, identificacion, nacionalidad);

        } catch (IllegalArgumentException e) {
            throw new ExceptionGenerica("Error en los parametros de CLIENTE: " + e.getMessage());
        }
    }

    public ReservacionRequest convertirAReservacion(String instruccion) throws ExceptionGenerica {
        String[] parametros = separarYQuitarComillas(instruccion);

        if (!(parametros.length == PARAMETROS_RESERVACION)) {
            throw new ExceptionGenerica("Error, la instruccion RESERVACION no tiene la cantidad exacta de parametros necesarios");
        }

        try {

            String nombrePaquete = parametros[0];
            String nombreAgente = parametros[1];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaViaje = LocalDate.parse(parametros[2], formatter);

            String pasajerosEntreComillas = parametros[3];
            String[] pasajeros = this.separarPasajeros(pasajerosEntreComillas);

            if (pasajeros.length == 0) {
                throw new ExceptionGenerica("ingrese los pasasjeros");
            }

            String idTitular = pasajeros[0];
            pasajeros = this.quitarElPrimerPasajero(pasajeros);

            return new ReservacionRequest(idTitular, nombrePaquete, nombreAgente, pasajeros, fechaViaje);

        } catch (IllegalArgumentException e) {
            throw new ExceptionGenerica("Error en los parametros de CLIENTE: " + e.getMessage());
        }
    }

    public PagoReservacion  convertirAPago(String instruccion) throws ExceptionGenerica {
        String[] parametros = separarYQuitarComillas(instruccion);

        if (!(parametros.length == PARAMETROS_PAGO)) {
            throw new ExceptionGenerica("Error, la instruccion PAGO no tiene la cantidad exacta de parametros necesarios");
        }
        

        try {
            int numeroReservacion = Integer.valueOf(parametros[0]);
            double monto = Double.valueOf(parametros[1]);
            int idmetodoPago = Integer.valueOf(parametros[2]);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaPago = LocalDate.parse(parametros[2], formatter);
            
            return new PagoReservacion(monto, numeroReservacion, idmetodoPago, fechaPago);

        } catch (IllegalArgumentException e) {
            throw new ExceptionGenerica("Error en los parametros de PAGO: " + e.getMessage());
        }
    }

    private String[] quitarComillasArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = quitarComillasString(array[i]);
        }
        return array;
    }

    private String quitarComillasString(String string) {
        if (string == null) {
            return null;
        }
        return string.trim().replace("\"", "");
    }

    private String[] separarYQuitarComillas(String linea) {
        String[] parametros = linea.split(",");
        return quitarComillasArray(parametros);
    }

    private String[] separarPasajeros(String linea) {
        return linea.trim().split("\\|");

    }

    private String[] quitarElPrimerPasajero(String[] pasajeros) {

        if (pasajeros == null || pasajeros.length == 0) {
            return new String[0];
        }

        String[] filtrados = new String[pasajeros.length - 1];

        for (int i = 0; i < filtrados.length; i++) {
            filtrados[i] = pasajeros[i + 1];
        }
        return filtrados;
    }
}
