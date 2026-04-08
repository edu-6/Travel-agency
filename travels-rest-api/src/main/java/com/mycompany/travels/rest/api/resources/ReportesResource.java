/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.travels.rest.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.ReporteRequest;
import com.mycompany.travels.rest.api.servicios.reportes.ReportesService;
import com.mycompany.travels.rest.api.utils.ConvertidorFechas;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edu
 */
@WebServlet(name = "ReportesResource", urlPatterns = {"/api/reportes/*"})
public class ReportesResource extends HttpServlet {

    private ReportesService service = new ReportesService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ReporteRequest request = gson.fromJson(req.getReader(), ReporteRequest.class);

        try {
            Object response = service.generarReporte(request);
            resp.setStatus(HttpServletResponse.SC_OK);
            escritor.escribirJsonConFecha(resp, response);
        } catch (ExceptionGenerica ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }

}
