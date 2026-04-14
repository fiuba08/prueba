package com.libro.controller;




import java.net.URISyntaxException;
import java.time.LocalDate;
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

	private final Mensaje mensaje = new Mensaje();

	public LibroController(LibroService libroService) {
		this.libroService = libroService;
	}
	
	/**
	 *  Init Database H2
	 */
	
	@RequestMapping("/init")
	public String index() {
		
		// Loader Libros
		for (int i = 0; i < 15; i++) {
			Libro libro1 = new Libro();
			libro1.setTitulo("Titulo");
			libro1.setAutor("AutorPrueba");
			libro1.setPrecio(13.1416 * 88);
			libro1.setFechaLanzamiento(LocalDate.now());
			Libro result = libroService.saveOrUpdate(libro1);
			log.debug("Libro1 Titulo : " + result.getTitulo());
		}
		
		return "Data Base H2 in directory./target/BaseDatos/ init OK ";
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
			throws URISyntaxException, BadRequestException {
		
		log.debug(mensaje.REST_REQUEST_TO_SAVE_LIBRO, libro);
		
		if (libro.getId() != 0) {
			throw new BadRequestException("A new libro cannot already have an ID");
		}

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
	public ResponseEntity<Libro> getBooks(@PathVariable("id") int libroid) throws BadRequestException, ResourceNotFoundException {
		
		if (libroid < 1) {
			throw new BadRequestException("ID must be greater than 0");
		}

		if (!libroService.existsById(libroid)) {
			throw new ResourceNotFoundException(mensaje.NO_EXISTE_LIBRO);
		}

		Libro libro1 = libroService.getLibroById(libroid);
		
		return ResponseEntity.ok(libro1);
	}

    //creating a delete mapping that deletes a specified libro
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") int id) throws BadRequestException, ResourceNotFoundException {
		
		if (id < 1) {
			throw new BadRequestException("ID must be greater than 0");
		}

		if (!libroService.existsById(id)) {
			throw new ResourceNotFoundException(mensaje.NO_EXISTE_LIBRO);
		}

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
			@Valid @RequestBody Libro libro) throws URISyntaxException, BadRequestException, ResourceNotFoundException {
		
		log.debug("REST request to update Libro : {}, {}", id, libro);
		
		if (libro.getId() == 0) {
			throw new BadRequestException(mensaje.ID_NULL);
		}
		if (!Objects.equals(id, (long) libro.getId())) {
			throw new BadRequestException(mensaje.INVALID_ID);
		}

		if (!libroService.existsById(id.intValue())) {
			throw new ResourceNotFoundException(mensaje.ID_NOT_FOUND);
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
			@NotNull @RequestBody Libro libro) throws URISyntaxException, BadRequestException, ResourceNotFoundException {
		
		log.debug("REST request to partial update Libro partially : {}, {}", id, libro);
		
		
		if (libro.getId() < 1) {
			throw new BadRequestException(mensaje.ID_NULL);
		}
		if (!Objects.equals(id, (long) libro.getId())) {
			throw new BadRequestException(mensaje.INVALID_ID);
		}

		if (!libroService.existsById(id.intValue())) {
			throw new ResourceNotFoundException(mensaje.ID_NOT_FOUND);
		}

		Optional<Libro> result = libroService.partialUpdate(libro);

		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		}
		throw new ResourceNotFoundException(mensaje.ID_NOT_FOUND);
	}
	
}
