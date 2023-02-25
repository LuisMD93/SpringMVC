package com.datajpa.app.controllers;

import com.datajpa.app.models.entity.Cliente;
import com.datajpa.app.models.entity.Factura;
import com.datajpa.app.models.entity.ItemFactura;
import com.datajpa.app.models.entity.Productos;
import com.datajpa.app.services.IClienteService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultMessageCodesResolver.Format;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author LuisMD
 */
@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("facturas/ep0dofesk")
@SessionAttributes("factura")//Nos permite mantener el objecto factura hasta que se procese el formulario o hasta que se envia los datos al metodo guardar
public class facturaController {

     @Autowired
     private IClienteService ics;

     private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/form/{clienteID}")
    public String crear(@PathVariable(value = "clienteID") Long id,Map<String,Object> model,RedirectAttributes redireccionar) {
        
        Cliente c = ics.findOne(id);
        if (c == null) {
            redireccionar.addAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        Factura f = new Factura(); 

        f.setCliente(c);
        model.put("factura", f);
        model.put("title","Crear Factura para el cliente: "+c.getNombre() +" "+ c.getApellido());
        return "factura/form";
    }


    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Productos> cargarProductos(@PathVariable String term) {
      
        System.out.println(term+"*******************");
        return ics.findByNombre(term);
    }

    @GetMapping("/AddItems")
    public String Items(){ return "factura/plantillaItems"; }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura, BindingResult result ,@RequestParam(name="item_id[]" , required=false) Long[] itemid,//COMO ESTOS DATOS NO ESTAN MAPEADOS AL FORM 
                                       @RequestParam(name="cantidad[]" , required=false) Integer[] kang,//LOS CAPTURAMOS MEDIANTE EL REQUESTPARAM
                          RedirectAttributes flash ,SessionStatus status,Model model){ 

        String nombretitular ="Crear Factura para el cliente: "+ factura.getCliente().getNombre()+" "+ factura.getCliente().getApellido();

        if(result.hasErrors()){

          model.addAttribute("title",nombretitular);
          return "factura/form";
        }

        if(itemid == null || itemid.length == 0) {
            model.addAttribute("title",nombretitular);
            model.addAttribute("error","Error : La factura NO puede tener lineas vacias!! ");
            return "factura/form";
        }

        for (int i = 0; i < itemid.length; i++) {
            Productos p = ics.findProductoByid(itemid[i]);

            ItemFactura lines = new ItemFactura();
            lines.setCantidad(kang[i]);
            lines.setProducto(p);
            factura.addItemsFacturas(lines);
            System.out.println("Info .--------------------------------> "+factura.getCliente().getId());
            log.info("ID : "+itemid[i]+" , CANTIDAD : "+kang[i]);
        }
        
        ics.saveFactura(factura);
        status.setComplete();
        flash.addFlashAttribute("success","Sape!!!");
        //System.out.println("Info .--------------------------------> "+xxx.getCliente().getId());
        return "redirect:/showdetails/"+factura.getCliente().getId();
    }

   @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash/*@RequestParam(name = "format") Format format*/) {

        //Factura factura = ics.findFacturaByid(id);
        Factura factura = ics.fecthByIdWithClientesWithItemFacturaWithProductos(id);
        if (factura == null) {
            flash.addFlashAttribute("error", "La factura no existe en la base de datos");
            return "redirect:/lista";
        }

        model.addAttribute("factura", factura);
        model.addAttribute("title", "Descripcion " + factura.getDescripcion());
        return "factura/ver";
    }

    @GetMapping("/drop/{id}")
    public String drop(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
       
        Factura factura = ics.findFacturaByid(id);
        if (factura != null) {
      
            ics.deleteFactura(id);
            flash.addFlashAttribute("success", "Factura eliminada con extio!!");
            return "redirect:/showdetails/"+factura.getCliente().getId();
        }

        flash.addFlashAttribute("error", "La factura no existe en la base de datos, No se pudo eliminaro!! "+factura.getId());
        return "redirect:/lista";
    }



}
 