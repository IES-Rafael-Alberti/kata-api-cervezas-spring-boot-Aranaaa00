package com.example.cervezas.service;

import com.example.cervezas.exception.ArchivoInvalidoException;
import com.example.cervezas.exception.ErrorAlmacenamientoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {

    private static final long TAMANO_MAXIMO_BYTES = 5 * 1024 * 1024; // 5MB
    private static final String PREFIJO_RUTA_IMAGEN = "/images/";
    private static final String TIPO_CONTENIDO_IMAGEN = "image/";
    private static final String EXTENSION_POR_DEFECTO = ".jpg";

    private final Path directorioAlmacenamiento;

    public String guardarArchivo(MultipartFile archivo) {
        validarArchivo(archivo);
        
        String nombreOriginal = archivo.getOriginalFilename();
        String extension = obtenerExtension(nombreOriginal);
        String nombreUnico = generarNombreUnico(extension);
        
        try {
            Path rutaDestino = directorioAlmacenamiento.resolve(nombreUnico);
            Files.copy(archivo.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);
            String rutaCompleta = PREFIJO_RUTA_IMAGEN + nombreUnico;
            log.info("Archivo guardado exitosamente: {}", rutaCompleta);
            return rutaCompleta;
        } catch (IOException e) {
            log.error("Error al guardar archivo: {}", nombreUnico, e);
            throw new ErrorAlmacenamientoException("Error al guardar el archivo", e);
        }
    }

    public void eliminarArchivo(String rutaArchivo) {
        if (esRutaArchivoInvalida(rutaArchivo)) {
            log.warn("Intento de eliminar ruta inválida: {}", rutaArchivo);
            return;
        }
        
        try {
            String nombreArchivo = extraerNombreArchivo(rutaArchivo);
            Path ruta = directorioAlmacenamiento.resolve(nombreArchivo);
            boolean eliminado = Files.deleteIfExists(ruta);
            
            if (eliminado) {
                log.info("Archivo eliminado exitosamente: {}", rutaArchivo);
            } else {
                log.warn("Archivo no encontrado para eliminar: {}", rutaArchivo);
            }
        } catch (IOException e) {
            log.error("Error al eliminar archivo: {}", rutaArchivo, e);
        }
    }

    private boolean esRutaArchivoInvalida(String rutaArchivo) {
        return rutaArchivo == null || !rutaArchivo.startsWith(PREFIJO_RUTA_IMAGEN);
    }

    private String extraerNombreArchivo(String rutaArchivo) {
        return rutaArchivo.substring(PREFIJO_RUTA_IMAGEN.length());
    }

    private void validarArchivo(MultipartFile archivo) {
        validarArchivoNoVacio(archivo);
        validarTipoImagen(archivo);
        validarTamanoArchivo(archivo);
    }

    private void validarArchivoNoVacio(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new ArchivoInvalidoException("El archivo no puede estar vacío");
        }
    }

    private void validarTipoImagen(MultipartFile archivo) {
        String tipoContenido = archivo.getContentType();
        if (tipoContenido == null || !tipoContenido.startsWith(TIPO_CONTENIDO_IMAGEN)) {
            throw new ArchivoInvalidoException("El archivo debe ser una imagen");
        }
    }

    private void validarTamanoArchivo(MultipartFile archivo) {
        if (archivo.getSize() > TAMANO_MAXIMO_BYTES) {
            throw new ArchivoInvalidoException("El archivo no puede superar los 5MB");
        }
    }

    private String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null || !nombreArchivo.contains(".")) {
            return EXTENSION_POR_DEFECTO;
        }
        return nombreArchivo.substring(nombreArchivo.lastIndexOf("."));
    }

    private String generarNombreUnico(String extension) {
        return UUID.randomUUID().toString() + extension;
    }
}
