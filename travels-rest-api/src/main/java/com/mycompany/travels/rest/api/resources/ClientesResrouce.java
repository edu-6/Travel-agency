/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.travels.rest.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.travels.rest.api.exceptions.CamposVaciosException;
import com.mycompany.travels.rest.api.exceptions.DatosMuyLargosException;
import com.mycompany.travels.rest.api.exceptions.EntidadDuplicadaException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.Cliente;
import com.mycompany.travels.rest.api.servicios.ClientesCrudService;
import com.mycompany.travels.rest.api.utils.ConvertidorFechas;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
@WebServlet(name = "ClientesResrouce", urlPatterns = {"/api/clientes/*"})
public class ClientesResrouce extends HttpServlet {

    private ClientesCrudService crudService = new ClientesCrudService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parametro = this.obtenerParametroRuta(req);
        if (parametro != null) {
            try {
                crudService.eliminar(parametro);
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
            Cliente cliente = gson.fromJson(req.getReader(), Cliente.class);
            
            
            crudService.editar(cliente);
            resp.setStatus(HttpServletResponse.SC_OK);

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
            
            Cliente cliente = gson.fromJson(req.getReader(), Cliente.class);
            
            
            crudService.crear(cliente);
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
        String parametro = obtenerParametroRuta(req);

        if (esNumero(parametro)) {
            try {
                Cliente cliente = crudService.buscarPorID(parametro);
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJsonConFecha(resp, cliente);
            } catch (ExceptionGenerica ex) {
                
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ex.getMessage(), resp);
            }
        } else {
            try {
                Cliente cliente = crudService.buscarPorNombre(parametro);
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJsonConFecha(resp, cliente);
                
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
            new BigInteger(parametro);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

}
