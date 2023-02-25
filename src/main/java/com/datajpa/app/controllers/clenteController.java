package com.datajpa.app.controllers;

import com.datajpa.app.models.entity.Cliente;
import com.datajpa.app.services.IClienteService;
import com.datajpa.app.services.IUploadFileServices;
import com.datajpa.app.util.paginator.PageRender;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author LuisMD
 */
@Controller
@SessionAttributes("dataCliente")//permite enviar el objeto atraves del request para evidar usar un input hidden con el id para podr actualizar
public class clenteController {

    @Autowired
    private IClienteService ics;

    @Autowired
    private IUploadFileServices uploadFileService;

    @Autowired
    private MessageSource messageSource;
   
    protected final Log logger  = LogFactory.getLog(this.getClass());

    @Secured({"ROLE_USER"})//lista para mas usuarios
    @RequestMapping(value = {"/lista", "/"}, method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,Authentication auth ,HttpServletRequest r , Locale locale) {

         if (auth != null) {

            logger.info("Hola usuario autenticado, tu username es: ".concat(auth.getName()).concat("desde el controlador"));
        }

        Authentication authen = SecurityContextHolder.getContext().getAuthentication();

          if (authen != null) {

            logger.info("Utilizano la forma estatica Authentication authen = SecurityContextHolder.getContext().getAuthentication();".concat(" Hola usuario autenticado, tu username es: ".concat(auth.getName()).concat("desde el controlador")));
        }

        if (hasRole("ROLE_ADMIN")) {
           logger.info("Hola ".concat(auth.getName()).concat(" Tiene acceso!!"));
        }else{
           logger.info("Hola ".concat(auth.getName()).concat(" No tienes acceso!!"));
        }

        //OTRA ALTERNATIVA PARA CONOCE QUIEN INICIA SESION
        SecurityContextHolderAwareRequestWrapper scharw = new SecurityContextHolderAwareRequestWrapper(r,"ROLE_");
        if(scharw.isUserInRole("ADMIN")){  logger.info("Hola usando SecurityContextHolderAwareRequestWrapper  ".concat(auth.getName()).concat(" Tiene acceso!!")); }
        else{
          logger.info("Hola usando SecurityContextHolderAwareRequestWrapper ".concat(auth.getName()).concat(" No tienes acceso!!"));
        }
        //OTRA FORMA DE VALIDAR EL ROL
        if(r.isUserInRole("ADMIN")){  logger.info("Hola usando HttpRequest ".concat(auth.getName()).concat(" Tiene acceso!!")); }
        else{
          logger.info("Hola usando HttpRequest ".concat(auth.getName()).concat(" No tienes acceso!!"));
        }

        Pageable pageRequest = PageRequest.of(page, 6);

        Page<Cliente> dataList = ics.findAll(pageRequest);

        PageRender<Cliente> pageRender = new PageRender("/", dataList);
        model.addAttribute("title", messageSource.getMessage("text.dataCliente.listar.title", null,locale));
        model.addAttribute("dataList", dataList);
        model.addAttribute("page", pageRender);
        
        return "lista";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String form(Map<String, Object> model) {

        Cliente cliente = new Cliente();
        model.put("title", "Formulario Clientes");
        model.put("dataCliente", cliente);
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String form(@Valid @ModelAttribute("dataCliente") Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Formulario Clientes");
            return "form";
        }
        if (!foto.isEmpty()) {

           
        if(cliente.getId() !=null
           && cliente.getId()>0
           && cliente.getFoto() !=null
           && cliente.getFoto().length()>0 ){ 
                  
              uploadFileService.delete(cliente.getFoto());
            }
  
             String uniqueFileName= null;
            try {
                uniqueFileName = uploadFileService.copy(foto);
            } catch (IOException ex) {
               ex.printStackTrace();
            }

             flash.addFlashAttribute("info", "Recuros Agragado correctamente " + uniqueFileName + " ");
             cliente.setFoto(uniqueFileName);
            //1.PARA AGREGAR IMAGEN DE MANERA LOCAL ES DECIR DENTRO DEL PROJECTO
            //Path directorioImg = Paths.get("src//main//resources//static//uploads");
            //String rootPath = directorioImg.toFile().getAbsolutePath();          
            //2.PARA AGREGAR IMAGEN DENTROL DEL PC  ES DECIR DE MANERA EXTERNA AL PROJECTO
            //String rootPath = "C://Windows//Temp//uploads";
            //3.AGREGAR IMMAGEN EN UN DIRECTORIO JUNTO A NUESTRO PROYECTO
           


                /*ESTO FUNCIONA CON LO DICHO EN COMENTARIO 1 Y 2
                byte[] bytes = foto.getBytes();
                Path rutacompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
                Files.write(rutacompleta, bytes);*/
                //cliente.setFoto(foto.getOriginalFilename());
                System.out.println("---------------->   " + foto.getOriginalFilename());
            }
        System.out.println("---------------->   " + foto.getOriginalFilename());
        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con exito!!!" : "Cliente creado con exito";

        ics.save(cliente);
        status.setComplete();//borra datos 
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        Cliente dataCliente = null;

        if (id > 0) {
            dataCliente = ics.findOne(id);
            if (dataCliente == null) {
                flash.addAttribute("error", "ID no existe!!");
                return "redirect/:lista";
            }
        } else {
            flash.addAttribute("error", "ID no puede ser 0");
            return "redirect/:lista";
        }
        model.put("dataCliente", dataCliente);
        model.put("title", "Editar Cliente");
        return "form";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        
        if (id > 0) {
 
            Cliente cliente =ics.findOne(id);//OBTENEMOS EL CLIENTE ANTES DE ELIMINARLO  
            ics.delete(id);
            if(uploadFileService.delete(cliente.getFoto())){
               flash.addFlashAttribute("error", "Elemento "+ cliente.getFoto()+" Eliminado con exito!!");
            }
            
        }
        return "redirect:/lista";
    }

    @GetMapping(value = "/showdetails/{id}")
    public String showdetails(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        //Cliente cliente = ics.findOne(id);
        Cliente cliente = ics.fetchByIdWithFacturas(id); 
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/lista";
        }

        model.put("dataCliente", cliente);
        model.put("title", "Detalles cliente " + cliente.getNombre());
        return "showdetails";
    }

    //ESTE METODO NOS PERMITE CARGAR LA IMAGEN DE MANERA PROGRMATICA ATRAVEZ DE LA RESPUESTA DE UN Resource.
    @GetMapping(value = "/uploads/{filename:.+}")//El +. evita que spring elimine la extension del archivo
    public ResponseEntity<Resource> Imgshow(@PathVariable String filename)  {

       Resource r = null;
        try {
            r = uploadFileService.load(filename);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + r.getFilename() + "\"").body(r);
    }
 
    //video 203
    private boolean hasRole(String role){

       SecurityContext context = SecurityContextHolder.getContext();
       if(context == null){
         return false;
       }   
        Authentication authen = context.getAuthentication();

       if(authen == null){
         return false;
       } 

       Collection<? extends GrantedAuthority> autoritis = authen.getAuthorities();
       return autoritis.contains(new SimpleGrantedAuthority(role));
       /** Funciona arriba es otr manera de hacerlo  
       
        for (GrantedAuthority autoriti : autoritis) {
            if (role.equals(autoriti.getAuthority())) {
                  logger.info("-Hola usuario ".concat(authen.getName()).concat(" Tu rol es ".concat(autoriti.getAuthority())));
                  return true;
            }
        }
       return false;*/
    }
}
