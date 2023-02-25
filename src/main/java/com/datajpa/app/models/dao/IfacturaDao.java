package com.datajpa.app.models.dao;

import com.datajpa.app.models.entity.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author LuisMD
 */
public interface IfacturaDao  extends CrudRepository<Factura, Long>{ 

   @Query("select fact from Factura fact join fetch fact.cliente c join fetch fact.items linea join fetch linea.producto where fact.id=?1")
   public Factura fecthByIdWithClientesWithItemFacturaWithProductos(long id);//para evitar usar carga peresoza
    
}
