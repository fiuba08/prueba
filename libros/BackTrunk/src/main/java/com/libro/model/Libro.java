package com.libro.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sun.istack.NotNull;

/**
 * A Libro.
 */
@Entity
@Table(name = "libros", indexes = {
	@Index(name = "idx_libros_titulo", columnList = "titulo"),
	@Index(name = "idx_libros_autor", columnList = "autor"),
	@Index(name = "idx_libros_precio", columnList = "precio"),
	@Index(name = "idx_libros_fecha_lanzamiento", columnList = "fecha_lanzamiento")
})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Libro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotNull
	@Size(min = 3, max = 30)
	@Column(name = "titulo", length = 30, nullable = false)
	private String titulo;

	@NotNull
	@Size(min = 10, max = 50)
	@Column(name = "autor", length = 50, nullable = false)
	private String autor;

	@NotNull
	@DecimalMin(value = "10")
	@DecimalMax(value = "9999999")
	@Column(name = "precio", nullable = false)
	private Double precio;
	@Column(name = "fecha_lanzamiento")
	private LocalDate fechaLanzamiento;

	public Libro() {

	}

	public Libro(int id1, String titulo1, String autor1, Double precio1, LocalDate fechaLanzamiento1) {
		this.id = id1;
		this.titulo = titulo1;
		this.autor = autor1;
		this.precio = precio1;
		this.fechaLanzamiento = fechaLanzamiento1;
	}

	public int getId() {
		return this.id;
	}

	public Libro id(int id) {
		this.setId(id);
		return this;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public Libro titulo(String titulo) {
		this.setTitulo(titulo);
		return this;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return this.autor;
	}

	public Libro autor(String autor) {
		this.setAutor(autor);
		return this;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Double getPrecio() {
		return this.precio;
	}

	public Libro precio(Double precio) {
		this.setPrecio(precio);
		return this;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public LocalDate getFechaLanzamiento() {
		return this.fechaLanzamiento;
	}

	public Libro fechaLanzamiento(LocalDate fechaLanzamiento) {
		this.setFechaLanzamiento(fechaLanzamiento);
		return this;
	}

	public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Libro{" + "id=" + getId() + ", titulo='" + getTitulo() + "'" + ", autor='" + getAutor() + "'"
				+ ", precio=" + getPrecio() + ", fechaLanzamiento='" + getFechaLanzamiento() + "'" + "}";
	}

}
