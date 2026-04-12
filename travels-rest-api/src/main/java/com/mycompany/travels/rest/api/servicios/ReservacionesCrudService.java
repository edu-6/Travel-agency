/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.ClientesDB;
import com.mycompany.travels.rest.api.db.EmpleadosDB;
import com.mycompany.travels.rest.api.db.PasajerosDB;
import com.mycompany.travels.rest.api.db.ReservacionesDB;
import com.mycompany.travels.rest.api.dtos.pasajeros.PasajeroResponse;
import com.mycompany.travels.rest.api.dtos.reservaciones.ReservacionRequest;
import com.mycompany.travels.rest.api.dtos.reservaciones.ReservacionResponse;
import com.mycompany.travels.rest.api.exceptions.EntidadDuplicadaException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.exceptions.NotFoundException;
import com.mycompany.travels.rest.api.interfaces.BuscarVariosString;
import com.mycompany.travels.rest.api.interfaces.CreacionReturnId;
import com.mycompany.travels.rest.api.modelos.Paquete;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class ReservacionesCrudService extends CrudService implements CreacionReturnId<ReservacionRequest>, BuscarVariosString<ReservacionResponse> {

    private ReservacionesDB db = new ReservacionesDB();
    private PasajerosDB pasajerosDB = new PasajerosDB();
    private EmpleadosDB empleadosDB = new EmpleadosDB();
    private ClientesDB clientesDB = new ClientesDB();

    private PaquetesCrudService paquetesCrudService = new PaquetesCrudService();

    @Override
    public int crear(ReservacionRequest entidad) throws ExceptionGenerica {

        if (!empleadosDB.existeEntidad(entidad.getNombreAgente())) {
            throw new EntidadDuplicadaException("no existe el agente  " + entidad.getNombreAgente() + " con rol atencion-cliente");
        }

        if (!clientesDB.existeEntidad(entidad.getIdTitular())) {
            throw new NotFoundException("no existe el cliente con id :" + entidad.getIdTitular());
        }

        String[] pasajeros = entidad.getPasajeros();
        for (String pasajero : pasajeros) {
            if (!clientesDB.existeEntidad(pasajero)) {
                throw new NotFoundException(" no existe el cliente con id " + pasajero);
            }
        }

        if (db.existeViajeEnEstaFecha(entidad.getFechaViaje(), entidad.getIdTitular())) {
            throw new EntidadDuplicadaException("ya existe una reservación para esta fecha ");
        }

        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaViaje = entidad.getFechaViaje();

        if (fechaViaje == null || !fechaViaje.isAfter(fechaHoy)) {
            throw new ExceptionGenerica("La fecha del viaje debe ser posterior al día de hoy.");
        }

        Paquete paquete = paquetesCrudService.buscarPorId(entidad.getIdPaquete());
        if (entidad.getPasajeros().length + 1 > paquete.getCapacidadMaxima()) {
            throw new EntidadDuplicadaException("Los pasajeros sobrepasan el paquete max: " + paquete.getCapacidadMaxima());
        }

        double precioPaquete = db.obtenerPrecioPaquete(entidad.getIdPaquete());
        entidad.setTotalAPagar(precioPaquete);

        int id = db.crear(entidad);
        entidad.generarPasajerosRequest(id);
        pasajerosDB.agregarPasajeros(entidad.getPasajerosRequest());
        return id;
    }

    /**
     * Para buscar las reservaciones de un cliente
     *
     * @param parametro id cliente
     * @return
     * @throws ExceptionGenerica
     */
    @Override
    public ArrayList<ReservacionResponse> buscarPorString(String parametro) throws ExceptionGenerica {
        ArrayList<ReservacionResponse> lista = db.buscarPorString(parametro);
        recuperarPasajerosReservacionLista(lista);
        return lista;
    }

    public ReservacionResponse buscarUnaPorID(int parametro) throws ExceptionGenerica {
        ReservacionResponse reservacion = db.buscarUnaPorID(parametro);
        recuperarPasajerosReseracion(reservacion);
        return reservacion;
    }

    public ArrayList<ReservacionResponse> buscarReservacionesDelDia() throws ExceptionGenerica {
        ArrayList<ReservacionResponse> lista = db.buscarReservacionesHoy();
        recuperarPasajerosReservacionLista(lista);
        return lista;
    }

    private void recuperarPasajerosReservacionLista(ArrayList<ReservacionResponse> lista) throws ExceptionGenerica {
        for (ReservacionResponse reservacion : lista) {
            this.recuperarPasajerosReseracion(reservacion);
        }
    }

    private void recuperarPasajerosReseracion(ReservacionResponse reservacion) throws ExceptionGenerica {
        int idReservacion = reservacion.getId();
        ArrayList<PasajeroResponse> pasajeros = pasajerosDB.buscarVariosInt(idReservacion);
        reservacion.setPasajeros(pasajeros);

    }

    public void marcarReservacionesComoTeminadas() throws ExceptionGenerica {
        this.db.marcarReservacionesComoTeminadas();
    }
}
