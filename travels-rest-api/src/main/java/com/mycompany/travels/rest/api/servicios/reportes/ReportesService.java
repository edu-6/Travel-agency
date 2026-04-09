/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.servicios.reportes;

import com.mycompany.travels.rest.api.db.reportes.AgenteConMasGanaciasDB;
import com.mycompany.travels.rest.api.db.reportes.AgenteConMasVentasDB;
import com.mycompany.travels.rest.api.db.reportes.PaqueteMasVendidoDB;
import com.mycompany.travels.rest.api.db.reportes.PaqueteMenosVendidoDB;
import com.mycompany.travels.rest.api.db.reportes.ReporteGanaciasDB;
import com.mycompany.travels.rest.api.db.reportes.ReporteOcupacionDB;
import com.mycompany.travels.rest.api.dtos.reportes.ReporteGanancia;
import com.mycompany.travels.rest.api.dtos.reportes.ReporteOcupacion;
import com.mycompany.travels.rest.api.dtos.reportes.ReproteAgenteMasGanancias;
import com.mycompany.travels.rest.api.dtos.reportes.agenteMasVentas.ReporteAgenteMasVentas;
import com.mycompany.travels.rest.api.dtos.reportes.ReportePaqueteMaxOMenosVendido;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.modelos.ReporteRequest;
import com.mycompany.travels.rest.api.modelos.enums.ReportesEnum;
import java.util.List;

/**
 *
 * @author edu
 */
public class ReportesService {

    private ReporteGanaciasDB gananciasDB = new ReporteGanaciasDB();
    private AgenteConMasVentasDB agenteMasVentasDB = new AgenteConMasVentasDB();
    private AgenteConMasGanaciasDB agenteMasGananciasDB = new AgenteConMasGanaciasDB();
    private PaqueteMasVendidoDB paqueteMasVendidoDB = new PaqueteMasVendidoDB();
    private PaqueteMenosVendidoDB paqueteMenosVendidoDB = new PaqueteMenosVendidoDB();
    private ReporteOcupacionDB reporteOcupacionDb = new ReporteOcupacionDB();

    public Object generarReporte(ReporteRequest request) throws ExceptionGenerica {

        String tipoReporte = request.getTipoReporte();

        if (tipoReporte.equals(ReportesEnum.REPORTE_GANANCIAS.getValor())) {
            return this.generarReproteGanacias(request);
        }

        if (tipoReporte.equals(ReportesEnum.REPORTE_AGENTE_MAS_VENTAS.getValor())) {
            return this.generarAgenteConMasVentas(request);
        }

        if (tipoReporte.equals(ReportesEnum.REPORTE_AGENTE_MAS_GANANCIAS.getValor())) {
            return this.generarAgenteConMasGanancias(request);
        }

        if (tipoReporte.equals(ReportesEnum.PAQUETE_MAS_VENDIDO.getValor())) {
            return this.generarPaqueteMasVendido(request);
        }

        if (tipoReporte.equals(ReportesEnum.PAQUETE_MENOS_VENDIDO.getValor())) {
            return this.generarPaqueteMenosVendido(request);
        }

        if (tipoReporte.equals(ReportesEnum.REPORTE_OCUPACION_POR_DESTINO.getValor())) {
            return this.generarReporteOcupacion(request);
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
        
        this.asegurarReporteNoNUll(reporte);

        return reporte;
    }

    private ReproteAgenteMasGanancias generarAgenteConMasGanancias(ReporteRequest request) throws ExceptionGenerica {
        this.validarRequest(request);

        ReproteAgenteMasGanancias reporte = null;

        if (request.reporteConRango()) {
            reporte = this.agenteMasGananciasDB.obtenerAgenteTopGananciaPeriodo(request);
        } else {
            reporte = this.agenteMasGananciasDB.obtenerAgenteTopGanancia();
        }
        
        this.asegurarReporteNoNUll(reporte);

        return reporte;
    }

    private ReportePaqueteMaxOMenosVendido generarPaqueteMasVendido(ReporteRequest request) throws ExceptionGenerica {
        this.validarRequest(request);

        ReportePaqueteMaxOMenosVendido reporte = null;

        if (request.reporteConRango()) {
            reporte = paqueteMasVendidoDB.obtenerPaqueteTopPeriodo(request);
            if (reporte != null) {
                reporte.setReservaciones(paqueteMasVendidoDB.obtenerDetallesPeriodo(reporte.getIdPaquete(), request));
            }
        } else {
            reporte = paqueteMasVendidoDB.obtenerPaqueteTop();
            if (reporte != null) {
                reporte.setReservaciones(paqueteMasVendidoDB.obtenerDetalles(reporte.getIdPaquete()));
            }
        }

        return reporte;
    }

    private ReportePaqueteMaxOMenosVendido generarPaqueteMenosVendido(ReporteRequest request) throws ExceptionGenerica {
        this.validarRequest(request);

        ReportePaqueteMaxOMenosVendido reporte = null;

        if (request.reporteConRango()) {
            reporte = paqueteMenosVendidoDB.obtenerPeorPaquetePeriodo(request);
            if (reporte != null) {
                reporte.setReservaciones(paqueteMasVendidoDB.obtenerDetallesPeriodo(reporte.getIdPaquete(), request));
            }
        } else {
            reporte = paqueteMenosVendidoDB.obtenerPeorPaquete();
            if (reporte != null) {
                reporte.setReservaciones(paqueteMasVendidoDB.obtenerDetalles(reporte.getIdPaquete()));
            }
        }
        
        this.asegurarReporteNoNUll(reporte);

        return reporte;
    }

    private List<ReporteOcupacion> generarReporteOcupacion(ReporteRequest request) throws ExceptionGenerica {

        this.validarRequest(request);

        if (request.reporteConRango()) {
            return this.reporteOcupacionDb.obtenerOcupacionPeriodo(request);
        } else {
            return this.reporteOcupacionDb.obtenerOcupacion();
        }
    }
    
    
    private void asegurarReporteNoNUll(Object reporte) throws ExceptionGenerica{
        if(reporte == null){
            throw new ExceptionGenerica(" no se encontró informacion");
        }
        
    }
    
    

}
