/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.travels.rest.api.resources;

import com.mycompany.travels.rest.api.archivoTexto.LectorDeArchivoTexto;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
@MultipartConfig
@WebServlet(name = "ArchivoTextoResource", urlPatterns = {"/archivo-texto/*"})
public class ArchivoTextoResource extends HttpServlet {

    private LectorDeArchivoTexto lector = new LectorDeArchivoTexto();
    private EscritorJson escritor = new EscritorJson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Part filePart = req.getPart("file");

        if (filePart == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se recibió ningún archivo.");
            return;
        }

        try (InputStream is = filePart.getInputStream()) {
            
            ArrayList<String> logs = lector.procesarArchivo(is);
            resp.setStatus(HttpServletResponse.SC_OK);
            escritor.escribirJson(resp, logs);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
