package com.datajpa.app.models.dao;

import com.datajpa.app.models.entity.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author LuisMD
 */
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{

    @Query("select c from Cliente c  left join fetch c.facturas fact where c.id=?1")
    public Cliente fetchByIdWithFacturas(long id);
  
}
