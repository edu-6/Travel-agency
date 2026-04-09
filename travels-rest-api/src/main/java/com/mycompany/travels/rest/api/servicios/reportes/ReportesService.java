/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios.reportes;

import com.mycompany.travels.rest.api.db.reportes.AgenteConMasVentasDB;
import com.mycompany.travels.rest.api.db.reportes.ReporteGanaciasDB;
import com.mycompany.travels.rest.api.dtos.reportes.ReporteGanancia;
import com.mycompany.travels.rest.api.dtos.reportes.agenteMasVentas.ReporteAgenteMasVentas;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.ReporteRequest;
import com.mycompany.travels.rest.api.modelos.enums.ReportesEnum;

/**
 *
 * @author edu
 */
public class ReportesService {

    private ReporteGanaciasDB gananciasDB = new ReporteGanaciasDB();
    private AgenteConMasVentasDB agenteMasVentasDB = new AgenteConMasVentasDB();

    public Object generarReporte(ReporteRequest request) throws ExceptionGenerica {

        String tipoReporte = request.getTipoReporte();

        if (tipoReporte.equals(ReportesEnum.REPORTE_GANANCIAS.getValor())) {
            return this.generarReproteGanacias(request);
        }

        if (tipoReporte.equals(ReportesEnum.REPORTE_AGENTE_MAS_VENTAS.getValor())) {
            return this.generarAgenteConMasVentas(request);
        }
        return null;
    }

    private void validarRequest(ReporteRequest request) throws ExceptionGenerica {
        if (request == null) {
            throw new ExceptionGenerica("Erorr al recibir la peticion");
        }

        request.validarPeriodos();
    }

    private ReporteGanancia generarReproteGanacias(ReporteRequest request) throws ExceptionGenerica {

        this.validarRequest(request);

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

    private ReporteAgenteMasVentas generarAgenteConMasVentas(ReporteRequest request) throws ExceptionGenerica {

        this.validarRequest(request);

        ReporteAgenteMasVentas reporte = null;

        if (request.reporteConRango()) {
            reporte = agenteMasVentasDB.obtenerAgenteTopPeriodo(request);
            reporte.setReservaciones(agenteMasVentasDB.obtenerDetalleReservacionPeriodo(reporte.getIdAgente(), request));
        } else {
            reporte = agenteMasVentasDB.obtenerAgenteTop();
            reporte.setReservaciones(agenteMasVentasDB.obtenerDetalleReservacion(reporte.getIdAgente()));
        }

        return reporte;
    }

}
