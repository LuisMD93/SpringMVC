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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author LuisMD
 */

@Entity
@Table(name="facturas")
public class Factura  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
   @NotEmpty
   private String descripcion; 
   @NotEmpty 
   private String observaciones; 

   @Temporal(TemporalType.DATE) 
   @Column(name="fecha_creacion")
   private Date createAt;
  
   @ManyToOne(fetch=FetchType.LAZY) //MUCHAS FACTURAS PATA UN CLIENTE 
   private Cliente cliente;

   @OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)//una factura  para muchas items
   @JoinColumn(name="factura_id")//INDICAMOS EL CAMPO QUE VA A REALICONAR LAS TABLAS FACTURAS CON FACTURAS ITEMS
   private List<ItemFactura> items;//COMO UNA FACTURA PUEDE TENER  ITEMS CREAMOS UNA COLECCION DE ITEMSFACTURA TIPO LIST

    @PrePersist//PERMITE ENVIAR DATOS ANTES DE QUE  HAGA EL QUERY
    public void prePersist() {
        createAt = new Date();
    }

   public Factura(){ this.items = new ArrayList<>();}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }

    public void addItemsFacturas(ItemFactura f){//ESTE METODO NOS PERMITE AGREGAR LAS FACTURAS A LA COLLECCION
 
        this.items.add(f);
    }

    public Double getTotal() {

        Double total = 0.0;
        int size = items.size();
        for (int i = 0; i < size; i++) {
            total += items.get(i).CalcularImporte();
        }
        return total;
    }

   private static final long serialVersionUID = 1L;
   
}
