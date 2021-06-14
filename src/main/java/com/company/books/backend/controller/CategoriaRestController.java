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

import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;
import com.company.books.backend.services.ICategoriaServices;

@RestController
@RequestMapping("/api")
public class CategoriaRestController {

	@Autowired
	private ICategoriaServices service;

	@GetMapping("/categorias")
	//ResponseEntity es para los codigos HTTP
	public ResponseEntity<CategoriaResponseRest>  getCategorias() {
	
		ResponseEntity<CategoriaResponseRest> categorias = service.buscarCategorias();
		return categorias;
	}
	
	@GetMapping("/categoria/{id}")
	public CategoriaResponseRest getCategoriaById(@PathVariable long id) {
		CategoriaResponseRest categoria = service.findCategoriaById(id);
		return categoria;
	}
	
	@PostMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> saveCategoriad(@RequestBody Categoria request) {
		
		ResponseEntity<CategoriaResponseRest> response = service.create(request);
		return response;
	}
	
	@PutMapping("/categoria/{id}")
	public ResponseEntity<CategoriaResponseRest>updateCategoria(@PathVariable long id, @RequestBody Categoria request) {
		
		ResponseEntity<CategoriaResponseRest> categoria = service.udpateCategoria(request, id);
		return categoria;
	}
	
	@DeleteMapping("/categoria/{id}")
	public ResponseEntity<CategoriaResponseRest>deleteCategoria(@PathVariable long id) {
		
		ResponseEntity<CategoriaResponseRest> categoria = service.deleteCategoria(id);
		return categoria;
	}
	
}
