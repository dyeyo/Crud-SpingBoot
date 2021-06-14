package com.company.books.backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.dao.ICategoira;
import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;
import java.util.*;

@Service
public class CategoriaServiceImpl implements ICategoriaServices {

	
	private static final Logger  log  = LoggerFactory.getLogger(CategoriaServiceImpl.class);
	
	@Autowired
	private ICategoira categoriaDao;

	@Override
	@Transactional(readOnly=true)
	//ResponseEntity es para los codigos HTTP
	public ResponseEntity<CategoriaResponseRest> buscarCategorias() { 
		log.info("Inicio el metodo de buscarCategorias()");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			List<Categoria> categorias = (List<Categoria>) categoriaDao.findAll();
			response.getCategoriasRespose().setCategoria(categorias);
			response.setMetadata("Respuesta OK", "200", "Respuesta existosa");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta FAIL", "500", "Respuesta FAIL");
			log.error("Error en buscarCategorias service", e.getMessage());
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
		
		
		
	}

	@Override
	@Transactional(readOnly=true)
	public  CategoriaResponseRest findCategoriaById(long id) {
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> categoria = new ArrayList<>();
		try {
			
			Optional<Categoria> categoriaId =  categoriaDao.findById(id);
			if(!categoriaId.isPresent()) {
				response.setMetadata("Categoria no found", "404", "Respuesta 404");
			}
			categoria.add(categoriaId.get());
			response.getCategoriasRespose().setCategoria(categoria);
			
		} catch (Exception e) {
			response.setMetadata("Respuesta FAIL", "500", "Respuesta FAIL");
			log.error("Error en buscarCategorias service", e.getMessage());
			return response;
		}
			
		return response;
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> create(Categoria categoria) {
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> categoriaList = new ArrayList<>();
		
		try {
			
			Categoria categoriaSave = categoriaDao.save(categoria);
			
			if(categoria != null) {
				categoriaList.add(categoriaSave);
				response.getCategoriasRespose().setCategoria(categoriaList);
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
			}else {
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			response.setMetadata("Respuesta FAIL", "500", "Respuesta FAIL");
			log.error("Error en crear categoria service", e.getMessage());
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> udpateCategoria(Categoria categoria, long id) {
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> categoriaList = new ArrayList<>();
		try {
			
			Optional<Categoria> categoriaBuscada =  categoriaDao.findById(id);
			
			if(categoriaBuscada.isPresent()) {
				
				categoriaBuscada.get().setNombre(categoria.getNombre());
				categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
				
				//actualiza
				Categoria categoriaActualizar = categoriaDao.save(categoriaBuscada.get());
				
				if( categoriaActualizar != null ) {
					categoriaList.add(categoriaActualizar);
					response.getCategoriasRespose().setCategoria(categoriaList);
					response.setMetadata("Categoria actualziada con exito", "200", "Respuesta 200");
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
				}else {
					response.setMetadata("Categoria no found", "404", "Respuesta 404");
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			}else {
				response.setMetadata("Categoria no found", "404", "Respuesta 404");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			response.setMetadata("Respuesta FAIL", "500", "Respuesta FAIL");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
		}
			
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> deleteCategoria(long id) {
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			
			categoriaDao.deleteById(id);
			response.setMetadata("Categoria eliminada con exito", "200", "Respuesta 200");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			response.setMetadata("Respuesta FAIL", "500", "Respuesta FAIL");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
