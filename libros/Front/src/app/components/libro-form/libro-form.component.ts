import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Libro } from '../../models/libro';
import { LibroService } from '../../services/libro.service';

@Component({
  selector: 'app-libro-form',
  templateUrl: './libro-form.component.html',
  styleUrls: ['./libro-form.component.css']
})
export class LibroFormComponent implements OnInit {

  libro: Libro = {
    id: 0,
    titulo: '',
    autor: '',
    precio: 0,
    fechaLanzamiento: ''
  };

  isEditMode = false;
  errorMessage = '';
  pageTitle = 'Add Libro';

  constructor(
    private libroService: LibroService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.pageTitle = 'Edit Libro';
      this.loadLibro(+id);
    }
  }

  loadLibro(id: number) {
    this.libroService.getLibroById(id).subscribe(
      data => {
        this.libro = data;
      },
      error => {
        this.errorMessage = 'Error loading libro.';
        console.error('Error:', error);
      }
    );
  }

  onSubmit() {
    this.errorMessage = '';

    if (!this.libro.titulo || this.libro.titulo.length < 3) {
      this.errorMessage = 'Titulo must be at least 3 characters.';
      return;
    }
    if (!this.libro.autor || this.libro.autor.length < 10) {
      this.errorMessage = 'Autor must be at least 10 characters.';
      return;
    }
    if (!this.libro.precio || this.libro.precio < 10) {
      this.errorMessage = 'Precio must be at least 10.';
      return;
    }

    if (this.isEditMode) {
      this.libroService.updateLibro(this.libro.id, this.libro).subscribe(
        () => {
          this.router.navigate(['/libros']);
        },
        error => {
          const msg = (error.error && error.error.message) ? error.error.message : error.message;
          this.errorMessage = 'Error updating libro: ' + msg;
          console.error('Error:', error);
        }
      );
    } else {
      this.libroService.createLibro(this.libro).subscribe(
        () => {
          this.router.navigate(['/libros']);
        },
        error => {
          const msg = (error.error && error.error.message) ? error.error.message : error.message;
          this.errorMessage = 'Error creating libro: ' + msg;
          console.error('Error:', error);
        }
      );
    }
  }

  cancel() {
    this.router.navigate(['/libros']);
  }
}
