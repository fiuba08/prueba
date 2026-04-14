import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LibroListComponent } from './components/libro-list/libro-list.component';
import { LibroFormComponent } from './components/libro-form/libro-form.component';
import { LibroDetailComponent } from './components/libro-detail/libro-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    LibroListComponent,
    LibroFormComponent,
    LibroDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
