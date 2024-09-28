import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8080';  // Endpoint base del tuo backend
    private username: string = ''; // Nome dell'utente autenticato


    constructor(private http: HttpClient) {
    }

    getUsername(): string {
        return this.username;
    }

    // Metodo per verificare se l'utente è autenticato
    isAuthenticated(): Observable<boolean> {
        const token = localStorage.getItem('authToken');

        if (!token) {
            return of(false); // Se non c'è un token, l'utente non è autenticato
        }

        // Chiamata al backend per verificare la validità del token
        return this.http.post<boolean>(`${this.apiUrl}/verifyToken`, {}, {
            headers: {
                Authorization: `Basic ${token}`
            }
        }).pipe(
            catchError(() => of(false)) // In caso di errore, ritorna false
        );
    }

    // Metodo per effettuare il logout
    logout(): void {
        localStorage.removeItem('authToken'); // Rimuove il token dal localStorage
        localStorage.removeItem('username');
    }

    // Metodo per effettuare il login
    login(credentials: { username: string, password: string }): Observable<boolean> {
        return this.http.post<{ token: string }>(`${this.apiUrl}/login`, credentials).pipe(
            map(response => {
                if (response.token) {
                    localStorage.setItem('authToken', response.token); // Salva il token nel localStorage
                    return true;
                }
                return false;
            }),
            catchError(() => of(false)) // In caso di errore, ritorna false
        );
    }

    // Metodo per ottenere il ruolo dell'utente
    getUserRole(): string | null {
        return localStorage.getItem('role');
    }

    getAllUsers(): Observable<any> {
        return this.http.get(`${this.apiUrl}/findAll`);
    }

    deleteUser(username: string): Observable<any> {
        return this.http.delete(`${this.apiUrl}/deleteUser`, {body: username});
    }

    promoteToAdmin(username: string): Observable<any> {
        return this.http.put(`${this.apiUrl}/promoteToAdmin`, username, {
            headers: {'Content-Type': 'text/plain'}  // Assicurati che il tipo di contenuto sia testuale
        });
    }



}





