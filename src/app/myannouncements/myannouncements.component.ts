import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {CurrencyPipe} from "@angular/common";
import { CommonModule } from '@angular/common';
import {catchError} from "rxjs/operators";

interface User {
    username: string;
    // Altri campi dell'utente
}

interface Property {
    id: number;
    owner: User;
    name: string;
    description: string;
    location: string;
    condition: string;
    energeticClass?: string;
    heatingSystem?: string;
    floor: number;
    type: string;
    sqm: number;
    price: number;
     sellMode: string;
     address: string;
     civicNumber: string;
}
@Component({
  selector: 'app-myannouncements',
  standalone: true,
    imports: [
        CurrencyPipe,
        CommonModule
    ],
  templateUrl: './myannouncements.component.html',
  styleUrl: './myannouncements.component.css'
})
export class MyannouncementsComponent implements OnInit {

    properties: Property[] = [];
    username: string = '';


    constructor(private http: HttpClient) { }
    getUsernameFromLocalStorage(): string {
        return localStorage.getItem('username') || '';
    }
    ngOnInit(): void {
        this.username = this.getUsernameFromLocalStorage();
        this.getPropertiesByOwner(this.username).subscribe((data: Property[]) => {
            this.properties = data;
        });
    }


    getPropertiesByOwner(username: string): Observable<Property[]> {
        return this.http.get<Property[]>('http://localhost:8080/getPropertyByOwner', { params: { username } })
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    console.error('Errore durante la chiamata HTTP', error);
                    return throwError('Errore nella chiamata HTTP; per favore riprova più tardi.');
                })
            );
    }
    deleteProperty(property: Property): void {
        const confirmDelete = confirm(`Sei sicuro di voler eliminare l'annuncio in"${property.address}"?`);

        if (confirmDelete) {
            this.http.delete('http://localhost:8080/deleteProperty', {
                params: { id: property.id.toString() } // Passa l'ID come parametro di query
            })
                .subscribe({
                    next: () => {
                        console.log('Proprietà eliminata con successo');
                        // Rimuove la proprietà dalla lista locale
                        this.properties = this.properties.filter(p => p.id !== property.id);
                        alert('L\'annuncio è stato eliminato con successo.');
                    },
                    error: (error: HttpErrorResponse) => {
                        console.error('Errore durante l\'eliminazione della proprietà', error);
                        alert('Errore durante l\'eliminazione della proprietà; per favore riprova più tardi.');
                    }
                });
        }
    }



}
