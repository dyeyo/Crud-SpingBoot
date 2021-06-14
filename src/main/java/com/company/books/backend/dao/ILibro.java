package com.company.books.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.books.backend.model.Libro;

public interface ILibro extends CrudRepository<Libro, Long>{

}
