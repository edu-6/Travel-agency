/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.travels.rest.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.travels.rest.api.dtos.cancelaciones.CancelacionRequest;
import com.mycompany.travels.rest.api.dtos.reservaciones.IdReservacion;
import com.mycompany.travels.rest.api.dtos.reservaciones.ReservacionRequest;
import com.mycompany.travels.rest.api.dtos.reservaciones.ReservacionResponse;
import com.mycompany.travels.rest.api.exceptions.CamposVaciosException;
import com.mycompany.travels.rest.api.exceptions.DatosMuyLargosException;
import com.mycompany.travels.rest.api.exceptions.EntidadDuplicadaException;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.servicios.CancelacionesCrudService;
import com.mycompany.travels.rest.api.servicios.ReservacionesCrudService;
import com.mycompany.travels.rest.api.utils.ConvertidorFechas;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
@WebServlet(name = "ReservacionesResource", urlPatterns = {"/api/reservaciones/*"})
public class ReservacionesResource extends HttpServlet {

    private ReservacionesCrudService crudService = new ReservacionesCrudService();
    private CancelacionesCrudService cancelacionesCrudService = new CancelacionesCrudService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        IdReservacion id = gson.fromJson(req.getReader(), IdReservacion.class);
        
        CancelacionRequest cancelacion = new CancelacionRequest(id.getIdReservacion(), LocalDate.now());
        
        try {
            this.cancelacionesCrudService.cancelarReservacion(cancelacion);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ExceptionGenerica ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReservacionRequest reservacion = gson.fromJson(req.getReader(), ReservacionRequest.class);

        try {
           int id = crudService.crear(reservacion);
           IdReservacion idR = new IdReservacion(id);
           resp.setStatus(HttpServletResponse.SC_OK);
           escritor.escribirJson(resp, idR);
            

        } catch (CamposVaciosException | DatosMuyLargosException ex) {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);

        } catch (EntidadDuplicadaException ex) {

            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            escritor.escribirError(ex.getMessage(), resp);

        } catch (ExceptionGenerica ex) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = obtenerParametroRuta(req);

        if (ruta != null) {

            if (esNumero(ruta)) {
                try {
                    // buscar por id
                    ReservacionResponse reservacion = crudService.buscarUnaPorID(Integer.valueOf(ruta));
                    resp.setStatus(HttpServletResponse.SC_OK);
                    escritor.escribirJsonConFecha(resp, reservacion);
                } catch (ExceptionGenerica ex) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    escritor.escribirError(ex.getMessage(), resp);
                }
            } else {
                if (ruta.equals("hoy")) {
                    try {
                        ArrayList<ReservacionResponse> reservaciones = crudService.buscarReservacionesDelDia();
                        resp.setStatus(HttpServletResponse.SC_OK);
                        escritor.escribirJsonConFecha(resp, reservaciones);
                    } catch (ExceptionGenerica ex) {
                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        escritor.escribirError(ex.getMessage(), resp);
                    }
                }
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
