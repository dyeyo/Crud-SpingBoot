package com.company.books.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.dao.ILibro;
import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.Libro;
import com.company.books.backend.response.CategoriaResponseRest;
import com.company.books.backend.response.LibroResponseRest;
import java.util.*;

@Service
public class LibroServicesImpl implements ILibrosService {

	
	@Autowired
	private ILibro libroDAO;

	@Override
	@Transactional(readOnly=true)
	public LibroResponseRest getAll() {
		
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			
			List<Libro> listaLibros = (List<Libro>) libroDAO.findAll();
			response.getLibrosRespose().setLibro(listaLibros);
			response.setMetadata("Respuesta OK", "200", "Respuesta existosa");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta FAIL", "500", "Respuesta FAIL");
		}
		
		return response;
	}

	@Override
	@Transactional(readOnly=true)
	public LibroResponseRest getLibroById(long id) {
		
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> libro = new ArrayList<Libro>();
		
		try {
			Optional<Libro> libroBucado = libroDAO.findById(id);
			
			if(libroBucado.isPresent()) {
				libro.add(libroBucado.get());
				response.getLibrosRespose().setLibro(libro);
				response.setMetadata("Respuesta OK", "200", "Respuesta existosa");
			}else {
				response.setMetadata("Categoria no found", "404", "Respuesta 404");
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta FAIL", "500", "Respuesta FAIL");
		}
		return response;
	}


	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> saveLibro(Libro libro) {
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> libroList= new ArrayList<Libro>();
		
		try {
			Libro libroSave= libroDAO.save(libro);
		
			if(libro != null) {
				libroList.add(libroSave);
				response.getLibrosRespose().setLibro(libroList);
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			response.setMetadata("Respuesta FAIL", "500", "Respuesta FAIL");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
		}
	
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> updateLibro(long id, Libro libro) {
		
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> libroList= new ArrayList<Libro>();
		
		try {
			Optional<Libro> libroBuscado= libroDAO.findById(id);
			
			if(libroBuscado.isPresent()) {
				//obtener cuerpo
				libroBuscado.get().setNombre(libro.getNombre());
				libroBuscado.get().setAutor(libro.getAutor());
				libroBuscado.get().setResumen(libro.getResumen());
				libroBuscado.get().setCategoria(libro.getCategoria());
				
				//actualiza
				Libro libroActualizar = libroDAO.save(libroBuscado.get());
				if( libroActualizar != null ) {
					libroList.add(libroActualizar);
					response.getLibrosRespose().setLibro(libroList);
					response.setMetadata("Categoria actualziada con exito", "200", "Respuesta 200");
					return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
				}else {
					response.setMetadata("Categoria no found", "404", "Respuesta 404");
					return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			}else {
				response.setMetadata("Categoria no found", "404", "Respuesta 404");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			response.setMetadata("Categoria no found", "500", "Respuesta 404");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> deleteLibro(long id) {
		
		LibroResponseRest response = new LibroResponseRest();
		try {
			Optional<Libro> libroEliminar= libroDAO.findById(id);
			if(libroEliminar.isPresent()) {
				libroDAO.deleteById(id);
				response.setMetadata("Libro Eliminado", "200", "Respuesta OK");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
			}else {
				response.setMetadata("Libro no found", "404", "Respuesta 404");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
		}
		
	}
}
