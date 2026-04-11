/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.archivoTexto;

import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.Destino;
import com.mycompany.travels.rest.api.modelos.Empleado;
import com.mycompany.travels.rest.api.modelos.Proveedor;
import com.mycompany.travels.rest.api.servicios.DestinosCrudService;
import com.mycompany.travels.rest.api.servicios.EmpleadosCrudService;
import com.mycompany.travels.rest.api.servicios.ProveedorCrudService;

/**
 *
 * @author edu
 */
public class ElecutadorDeInstrucciones {

    private EmpleadosCrudService empleadosCrudService = new EmpleadosCrudService();
    private DestinosCrudService destinosCrudService = new DestinosCrudService();
    private ProveedorCrudService proveedoresCrudService = new ProveedorCrudService();

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

}
