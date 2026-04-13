/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.travels.rest.api.resources;

import com.google.gson.Gson;
import com.mycompany.travels.rest.api.dtos.paquete.PaqueteGeneral;
import com.mycompany.travels.rest.api.exceptions.CamposVaciosException;
import com.mycompany.travels.rest.api.exceptions.DatosMuyLargosException;
import com.mycompany.travels.rest.api.exceptions.EntidadDuplicadaException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.exceptions.SinGananciaException;
import com.mycompany.travels.rest.api.modelos.Paquete;
import com.mycompany.travels.rest.api.servicios.PaqueteServicioCService;
import com.mycompany.travels.rest.api.servicios.PaquetesCrudService;
import com.mycompany.travels.rest.api.servicios.PaquetesCrudServiceGlobal;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
@WebServlet(name = "PaquetesSource", urlPatterns = {"/api/paquetes/*"})
public class PaquetesSource extends HttpServlet {

    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new Gson();
    private PaquetesCrudServiceGlobal crudService = new PaquetesCrudServiceGlobal();
    private PaquetesCrudService paquetesCrudService = new PaquetesCrudService();
    private PaqueteServicioCService paqueteServicioCS = new PaqueteServicioCService();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = this.obtenerParametroRuta(req);

        if (id != null && this.esNumero(id)) {
            try {
                paqueteServicioCS.eliminar(Integer.valueOf(id));
            } catch (ExceptionGenerica ex) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ex.getMessage(), resp);
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        

        try {
            PaqueteGeneral nuevo = gson.fromJson(req.getReader(), PaqueteGeneral.class);
            crudService.editar(nuevo);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (CamposVaciosException | DatosMuyLargosException ex) {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);

        } catch (EntidadDuplicadaException ex) {

            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            escritor.escribirError(ex.getMessage(), resp);

        } catch (SinGananciaException ex) {

            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (ExceptionGenerica ex) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }catch (IllegalArgumentException e) {
            
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirErrorArgumentacion(resp);
        }catch (IOException | RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirErrorArgumentacion(resp);
        } 
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        

        try {
            PaqueteGeneral nuevo = gson.fromJson(req.getReader(), PaqueteGeneral.class);
            crudService.crear(nuevo);
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (CamposVaciosException | DatosMuyLargosException ex) {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);

        } catch (EntidadDuplicadaException ex) {

            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            escritor.escribirError(ex.getMessage(), resp);

        } catch (SinGananciaException ex) {

            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (ExceptionGenerica ex) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }catch (IllegalArgumentException e) {
            
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirErrorArgumentacion(resp);
        }catch (IOException | RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirErrorArgumentacion(resp);
        } 
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String ruta = obtenerParametroRuta(req);

        if (esNumero(ruta)) {
            try {
                ArrayList<Paquete> lista = paquetesCrudService.buscarVariosInt(Integer.valueOf(ruta));
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, lista);
            } catch (ExceptionGenerica ex) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ex.getMessage(), resp);
            }
        } else {

            Object objeto;
            try {
                if (ruta.equals("todos")) {
                    objeto = paquetesCrudService.buscarPaquetesActivos();
                } else {
                    objeto = crudService.buscar(ruta);
                }
                
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, objeto);
                
            } catch (ExceptionGenerica e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(e.getMessage(), resp);
            }

        }

    }

    private String obtenerParametroRuta(HttpServletRequest req) {
        String ruta = req.getPathInfo();

        if (ruta == null || ruta.equals("/")) {
            return null;
        } else {
            return ruta.substring(1);
        }
    }

    private boolean esNumero(String parametro) {
        try {
            Integer.valueOf(parametro);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

}
