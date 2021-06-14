package com.company.books.backend.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;

@Service
public interface ICategoriaServices {

	public ResponseEntity<CategoriaResponseRest> buscarCategorias();
	
	public CategoriaResponseRest findCategoriaById(long id);
	
	public ResponseEntity<CategoriaResponseRest> create(Categoria categria); 
	
	public ResponseEntity<CategoriaResponseRest> udpateCategoria(Categoria categria, long id); 
	
	public ResponseEntity<CategoriaResponseRest> deleteCategoria(long id); 
	
}
