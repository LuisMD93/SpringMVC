package com.datajpa.app.models.dao;

import com.datajpa.app.models.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author LuisMD
 */
public interface IClienteDaoPagination extends PagingAndSortingRepository<Cliente, Long>{


  
}
