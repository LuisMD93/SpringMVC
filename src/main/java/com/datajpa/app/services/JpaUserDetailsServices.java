package com.datajpa.app.services;

import com.datajpa.app.models.dao.IUsersDao;
import com.datajpa.app.models.entity.Rol;
import com.datajpa.app.models.entity.Users;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author LuisMD
 */

@Service("jpaUserDetailsServices")
public class JpaUserDetailsServices implements UserDetailsService{
     
   @Autowired
   private IUsersDao isd;
   
   private Logger log = LoggerFactory.getLogger(JpaUserDetailsServices.class);

   @Override
   @Transactional(readOnly=true)
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
       Users u = isd.findByUsername(username);

       if (u == null) {
           log.error("Error login: no existe el usuario '" + username + "'");
           throw new UsernameNotFoundException("Username " + username + "no existe en el sistema");
       }

       List<GrantedAuthority> athorized = new ArrayList<GrantedAuthority>();
       for (Rol role : u.getRoles()) {
           log.info("Role : ".concat(role.getAuthority()));
           athorized.add(new SimpleGrantedAuthority(role.getAuthority()));
       }

       if (athorized.isEmpty()) {
           log.error("Error login: no existe el usuario '" + username + "'no tiene roles asignados!");
           throw new UsernameNotFoundException("Username " + username + "no existe en el sistema");
       }

       return new User(username, u.getPassword(), u.getEnabled(), true, true, true, athorized);
   }
}
