package com.datajpa.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author LuisMD
 */

@Entity
@Table(name="clientes")
public class Cliente  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @Column(name="fecha_creacion")
    @Temporal(TemporalType.DATE)//ME PERMITE CONFIGURAR EN QUE FORMATO GUARDO LA FECHA EN LA BASE DE DATOS
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
 
    private String foto;

    @OneToMany(mappedBy ="cliente",fetch=FetchType.LAZY,cascade=CascadeType.ALL)//un cliente  para muchas facturas
    private List<Factura> facturas;//COMO UN CLIENTE PUEDE TENER MUCHAS FACTURAS CREAMOS UNA COLECCION DE FACTURAS TIPO LIST

   
    public Cliente() {
      
      facturas  = new ArrayList<Factura>() ;
    }
    
    @PrePersist//PERMITE ENVIAR DATOS ANTES DE QUE  HAGA EL QUERY
    public void prePersist(){
       createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public void addFacturas(Factura f){//ESTE METODO NOS PERMITE AGREGAR LAS FACTURAS A LA COLLECCION
 
        this.facturas.add(f);
    } 

    @Override
    public String toString() {
        //return "Nombre:" + nombre + "- Apellido:" + apellido;
        return  nombre +" "+ apellido;
    }
   
   private static final long serialVersionUID = 1L;
    
}
