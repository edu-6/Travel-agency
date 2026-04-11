/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.archivoTexto;

import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.Destino;
import com.mycompany.travels.rest.api.modelos.Empleado;
import com.mycompany.travels.rest.api.modelos.Proveedor;

/**
 *
 * @author edu
 */
public class ConversorAObjetos {

    private static final int PARAMETROS_USUARIO = 3;
    private static final int PARAMETROS_DESTINO = 3;
    private static final int PARAMETROS_PROVEEDOR = 3;

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

        if (!(parametros.length == PARAMETROS_PROVEEDOR)) {
            throw new ExceptionGenerica("Error, la instruccion PROVEEDOR no tiene la cantidad exacta de parametros necesarios");
        }

        try {
            String nombre = parametros[0];
             int tipoServicio = Integer.valueOf(parametros[1]);
            String nombrePais = parametros[2];

            return new Proveedor(nombre,nombrePais, tipoServicio);

        } catch (IllegalArgumentException e) {
            throw new ExceptionGenerica("Error en los parametros de PROVEEDOR: " + e.getMessage());
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
}
