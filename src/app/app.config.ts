import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import {HttpClient, HttpClientModule, HttpHandler} from '@angular/common/http';
import { routes } from './app.routes';
import {AuthService} from "./auth.service";
import {provideHttpClient} from "@angular/common/http";

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), HttpClientModule,provideHttpClient(), AuthService]
};
