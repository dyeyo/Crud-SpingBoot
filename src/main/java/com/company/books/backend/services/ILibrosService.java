package com.company.books.backend.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;

@Service
public interface ILibrosService {
	
	public LibroResponseRest getAll();
	
	public LibroResponseRest getLibroById(long id);
	
	public ResponseEntity<LibroResponseRest> saveLibro(Libro libro);
	
	public ResponseEntity<LibroResponseRest> updateLibro (long id,Libro libro);
	
	public ResponseEntity<LibroResponseRest> deleteLibro(long id);
	
}
