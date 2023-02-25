package com.datajpa.app.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author LuisMD
 */

@Service
public class UploadFileServicesImpl implements IUploadFileServices{


    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static String UPLOADS_FOLDER = "uploads";

    @Override
    public Resource load(String filename)  throws MalformedURLException{

        Path pathFoto = getPathFoto(filename);
        log.info("pathFoto: " + pathFoto);
        log.info("Fielename: " + filename);
        Resource r = null;
        r = new UrlResource(pathFoto.toUri());
        if (!r.exists() && !r.isReadable()) {
            throw new RuntimeException("Error: no se puede cargar la imagen " + pathFoto.toString());
        }
        return r;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {

        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path rootPath = getPathFoto(uniqueFileName);
        log.info("rootPath: " + rootPath);

            Files.copy(file.getInputStream(), rootPath);

            
            System.out.println("---------------->   " + file.getOriginalFilename());
        return uniqueFileName;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPathFoto(filename);//PROCEDEMOS A OBTENER LA RUTA ABSOLUTA DE LA IMAGEN PARA PODER ELIMINARLA
            File archivo = rootPath.toFile();

            if (archivo.exists() && archivo.canRead()) {
                if (archivo.delete()) {
                   return true;
                }
            }
        return false;
    }

    Path getPathFoto(String filename) {

        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }


}
