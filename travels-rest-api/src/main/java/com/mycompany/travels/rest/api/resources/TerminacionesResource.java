/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.travels.rest.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.servicios.ReservacionesCrudService;
import com.mycompany.travels.rest.api.utils.ConvertidorFechas;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
@WebServlet(name = "TerminacionesResource", urlPatterns = {"/api/terminaciones/*"})
public class TerminacionesResource extends HttpServlet {
    
    private EscritorJson escritor = new EscritorJson();
    
    private ReservacionesCrudService crudService = new  ReservacionesCrudService();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            crudService.marcarReservacionesComoTeminadas();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ExceptionGenerica ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }
    
    

}
