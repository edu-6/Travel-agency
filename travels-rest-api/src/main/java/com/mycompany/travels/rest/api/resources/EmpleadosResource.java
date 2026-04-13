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
import com.mycompany.travels.rest.api.modelos.Empleado;
import com.mycompany.travels.rest.api.servicios.EmpleadosCrudService;
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
@WebServlet(name = "EmpleadosResource", urlPatterns = {"/api/empleados/*"})
public class EmpleadosResource extends HttpServlet {

    private Gson gson = new Gson();
    private EmpleadosCrudService crudService = new EmpleadosCrudService();
    private EscritorJson escritor = new EscritorJson();

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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        

        try {
            
            Empleado empleado = gson.fromJson(req.getReader(), Empleado.class);
            
            crudService.editar(empleado);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        

        try {
            
            Empleado empleado = gson.fromJson(req.getReader(), Empleado.class);
            
            
            crudService.crear(empleado);
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
        
        if(esNumero(ruta)){
            try {
                ArrayList<Empleado> lista = crudService.buscarEmpleadosPorRol(Integer.valueOf(ruta));
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, lista);
            } catch (ExceptionGenerica ex) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ex.getMessage(), resp);
            }
        }else{
            try {
                Empleado paqueteGeneral = crudService.buscarEmpleado(ruta);
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, paqueteGeneral);
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

    private boolean esNumero(String parametro) {
        try {
            Integer.valueOf(parametro);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

}
