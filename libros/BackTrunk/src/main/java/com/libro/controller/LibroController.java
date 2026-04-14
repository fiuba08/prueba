package com.libro.controller;




import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libro.exception.BadRequestException;
import com.libro.exception.Mensaje;
import com.libro.exception.ResourceNotFoundException;
import com.libro.model.Libro;
import com.libro.service.LibroService;

// Controller
@RestController
@RequestMapping("/api/libro")
public class LibroController {
	
	private final Logger log = LoggerFactory.getLogger(LibroController.class);

	@Autowired
	private final LibroService libroService;

	public LibroController(LibroService libroService) {
		this.libroService = libroService;
	}
	
	/**
	 * {@code POST  /add} : Create a new libro.
	 *
	 * @param libro the libro to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new libro, or with status {@code 400 (Bad Request)} if the
	 *         libro has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	
	@PostMapping("/add")
	public ResponseEntity<Libro> createLibro(@Valid @RequestBody Libro libro)
			throws URISyntaxException, ResourceNotFoundException {
		
		log.debug(Mensaje.REST_REQUEST_TO_SAVE_LIBRO, libro);
		
		if (libro.getId() != 0 && libroService.existsById(libro.getId()))
			
			 throw new BadRequestException(Mensaje.EXISTE_LIBRO);

		Libro result = libroService.save(libro);
		
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	// Create o Update Libros
	@PostMapping("/createOrUpdate")
    public ResponseEntity<Libro> UpdateOrCreate (@Valid @RequestBody Libro libro) throws ResourceNotFoundException {
        Libro saveProducto = libroService.UpdateOrCreate(libro);
        return new ResponseEntity<>(saveProducto, HttpStatus.CREATED);
    }
	
	// creating a get mapping that retrieves all the libros detail from the database
	
	@GetMapping("/list")
	public ResponseEntity<List<Libro>> getAllBooks() {

		List<Libro> libros = libroService.getAllLibros();
 		return ResponseEntity.ok(libros);
	}

    //creating a get mapping that retrieves the detail of a specific libro
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Libro> getBooks(@PathVariable("id") int libroid) throws ResourceNotFoundException {
		
		if (!libroService.existsById(libroid))
			throw new ResourceNotFoundException(Mensaje.NO_EXISTE_LIBRO);
		
		Libro libro1 = libroService.getLibroById(libroid);
		
		return ResponseEntity.ok(libro1);
	}

    //creating a delete mapping that deletes a specified libro
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") int id) throws ResourceNotFoundException {
		
		if (!libroService.existsById(id))
			throw new ResourceNotFoundException(Mensaje.NO_EXISTE_LIBRO);
		
		libroService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * {@code PUT  /update/id} : Updates an existing libro.
	 *
	 * @param id    the id of the libro to save.
	 * @param libro the libro to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated libro, or with status {@code 400 (Bad Request)} if the
	 *         libro is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the libro couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Libro> updateLibro(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody Libro libro) throws URISyntaxException, ResourceNotFoundException {
		
		log.debug("REST request to update Libro : {}, {}", id, libro);
		
		if (libro.getId() == 0) {
			throw new BadRequestException(Mensaje.ID_NULL);
		}
		if (!Objects.equals(id, Long.valueOf(libro.getId()))) {
			throw new BadRequestException(Mensaje.INVALID_ID);
		}

		if (!libroService.existsById(id.intValue())) {
			throw new ResourceNotFoundException(Mensaje.ID_NOT_FOUND);
		}

		Libro result = libroService.update(libro);
		return ResponseEntity.ok(result);
	}

	/**
	 * {@code PATCH  /updateParc/:id} : Partial updates given fields of an existing
	 * libro, field will ignore if it is null
	 *
	 * @param id    the id of the libro to save.
	 * @param libro the libro to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated libro, or with status {@code 400 (Bad Request)} if the
	 *         libro is not valid, or with status {@code 404 (Not Found)} if the
	 *         libro is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the libro couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	
	@PatchMapping(value = "/updateParc/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<Libro> partialUpdateLibro(@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody Libro libro) throws URISyntaxException, ResourceNotFoundException {
		
		log.debug("REST request to partial update Libro partially : {}, {}", id, libro);
		
		
		if (libro.getId() < 1) {
			throw new BadRequestException(Mensaje.ID_NULL);
		}
		if (!Objects.equals(id, Long.valueOf(libro.getId()))) {
			throw new BadRequestException(Mensaje.INVALID_ID);
		}

		if (!libroService.existsById(id.intValue())) {
			throw new ResourceNotFoundException(Mensaje.ID_NOT_FOUND);
		}

		Optional<Libro> result = libroService.partialUpdate(libro);

		return result.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResourceNotFoundException(Mensaje.ID_NOT_FOUND));
	}
	
}
