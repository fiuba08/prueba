import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LibroListComponent } from './components/libro-list/libro-list.component';
import { LibroFormComponent } from './components/libro-form/libro-form.component';
import { LibroDetailComponent } from './components/libro-detail/libro-detail.component';

const routes: Routes = [
  { path: '', redirectTo: '/libros', pathMatch: 'full' },
  { path: 'libros', component: LibroListComponent },
  { path: 'libros/add', component: LibroFormComponent },
  { path: 'libros/edit/:id', component: LibroFormComponent },
  { path: 'libros/detail/:id', component: LibroDetailComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
