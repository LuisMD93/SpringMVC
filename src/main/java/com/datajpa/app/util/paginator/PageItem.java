package com.datajpa.app.util.paginator;

/**
 *
 * @author LuisMD
 */
public class PageItem {

   private boolean actual;
   private int numero;

    public PageItem(int numero, boolean actual) {
        this.numero = numero;
        this.actual = actual;
    }

    public boolean isActual() {
        return actual;
    }

    public int getNumero() {
        return numero;
    }
    
    

}
