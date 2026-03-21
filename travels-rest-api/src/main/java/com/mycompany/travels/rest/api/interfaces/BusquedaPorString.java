/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.travels.rest.api.interfaces;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public interface BusquedaPorString <T> {
    public ArrayList<T> buscarPorString(String parametro) throws ExceptionGenerica;
}
