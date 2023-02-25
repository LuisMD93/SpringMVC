package com.datajpa.app.services;

import com.datajpa.app.models.entity.Cliente;
import com.datajpa.app.models.entity.Factura;
import com.datajpa.app.models.entity.Productos;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author LuisMD
 */
public interface IClienteService {

    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable p);

    public void save(Cliente c);

    public Cliente findOne(Long id);

    public void delete(Long id); 
    
    public List<Productos> findByNombre(String term);

    public void  saveFactura(Factura factura);

    public Productos findProductoByid(Long id);

    public Factura findFacturaByid(Long id);

    public void deleteFactura(Long id); 

    public Factura fecthByIdWithClientesWithItemFacturaWithProductos(long id);

    public Cliente fetchByIdWithFacturas(long id);


}
