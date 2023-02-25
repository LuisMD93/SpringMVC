package com.datajpa.app.models.dao;

import com.datajpa.app.models.entity.Users;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author LuisMD
 */

public interface IUsersDao extends CrudRepository<Users, Long>{
    
   public Users findByUsername(String u);
}
