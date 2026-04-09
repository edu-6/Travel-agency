/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.modelos.enums;

/**
 *
 * @author edu
 */
public enum ReportesEnum {
    REPORTE_GANANCIAS("Reporte de ganancias"),
    REPORTE_AGENTE_MAS_VENTAS("Reporte del agente con mas ventas"),
    REPORTE_AGENTE_MAS_GANANCIAS("Reporte del agente con mas ganancias"),
    RESERVACION("Reservacion"),
    PAGO("Pago"),
    REEMBOLSO("Reembolso"),
    PENDIENTE("Pendiente"),
    COMPLETADO("Completado"),
    ERROR("Error");

    private final String valor;

    ReportesEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
