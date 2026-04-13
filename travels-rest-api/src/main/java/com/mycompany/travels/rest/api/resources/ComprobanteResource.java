/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.travels.rest.api.resources;

import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.pdfs.CreadorDeComprobante;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author edu
 */
@WebServlet(name = "PdfResources", urlPatterns = {"/api/comprobante/*"})
public class ComprobanteResource extends HttpServlet {

    private CreadorDeComprobante creadorComprobante = new CreadorDeComprobante();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String ruta = obtenerParametroRuta(req);
        if (ruta != null && this.esNumero(ruta)) {
            this.ponerEncabezados(resp);
            ServletOutputStream out = resp.getOutputStream();
            out.flush();
            try {
                creadorComprobante.crearComprobante(out, Integer.valueOf(ruta));
            } catch (ExceptionGenerica ex) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    private void ponerEncabezados(HttpServletResponse resp) {
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"comprobante.pdf\"");
        resp.resetBuffer();
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
