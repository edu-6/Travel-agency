/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios;

import com.mycompany.travels.rest.api.db.PaquetesDB;
import com.mycompany.travels.rest.api.dtos.paquete.PaqueteGeneral;
import com.mycompany.travels.rest.api.exceptions.CamposVaciosException;
import com.mycompany.travels.rest.api.exceptions.DatosMuyLargosException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.exceptions.NotFoundException;
import com.mycompany.travels.rest.api.exceptions.SinGananciaException;
import com.mycompany.travels.rest.api.interfaces.BuscarTodos;
import com.mycompany.travels.rest.api.interfaces.BuscarVariosInt;
import com.mycompany.travels.rest.api.interfaces.BusquedaUnitariaString;
import com.mycompany.travels.rest.api.interfaces.CreacionEntidad;
import com.mycompany.travels.rest.api.interfaces.EdicionEntidad;
import com.mycompany.travels.rest.api.modelos.Paquete;
import com.mycompany.travels.rest.api.modelos.Paquete_servicio;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PaquetesCrudServiceGlobal implements CreacionEntidad<PaqueteGeneral>, EdicionEntidad<PaqueteGeneral>,
        BusquedaUnitariaString<PaqueteGeneral>, BuscarTodos<PaqueteGeneral> , BuscarVariosInt<PaqueteGeneral>{

    private PaquetesCrudService paquetesService = new PaquetesCrudService();
    private PaqueteServicioCService serviciosService = new PaqueteServicioCService();
    private PaquetesDB paquetesDB = new PaquetesDB();

    @Override
    public void crear(PaqueteGeneral entidad) throws ExceptionGenerica {
        ArrayList<Paquete_servicio> lista = entidad.getNuevosServicios();
        
        double ganancias = this.asegurarGanancias(entidad);
        validarPaquetesServicios(lista);
        
        int idPaquete = paquetesService.crear(entidad.getPaquete());
        
        this.crearServiciosPaquete(lista, idPaquete);
        
        this.paquetesDB.actualizarGanancia(idPaquete, ganancias);
    }

    @Override
    public void editar(PaqueteGeneral entidad) throws ExceptionGenerica {
        
        ArrayList<Paquete_servicio> lista = entidad.getServicios();
        ArrayList<Paquete_servicio> listaNuevos = entidad.getNuevosServicios();
        int idPaquete = entidad.getPaquete().getId();

        
        validarPaquetesServicios(lista);
        validarPaquetesServicios(listaNuevos);
        
        double ganancias = this.asegurarGanancias(entidad);
        paquetesService.editar(entidad.getPaquete());
        
        this.editarServiciosPaquete(lista);
        this.crearServiciosPaquete(listaNuevos,idPaquete);
        
        this.paquetesDB.actualizarGanancia(idPaquete, ganancias);
        
    }

    @Override
    public PaqueteGeneral buscar(String nombre) throws ExceptionGenerica {

        Paquete paquete = paquetesService.buscar(nombre);
        if(paquete == null){
            throw new NotFoundException("no se encontró el paquete con nombre: "+ nombre);
        }
        
        boolean altaDemanda = paquetesService.tieneAltaDemanda(paquete);
        paquete.setTopadoEnProximasSalidas(altaDemanda);
        
        
        ArrayList<Paquete_servicio> lista = serviciosService.buscarVariosInt(paquete.getId());

        return new PaqueteGeneral(paquete, lista);
    }

    @Override
    public ArrayList<PaqueteGeneral> buscarTodos() throws ExceptionGenerica {

        ArrayList<PaqueteGeneral> lista = new ArrayList();

        ArrayList<Paquete> paquetes = paquetesService.buscarTodos();
        for (Paquete paquete : paquetes) {

            ArrayList<Paquete_servicio> listaServicios = serviciosService.buscarVariosInt(paquete.getId());

            lista.add(new PaqueteGeneral(paquete, listaServicios));
        }
        return lista;
    }

    public ArrayList<PaqueteGeneral> buscarActivos() throws ExceptionGenerica {

        ArrayList<PaqueteGeneral> lista = new ArrayList();

        ArrayList<Paquete> paquetes = paquetesService.buscarPaquetesActivos();
        for (Paquete paquete : paquetes) {

            ArrayList<Paquete_servicio> listaServicios = serviciosService.buscarVariosInt(paquete.getId());

            lista.add(new PaqueteGeneral(paquete, listaServicios));
        }
        return lista;
    }
    
    
    
    
    private double asegurarGanancias(PaqueteGeneral paqueteGeneral) throws SinGananciaException{
        
        double precioPaquete = paqueteGeneral.getPaquete().getPrecioVenta();
        double costoPaquetes = 0;
        
        costoPaquetes+= sumarCostosList(paqueteGeneral.getServicios()); // antiguos
        costoPaquetes+= sumarCostosList(paqueteGeneral.getNuevosServicios()); // nuevos
        
        double ganancia = precioPaquete -costoPaquetes;
        
        if(ganancia <= 0){
            throw new SinGananciaException("El paquete no genera ganacias, eleve el precio del paquete  GANANCIA "+ ganancia);
        }
        return ganancia;
    }
    
    
    private double sumarCostosList(ArrayList<Paquete_servicio> lista){
        double costoPaquetes = 0;
        for (Paquete_servicio paquete_servicio : lista) {
            costoPaquetes+= paquete_servicio.getPrecio();
        }
        return costoPaquetes;
    }
    
    
    private void editarServiciosPaquete(ArrayList<Paquete_servicio> lista) throws ExceptionGenerica{
        for (Paquete_servicio paquete_servicio : lista) {
            serviciosService.editar(paquete_servicio);
        }
    }
    
    
    private void crearServiciosPaquete(ArrayList<Paquete_servicio> lista, int idPaquete) throws ExceptionGenerica{
        for (Paquete_servicio paquete_servicio : lista) {
            paquete_servicio.setId_paquete(idPaquete); // setear al id paquete recien creado
            serviciosService.crear(paquete_servicio);
        }
    }
    
    
    private void validarPaquetesServicios(ArrayList<Paquete_servicio> lista) throws CamposVaciosException, DatosMuyLargosException, SinGananciaException{
        for (Paquete_servicio paquete_servicio : lista) {
            serviciosService.revisarDatosCorrectos(paquete_servicio);
            if(paquete_servicio.getPrecio() <= 0){
                throw new SinGananciaException("El precio de un servicio-paquete o varios servicios es negativo");
            }
        }
    }

    @Override
    public ArrayList<PaqueteGeneral> buscarVariosInt(int param) throws ExceptionGenerica {
        
        ArrayList<PaqueteGeneral> lista = new ArrayList();
        
        ArrayList<Paquete> listaPaquetes = paquetesService.buscarVariosInt(param);
        for (Paquete p : listaPaquetes) {
            lista.add(new PaqueteGeneral(p,serviciosService.buscarVariosInt(p.getId())));
        }
        return lista;
    }

}
