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
import com.mycompany.travels.rest.api.modelos.Paquete;
import com.mycompany.travels.rest.api.modelos.Paquete_servicio;
import com.mycompany.travels.rest.api.modelos.Proveedor;
import com.mycompany.travels.rest.api.servicios.ClientesCrudService;
import com.mycompany.travels.rest.api.servicios.DestinosCrudService;
import com.mycompany.travels.rest.api.servicios.EmpleadosCrudService;
import com.mycompany.travels.rest.api.servicios.PaqueteServicioCService;
import com.mycompany.travels.rest.api.servicios.PaquetesCrudService;
import com.mycompany.travels.rest.api.servicios.ProveedorCrudService;
import com.mycompany.travels.rest.api.servicios.ReservacionesCrudService;

/**
 *
 * @author edu
 */
public class ElecutadorDeInstrucciones {

    private EmpleadosCrudService empleadosCrudService = new EmpleadosCrudService();
    private DestinosCrudService destinosCrudService = new DestinosCrudService();
    private ProveedorCrudService proveedoresCrudService = new ProveedorCrudService();
    private PaquetesCrudService paqueteCrudService = new PaquetesCrudService();
    private PaqueteServicioCService servicioPaqeteService = new PaqueteServicioCService();
    private ClientesCrudService clientesService = new ClientesCrudService();
    private ReservacionesCrudService reservacionesService = new ReservacionesCrudService();

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

        paqueteCrudService.crear(paquete);
    }

    public void registrarServicioPaquete(Paquete_servicio ps) throws ExceptionGenerica {

        if (ps == null) {
            throw new ExceptionGenerica("error al recbir el servicio-paquete");
        }

        int idPaquete = buscadorIds.buscarIdPaquete(ps.getNombrePaquete());
        int idProvedor = buscadorIds.buscarIdProveedor(ps.getNombreProveedor());

        if (idPaquete <= 0) {
            throw new ExceptionGenerica("no existe el paquete " + ps.getNombrePaquete());
        }

        if (idProvedor <= 0) {
            throw new ExceptionGenerica("no existe el proveedor " + ps.getNombrePaquete());
        }

        ps.setId_paquete(idPaquete);
        ps.setId_proveedor(idProvedor);

        servicioPaqeteService.crear(ps);
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
            throw new ExceptionGenerica("no existe el agente con usuario " + reservacion.getNombreAgente());
        }

        if (idPaquete <= 0) {
            throw new ExceptionGenerica("no existe el paquete con nombre  " + reservacion.getNombrePaquete());
        }

        reservacion.setIdAgenteCreador(idAgente);
        reservacion.setIdPaquete(idPaquete);

        reservacionesService.crear(reservacion);
    }

}
