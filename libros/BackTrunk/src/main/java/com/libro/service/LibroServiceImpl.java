package com.libro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libro.exception.ResourceNotFoundException;
import com.libro.model.Libro;
import com.libro.repository.LibroRepository;

//defining the business logic
@Service
public class LibroServiceImpl implements LibroService {

	private final Logger log =LoggerFactory.getLogger(LibroServiceImpl.class);

	@Autowired
	private LibroRepository libroRepository;

	//saving a specific record by using the method save()
	public Libro save(Libro libro) {
		if (libroRepository.existsById(libro.getId()))
			return null;
		return libroRepository.save(libro);
	}

   //getting all libros record by using the method findaAll() of CrudRepository
	public List<Libro> getAllLibros() {
		List<Libro> libros = new ArrayList<Libro>();
		libroRepository.findAll().forEach(libro -> libros.add(libro));
		return libros;
	}
	
	//getting a specific record by using the method findById() of CrudRepository
	public Libro getLibroById(int id) {
		return libroRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Libro not found with id: " + id));
	}

	//saving a specific record by using the method save() of CrudRepository
	public Libro saveOrUpdate(Libro libros) {
		libroRepository.save(libros);
		return libros;
	}

    //deleting a specific record by using the method deleteById() of CrudRepository
	public Libro delete(int id) {
		libroRepository.deleteById(id);
		return null;
	}


	//updating a record
	public Libro update(Libro libro) {
		if (libroRepository.existsById(libro.getId()))
			return libroRepository.save(libro);
		return null;
	}

	// Partiel updating a record
	public Optional<Libro> partialUpdate(Libro libro) {
		
		log.debug("Request to partially update Libro : {}", libro);

		return libroRepository.findById(libro.getId()).map(existingLibro -> {
			if (libro.getTitulo() != null) {
				existingLibro.setTitulo(libro.getTitulo());
			}
			if (libro.getAutor() != null) {
				existingLibro.setAutor(libro.getAutor());
			}
			if (libro.getPrecio() != null) {
				existingLibro.setPrecio(libro.getPrecio());
			}
			if (libro.getFechaLanzamiento() != null) {
				existingLibro.setFechaLanzamiento(libro.getFechaLanzamiento());
			}

			return existingLibro;
		}).map(libroRepository::save);
	}
	
	// Updater 0 Creater Libro
	public Libro UpdateOrCreate(Libro libro)throws ResourceNotFoundException {
		
		        if(libroRepository.existsById(libro.getId())){
		        	
		            Libro libroModif= new Libro();
		            
		            libroModif.setId(libro.getId());
		            libroModif.setTitulo(libro.getTitulo());
		            libroModif.setAutor(libro.getAutor());
		            libroModif.setPrecio(null);
		            libroModif.setFechaLanzamiento(libro.getFechaLanzamiento());
		            
		            log.debug("Request to partially update Libro : {}", libroModif);
		            
		            return libroRepository.save(libroModif);
		            
		        }
		        log.debug("Request to partially save Libro : {}", libro);
		        return libroRepository.save(libro);
		    }
	
	
	// Exists Libro id
	public boolean existsById(int id) {
		return libroRepository.existsById(id);
	}
}
