/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.travels.rest.api.modelos.ErrorRequest;
import com.mycompany.travels.rest.api.utils.ConvertidorFechas;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class EscritorJson {
    private final Gson gson = new Gson();
    private Gson gsonParaFecha = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();
    
    public void escribirJson(HttpServletResponse res, Object data) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(gson.toJson(data));
    }

    public void escribirError(String mesaje, HttpServletResponse reponse) throws IOException {
        ErrorRequest error = new ErrorRequest(mesaje, mesaje);
        this.escribirJson(reponse, error);
    }
    
    
    public void escribirJsonConFecha(HttpServletResponse res, Object data) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(gsonParaFecha.toJson(data));
    }
}
