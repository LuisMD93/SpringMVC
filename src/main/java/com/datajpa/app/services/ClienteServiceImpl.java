package com.datajpa.app.services;

import com.datajpa.app.models.dao.IClienteDao;
import com.datajpa.app.models.dao.IProductoDao;
import com.datajpa.app.models.dao.IfacturaDao;
import com.datajpa.app.models.entity.Cliente;
import com.datajpa.app.models.entity.Factura;
import com.datajpa.app.models.entity.Productos;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author LuisMD
 */
@Service
public class ClienteServiceImpl implements IClienteService{
    
    @Autowired
   // @Qualifier("cliente_dao_jpa")
    private IClienteDao icd; 

    @Autowired
   // @Qualifier("cliente_dao_jpa")
    private IClienteDao icdp; 

    @Autowired
    private IProductoDao productoDao;

    @Autowired
    private IfacturaDao facturaDao; 

    @Override
    @Transactional
    public List<Cliente> findAll(){return (List<Cliente>) icd.findAll();}

    @Override
    @Transactional
    public void save(Cliente c){ icd.save(c);}

    @Override
    public Cliente findOne(Long id){return icd.findById(id).orElse(null);}

    
    @Override
    @Transactional
    public void delete(Long id){ icd.deleteById(id);}

    @Override
    @Transactional
    public Page<Cliente> findAll(Pageable p){return icd.findAll(p);}

    @Override
    @Transactional
    public List<Productos> findByNombre(String term){
        return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura){
       facturaDao.save(factura);
    }

    @Override
    @Transactional
    public Productos findProductoByid(Long id){ 

        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Factura findFacturaByid(Long id){ 

        return facturaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFactura(Long id){ facturaDao.deleteById(id);}

    @Override
    @Transactional
    public Factura fecthByIdWithClientesWithItemFacturaWithProductos(long id){ return facturaDao.fecthByIdWithClientesWithItemFacturaWithProductos(id); }
    
    @Override
    @Transactional
    public Cliente fetchByIdWithFacturas(long id){ return icd.fetchByIdWithFacturas(id);}
}
