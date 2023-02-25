package com.datajpa.app.view.pdf;

import com.datajpa.app.models.entity.Factura;
import com.datajpa.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

/**
 *
 * @author LuisMD
 */
@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView{


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

  
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Factura f = (Factura) model.get("factura");
        Locale locale = localeResolver.resolveLocale(request);

        MessageSourceAccessor mensajes =  getMessageSourceAccessor(); //Otra manera de traducir

        PdfPTable tabla = new PdfPTable(1);
        tabla.setSpacingAfter(20);

        PdfPCell cell = null;
        String mensaje = String.format(messageSource.getMessage("text.factura.ver.datos.dataCliente.title", null, locale));
        cell = new PdfPCell(new Phrase(mensaje));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);
        tabla.addCell(cell);

        tabla.addCell(f.getCliente().getNombre() + " " + f.getCliente().getApellido());
        tabla.addCell(f.getCliente().getEmail());

        PdfPTable tabla2 = new PdfPTable(1);
        tabla2.setSpacingAfter(20);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setPadding(8f);
        tabla2.addCell(cell);/*
        tabla2.addCell("Folio: " + f.getId());
        tabla2.addCell("Descripcion: " + f.getDescripcion());
        tabla2.addCell("Fecha: " + f.getCreateAt());*/

        tabla2.addCell(mensajes.getMessage("text.dataCliente.factura.folio") + ": " + f.getId());
        tabla2.addCell(mensajes.getMessage("text.dataCliente.factura.descripcion") + ": " + f.getDescripcion());
        tabla2.addCell(mensajes.getMessage("text.dataCliente.factura.fecha") + ": " + f.getCreateAt());

        document.add(tabla);
        document.add(tabla2);

        PdfPTable tabla3 = new PdfPTable(4);
        tabla3.setWidths(new float[]{3.5f, 1, 1, 1});/*
        tabla3.addCell("Producto");
        tabla3.addCell("Precio");
        tabla3.addCell("cantidad");
        tabla3.addCell("Total");*/

        tabla3.addCell(mensajes.getMessage("text.factura.form.item.nombre"));
        tabla3.addCell(mensajes.getMessage("text.factura.form.item.precio"));
        tabla3.addCell(mensajes.getMessage("text.factura.form.item.cantidad"));
        tabla3.addCell(mensajes.getMessage("text.factura.form.item.total"));

        for (ItemFactura item : f.getItems()) {

            tabla3.addCell(item.getProducto().getNombre());
            tabla3.addCell(item.getProducto().getPrecio().toString());
            cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla3.addCell(cell);
            tabla3.addCell(String.valueOf(item.CalcularImporte()));
        }

        cell = new PdfPCell(new Phrase("Total :"));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        tabla3.addCell(cell);
        tabla3.addCell(f.getTotal().toString());

        document.add(tabla3);

    }
}
