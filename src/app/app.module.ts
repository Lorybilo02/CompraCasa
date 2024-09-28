import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MapSelectorComponent } from './map-selector/map-selector.component'; // Importa il componente
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { routes } from './app.routes';
import { AppComponent } from './app.component';
import { AuthService } from './auth.service';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import {SigninScreenComponent} from "./signin-screen/signin-screen.component";
import {CommonModule} from "@angular/common";

@NgModule({
    declarations: [
        SigninScreenComponent,

    ],

    imports: [
        BrowserModule,
        HttpClientModule,
        ReactiveFormsModule,
        FormsModule,
        CommonModule,
        RouterModule.forRoot(routes),

    ],
    providers: [
        AuthService
    ],
    exports: [

    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
