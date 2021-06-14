package com.company.books.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;
import com.company.books.backend.services.ILibrosService;

@RestController
@RequestMapping("/api")
public class LibrosRestController {

	@Autowired
	private ILibrosService service;
	
	@GetMapping("/libros")
	public LibroResponseRest getLibros() {
		
		 LibroResponseRest libros = service.getAll();
		 return libros;
	}
	
	@GetMapping("/libro/{id}")
	public LibroResponseRest getLibroId(@PathVariable long id) {
		LibroResponseRest libro = service.getLibroById(id);
		return libro;
	}
	
	@PostMapping("/libros")
	public ResponseEntity<LibroResponseRest> saveLibro (@RequestBody Libro request) {
		
		ResponseEntity<LibroResponseRest> libro = service.saveLibro(request);
		return libro;
		
	}
	
	@PutMapping("libro/{id}")
	public ResponseEntity<LibroResponseRest> update(@PathVariable long id, @RequestBody Libro request){
		
		ResponseEntity<LibroResponseRest> libro = service.updateLibro(id,request);
		return libro;

	}
	
	@DeleteMapping("libro/{id}")
	public ResponseEntity<LibroResponseRest> delete(@PathVariable long id){
		
		ResponseEntity<LibroResponseRest> libro = service.deleteLibro(id);
		return libro;

	}
	
}
