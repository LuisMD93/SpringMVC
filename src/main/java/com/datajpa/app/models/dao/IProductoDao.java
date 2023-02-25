package com.datajpa.app.models.dao;

import com.datajpa.app.models.entity.Productos;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author LuisMD
 */

public interface IProductoDao  extends CrudRepository<Productos,Long>{

    @Query("select product from Productos product where product.nombre like %?1%") 
    public List<Productos> findByNombre(String term);

    public List<Productos> findByNombreLikeIgnoreCase(String term);
}
