import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ErrorHandler, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MaterialModule } from './material.module';
import { AppComponent, ShipEditDialogComponent, ShipFormComponent, ShipListComponent } from './components';
import { ErrorHandlerService, HttpService, NotificationService } from './services';
import { AppStore } from './store';

@NgModule({
  declarations: [
    AppComponent,
    ShipFormComponent,
    ShipListComponent,
    ShipEditDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CommonModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  providers: [
    AppStore,
    HttpService,
    NotificationService,
    { provide: ErrorHandler, useClass: ErrorHandlerService }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
