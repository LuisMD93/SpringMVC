package com.datajpa.app.services;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author LuisMD
 */
public interface IUploadFileServices {
  
    public Resource load(String filename)throws MalformedURLException;//mostrar imagen

    public String copy(MultipartFile file) throws IOException;//toma el nombre de la imagen y la copia en el directorio y la renombre

    public boolean delete(String filename);


}
