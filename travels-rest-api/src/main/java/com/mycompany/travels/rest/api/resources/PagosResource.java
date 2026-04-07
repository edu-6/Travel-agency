/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.travels.rest.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.PagoReservacion;
import com.mycompany.travels.rest.api.servicios.PagosCrudService;
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
@WebServlet(name = "PagosResource", urlPatterns = {"/api/pagos/*"})
public class PagosResource extends HttpServlet {
    
     private PagosCrudService crudService = new PagosCrudService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        PagoReservacion pago = gson.fromJson(req.getReader(), PagoReservacion.class);
        pago.setFechaPago(LocalDate.now());
        
          try {
            crudService.crear(pago);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ExceptionGenerica ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }
       
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = obtenerParametroRuta(req);
        int idReservacion = 0;
        if(ruta != null){
            idReservacion = Integer.valueOf(ruta);
        }
        
         try {
              ArrayList<PagoReservacion> lista = crudService.buscarVariosInt(idReservacion);
             resp.setStatus(HttpServletResponse.SC_OK);
             
             escritor.escribirJsonConFecha(resp,lista );
         } catch (ExceptionGenerica ex) {
             resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
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
