/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.archivoTexto;

import com.mycompany.travels.rest.api.dtos.paquete.PaqueteGeneral;
import com.mycompany.travels.rest.api.dtos.reservaciones.ReservacionRequest;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.Cliente;
import com.mycompany.travels.rest.api.modelos.Destino;
import com.mycompany.travels.rest.api.modelos.Empleado;
import com.mycompany.travels.rest.api.modelos.PagoReservacion;
import com.mycompany.travels.rest.api.modelos.Paquete;
import com.mycompany.travels.rest.api.modelos.Paquete_servicio;
import com.mycompany.travels.rest.api.modelos.Proveedor;
import com.mycompany.travels.rest.api.servicios.ClientesCrudService;
import com.mycompany.travels.rest.api.servicios.DestinosCrudService;
import com.mycompany.travels.rest.api.servicios.EmpleadosCrudService;
import com.mycompany.travels.rest.api.servicios.PagosCrudService;
import com.mycompany.travels.rest.api.servicios.PaquetesCrudServiceGlobal;
import com.mycompany.travels.rest.api.servicios.ProveedorCrudService;
import com.mycompany.travels.rest.api.servicios.ReservacionesCrudService;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class EjecutadorDeInstrucciones {

    private EmpleadosCrudService empleadosCrudService = new EmpleadosCrudService();
    private DestinosCrudService destinosCrudService = new DestinosCrudService();
    private ProveedorCrudService proveedoresCrudService = new ProveedorCrudService();
    private ClientesCrudService clientesService = new ClientesCrudService();
    private ReservacionesCrudService reservacionesService = new ReservacionesCrudService();
    private PagosCrudService pagosService = new PagosCrudService();
    private PaquetesCrudServiceGlobal paquetesCrudServiceGlobal = new PaquetesCrudServiceGlobal();

    private BuscadorDeIds buscadorIds = new BuscadorDeIds();

    public void registrarEmpleado(Empleado empleado) throws ExceptionGenerica {
        empleadosCrudService.crear(empleado);
    }

    public void registrarDestino(Destino destino) throws ExceptionGenerica {

        if (destino == null) {
            throw new ExceptionGenerica("error al recbir destino");
        }

        int paisId = buscadorIds.buscarIdPais(destino.getNombrePais());

        destino.setId_pais(paisId);
        destinosCrudService.crear(destino);
    }

    public void registrarProveedor(Proveedor proveedor) throws ExceptionGenerica {

        if (proveedor == null) {
            throw new ExceptionGenerica("error al recbir proveedor");
        }

        int paisId = buscadorIds.buscarIdPais(proveedor.getPais());

        if (paisId <= 0) {
            throw new ExceptionGenerica("no existe el pais " + proveedor.getPais());
        }
        proveedor.setId_pais(paisId);
        proveedoresCrudService.crear(proveedor);
    }

    public void registrarPaquete(Paquete paquete) throws ExceptionGenerica {

        if (paquete == null) {
            throw new ExceptionGenerica("error al recbir paquete");
        }

        int idDestino = buscadorIds.buscarIdDestino(paquete.getDestino());

        if (idDestino <= 0) {
            throw new ExceptionGenerica("no existe el destino " + paquete.getDestino());
        }
        
        
        paquete.setId_destino(idDestino);

        // se crea la clase general para conservar la estructurra crud service
        ArrayList<Paquete_servicio> servicios = new ArrayList();
        ArrayList<Paquete_servicio> nuevosServicios = new ArrayList();
        
        PaqueteGeneral paqueteGeneral = new PaqueteGeneral(paquete, servicios, nuevosServicios);

        this.paquetesCrudServiceGlobal.crear(paqueteGeneral);
    }

    public void registrarServicioPaquete(Paquete_servicio paqueteServicio) throws ExceptionGenerica {

        if (paqueteServicio == null) {
            throw new ExceptionGenerica("error al recbir el servicio-paquete");
        }

        
        int idProvedor = buscadorIds.buscarIdProveedor(paqueteServicio.getNombreProveedor());

        PaqueteGeneral paqueteGeneral = this.paquetesCrudServiceGlobal.buscar(paqueteServicio.getNombrePaquete());
        if (paqueteGeneral == null) {
            throw new ExceptionGenerica("no existe el paquete " + paqueteServicio.getNombrePaquete());
        }

        if (idProvedor <= 0) {
            throw new ExceptionGenerica("no existe el proveedor " + paqueteServicio.getNombreProveedor());
        }

        // se toma del que se buscó
        paqueteServicio.setId_paquete(paqueteGeneral.getPaquete().getId());
        
        // se añade id proveedro del servicio
        paqueteServicio.setId_proveedor(idProvedor);

        ArrayList<Paquete_servicio> nuevosServicios = new ArrayList();
        nuevosServicios.add(paqueteServicio);
        
        paqueteGeneral.setNuevosServicios(nuevosServicios);
        
        paquetesCrudServiceGlobal.editar(paqueteGeneral);
        
    }

    public void registrarCliente(Cliente cliente) throws ExceptionGenerica {

        if (cliente == null) {
            throw new ExceptionGenerica("error al recbir el cliente");
        }

        int idNacionalidad = buscadorIds.buscarIdNacionalidad(cliente.getNacionalidad());

        if (idNacionalidad <= 0) {
            throw new ExceptionGenerica("no existe la nacionalidad " + cliente.getNacionalidad());
        }

        cliente.setId_nacionalidad(idNacionalidad);

        clientesService.crear(cliente);
    }

    public void registrarReservacion(ReservacionRequest reservacion) throws ExceptionGenerica {

        if (reservacion == null) {
            throw new ExceptionGenerica("error al recbir  la reservacion");
        }

        int idAgente = buscadorIds.buscarIdAgente(reservacion.getNombreAgente());
        int idPaquete = buscadorIds.buscarIdPaquete(reservacion.getNombrePaquete());

        if (idAgente <= 0) {
            throw new ExceptionGenerica("no existe el agente con usuario " + reservacion.getNombreAgente() + " no no tiene el rol correcto");
        }

        if (idPaquete <= 0) {
            throw new ExceptionGenerica("no existe el paquete con nombre  " + reservacion.getNombrePaquete());
        }

        reservacion.setIdAgenteCreador(idAgente);
        reservacion.setIdPaquete(idPaquete);

        reservacionesService.crear(reservacion);
    }

    public void registrarPago(PagoReservacion pago) throws ExceptionGenerica {

        if (pago == null) {
            throw new ExceptionGenerica("error al recibir el pago");
        }

        // tira una excetión automaticamente si no la encuentra
        reservacionesService.buscarUnaPorID(pago.getIdReservacion());

        if (!(pago.getId_metodo_pago() >= 1 && pago.getId_metodo_pago() <= 3)) {
            throw new ExceptionGenerica(" el metoo de pago " + pago.getId_metodo_pago() + " no existe");
        }

        pagosService.crear(pago);

    }

}
