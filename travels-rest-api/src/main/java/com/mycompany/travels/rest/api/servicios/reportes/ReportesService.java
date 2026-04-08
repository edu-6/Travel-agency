/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios.reportes;

import com.mycompany.travels.rest.api.db.reportes.GananciasDB;
import com.mycompany.travels.rest.api.dtos.reportes.ReporteGanancia;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.ReporteRequest;
import com.mycompany.travels.rest.api.modelos.enums.ReportesEnum;

/**
 *
 * @author edu
 */
public class ReportesService {

    private GananciasDB gananciasDB = new GananciasDB();

    private ReporteGanancia generarReproteGanacias(ReporteRequest request) throws ExceptionGenerica {

        if (request == null) {
            throw new ExceptionGenerica("Erorr al recibir la peticion");
        }

        request.validarPeriodos();

        double ingresos;
        double engresos;
        double ganancia;

        if (request.reporteConRango()) {
            ingresos = gananciasDB.obtenerIngresosPeriodo(request);
            engresos = gananciasDB.obtenerEgresosPeriodo(request);

        } else {
            ingresos = gananciasDB.obtenerIngresos();
            engresos = gananciasDB.obtenerEgresos();
        }

        ganancia = ingresos - engresos;

        return new ReporteGanancia(ingresos, engresos, ganancia);
    }

    public Object generarReporte(ReporteRequest request) throws ExceptionGenerica {

        String tipoReporte = request.getTipoReporte();

        if (tipoReporte.equals(ReportesEnum.REPORTE_GANANCIAS.getValor())) {
            return this.generarReproteGanacias(request);
        }

        return null;

    }

}
