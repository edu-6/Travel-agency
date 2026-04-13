/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.travels.rest.api.pdfs;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import com.mycompany.travels.rest.api.dtos.reservaciones.ReservacionResponse;
import com.mycompany.travels.rest.api.exceptions.ExceptionGenerica;
import com.mycompany.travels.rest.api.servicios.ReservacionesCrudService;
import jakarta.servlet.ServletOutputStream;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class CreadorDeComprobante {

    private ReservacionesCrudService crudService = new ReservacionesCrudService();

    public void crearComprobante(ServletOutputStream archivoPdf, int id) throws ExceptionGenerica {
        ReservacionResponse reservacion = crudService.buscarUnaPorID(id);
        this.escribirPdf(archivoPdf, reservacion);
    }

    private void escribirPdf(ServletOutputStream stream, ReservacionResponse reservacion) throws ExceptionGenerica {

        try {
            PdfWriter writer = new PdfWriter(stream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("COMPROBANTE DE PAGO").setBold().setFontSize(18));
            document.add(new Paragraph("ha completado el pago de su reservacion!"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Titular: "+ reservacion.getTitular())).setBold();
            document.add(new Paragraph("Total pagado: " + reservacion.getTotalPagado()));
            document.add(new Paragraph("Paquete: " + reservacion.getNombrePaquete()));
            document.add(new Paragraph("Fecha descarga: " + LocalDate.now()));

            document.close();
        } catch (Exception e) {
            throw new ExceptionGenerica("Error fatal al construir el PDF");
        }
    }
}
