import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Libro } from '../../models/libro';
import { LibroService } from '../../services/libro.service';

@Component({
  selector: 'app-libro-detail',
  templateUrl: './libro-detail.component.html',
  styleUrls: ['./libro-detail.component.css']
})
export class LibroDetailComponent implements OnInit {

  libro: Libro = null;
  errorMessage = '';

  constructor(
    private libroService: LibroService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadLibro(+id);
    }
  }

  loadLibro(id: number) {
    this.libroService.getLibroById(id).subscribe(
      data => {
        this.libro = data;
      },
      error => {
        this.errorMessage = 'Error loading libro details.';
        console.error('Error:', error);
      }
    );
  }

  editLibro() {
    this.router.navigate(['/libros/edit', this.libro.id]);
  }

  goBack() {
    this.router.navigate(['/libros']);
  }
}
