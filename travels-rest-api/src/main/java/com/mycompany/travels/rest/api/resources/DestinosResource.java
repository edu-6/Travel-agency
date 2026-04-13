/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.travels.rest.api.resources;

import com.google.gson.Gson;
import com.mycompany.travels.rest.api.exceptions.CamposVaciosException;
import com.mycompany.travels.rest.api.exceptions.DatosMuyLargosException;
import com.mycompany.travels.rest.api.exceptions.EntidadDuplicadaException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.exceptions.NotFoundException;
import com.mycompany.travels.rest.api.modelos.Destino;
import com.mycompany.travels.rest.api.servicios.DestinosCrudService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author edu
 */
@WebServlet(name = "DestinosResource", urlPatterns = {"/api/destinos/*"})
public class DestinosResource extends HttpServlet {

    private final Gson gson = new Gson();
    private DestinosCrudService crudService = new DestinosCrudService();
    private EscritorJson escritor = new EscritorJson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        

        try {
            Destino nuevo = gson.fromJson(req.getReader(), Destino.class);
            
            crudService.crear(nuevo);
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (CamposVaciosException | DatosMuyLargosException ex) {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);

        } catch (EntidadDuplicadaException ex) {

            resp.setStatus(HttpServletResponse.SC_CONFLICT);
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

        Object objeto;
        try {

            if (ruta == null) {
                objeto = crudService.buscarTodos();
            } else {
                objeto = crudService.buscar(ruta);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            escritor.escribirJson(resp, objeto);

        } catch (NotFoundException ex) {

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            escritor.escribirError(ex.getMessage(), resp);

        } catch (ExceptionGenerica ex) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try {
            
            Destino edicion = gson.fromJson(req.getReader(), Destino.class);
            
            crudService.editar(edicion);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (EntidadDuplicadaException ex) {

            resp.setStatus(HttpServletResponse.SC_CONFLICT);
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id;
        String idString = obtenerParametroRuta(req);
        if(idString != null){
            id = Integer.valueOf(idString);
            try {
                crudService.eliminar(id);
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (ExceptionGenerica ex) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ex.getMessage(), resp);
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

}
