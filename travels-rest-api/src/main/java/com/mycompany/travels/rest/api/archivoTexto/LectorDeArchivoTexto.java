/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.archivoTexto;

import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class LectorDeArchivoTexto {

    private ElecutadorDeInstrucciones procesador = new ElecutadorDeInstrucciones();
    private ConversorAObjetos conversor = new ConversorAObjetos();

    public ArrayList<String> procesarArchivo(InputStream inputStream) {
        ArrayList<String> listaLogs = new ArrayList();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String linea;
            int numeroLinea = 0;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    try {
                        numeroLinea++;
                        reconocerInstruccion(linea, numeroLinea, listaLogs);
                    } catch (ExceptionGenerica e) {
                        listaLogs.add("Error linea " + numeroLinea + " " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            listaLogs.add("error al leer el archivo " + e.getMessage());
        }

        return listaLogs;
    }

    private void reconocerInstruccion(String linea, int numeroLinea, ArrayList<String> listaLogs) throws ExceptionGenerica {
        String nombreInstruccion = obtenerNombreInstruccion(linea);
        String lineaLimpia = "";

        switch (nombreInstruccion) {
            case "USUARIO":
                lineaLimpia = dejarUnicamenteParametros(linea, nombreInstruccion);
                procesador.registrarEmpleado(conversor.convertirAUsuario(lineaLimpia));
                listaLogs.add("Linea No." + numeroLinea + " Se registró nuevo usuario");
                break;
            case "DESTINO":
                lineaLimpia = dejarUnicamenteParametros(linea, nombreInstruccion);
                procesador.registrarDestino(conversor.convertirADestino(lineaLimpia));
                listaLogs.add("Linea No." + numeroLinea + " Se registró nuevo destino");
                break;
            case "PROVEEDOR":
                lineaLimpia = dejarUnicamenteParametros(linea, nombreInstruccion);
                procesador.registrarProveedor(conversor.convertirAProveedor(lineaLimpia));
                listaLogs.add("Linea No." + numeroLinea + " Se registró nuevo proveedor");
                break;
            case "PAQUETE":
                lineaLimpia = dejarUnicamenteParametros(linea, nombreInstruccion);
                procesador.registrarPaquete(conversor.convertirAPaquete(lineaLimpia));
                listaLogs.add("Linea No." + numeroLinea + " Se registró nuevo paquete");
                break;
            case "SERVICIO_PAQUETE":
                lineaLimpia = dejarUnicamenteParametros(linea, nombreInstruccion);
                procesador.registrarServicioPaquete(conversor.convertirAPaqueteServicio(lineaLimpia));
                listaLogs.add("Linea No." + numeroLinea + " Se registró nuevo servicio-paquete");
                break;
            case "CLIENTE":
                lineaLimpia = dejarUnicamenteParametros(linea, nombreInstruccion);
                procesador.registrarCliente(conversor.convertirACliente(lineaLimpia));
                listaLogs.add("Linea No." + numeroLinea + " Se registró nuevo cliente");
                break;
            case "RESERVACION":
                lineaLimpia = dejarUnicamenteParametros(linea, nombreInstruccion);
                procesador.registrarReservacion(conversor.convertirAReservacion(lineaLimpia));
                listaLogs.add("Linea No." + numeroLinea + " Se registró nuevo cliente");
                break;
            default:
                listaLogs.add("Linea No." + numeroLinea + " no se reconoció la instrucción " + linea);
        }
    }

    private String obtenerNombreInstruccion(String linea) {
        String nombreInstruccion = "";

        for (int i = 0; i < linea.length(); i++) {
            if (linea.charAt(i) == '(') {
                break;
            } else {
                nombreInstruccion += linea.charAt(i);
            }
        }
        return nombreInstruccion;
    }

    private String dejarUnicamenteParametros(String lineaSucia, String nombreInstruccion) {
        String lineaLimpia = "";
        int inicioIteracion = nombreInstruccion.length() + 1;

        for (int i = inicioIteracion; i < lineaSucia.length() - 1; i++) {
            lineaLimpia += lineaSucia.charAt(i);
        }
        return lineaLimpia;
    }

}
