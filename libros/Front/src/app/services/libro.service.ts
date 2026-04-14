import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Libro } from '../models/libro';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LibroService {

  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getLibros(): Observable<Libro[]> {
    return this.http.get<Libro[]>(`${this.baseUrl}/list`);
  }

  getLibroById(id: number): Observable<Libro> {
    return this.http.get<Libro>(`${this.baseUrl}/findById/${id}`);
  }

  createLibro(libro: Libro): Observable<Libro> {
    return this.http.post<Libro>(`${this.baseUrl}/add`, libro);
  }

  updateLibro(id: number, libro: Libro): Observable<Libro> {
    return this.http.put<Libro>(`${this.baseUrl}/update/${id}`, libro);
  }

  deleteLibro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }

  initDatabase(): Observable<string> {
    return this.http.get(`${this.baseUrl}/init`, { responseType: 'text' });
  }
}
