import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Libro } from '../../models/libro';
import { LibroService } from '../../services/libro.service';

@Component({
  selector: 'app-libro-list',
  templateUrl: './libro-list.component.html',
  styleUrls: ['./libro-list.component.css']
})
export class LibroListComponent implements OnInit {

  libros: Libro[] = [];
  errorMessage = '';
  successMessage = '';

  constructor(
    private libroService: LibroService,
    private router: Router
  ) { }

  ngOnInit() {
    this.loadLibros();
  }

  loadLibros() {
    this.libroService.getLibros().subscribe(
      data => {
        this.libros = data;
      },
      error => {
        this.errorMessage = 'Error loading libros. Make sure the backend is running on port 8082.';
        console.error('Error:', error);
      }
    );
  }

  deleteLibro(id: number) {
    if (confirm('Are you sure you want to delete this libro?')) {
      this.libroService.deleteLibro(id).subscribe(
        () => {
          this.successMessage = 'Libro deleted successfully.';
          this.loadLibros();
          setTimeout(() => this.successMessage = '', 3000);
        },
        error => {
          this.errorMessage = 'Error deleting libro.';
          console.error('Error:', error);
        }
      );
    }
  }

  editLibro(id: number) {
    this.router.navigate(['/libros/edit', id]);
  }

  viewLibro(id: number) {
    this.router.navigate(['/libros/detail', id]);
  }

  addLibro() {
    this.router.navigate(['/libros/add']);
  }

  initDatabase() {
    this.libroService.initDatabase().subscribe(
      (response) => {
        this.successMessage = response;
        this.loadLibros();
        setTimeout(() => this.successMessage = '', 5000);
      },
      error => {
        this.errorMessage = 'Error initializing database.';
        console.error('Error:', error);
      }
    );
  }
}
