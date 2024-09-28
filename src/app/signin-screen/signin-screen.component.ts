import { Component } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
    selector: 'app-signin-screen',
    templateUrl: './signin-screen.component.html',
    styleUrls: ['./signin-screen.component.css']
})
export class SigninScreenComponent {

    private apiUrl = 'http://localhost:8080/signin/addUser';  // L'endpoint REST del tuo server

    constructor(private http: HttpClient, private router: Router) {}
    redirectTo(id: string) {
        this.router.navigate([id]);
    }

    handleRegistration(event: Event) {
        event.preventDefault();

        const username = (<HTMLInputElement>document.querySelector('input[name="username"]')).value.trim();
        const firstname = (<HTMLInputElement>document.querySelector('input[name="name"]')).value.trim();
        const secondname = (<HTMLInputElement>document.querySelector('input[name="surname"]')).value.trim();
        const password = (<HTMLInputElement>document.querySelector('input[name="password"]')).value.trim();

        if (!username || !firstname || !secondname || !password) {
            alert('Tutti i campi devono essere compilati.');
            return;
        }

        if (password.length < 8 || !this.containsSpecialCharacter(password)) {
            alert('La password deve contenere almeno 8 caratteri e almeno un carattere speciale.');
            return;
        }


        const userData = {
            username: username,
            password: password,
            firstname: firstname,
            secondname: secondname
        };

        console.log("Dati inviati al server:", userData); // Debug: log dei dati inviati

        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

        this.http.post(this.apiUrl, userData, { headers })
            .subscribe(
                (response:any) => {
                    if (response && response.token) {
                        alert('Registrazione avvenuta con successo. Benvenuto!');

                        localStorage.setItem('authToken', response.token);

                        localStorage.setItem('username', username);
                        localStorage.setItem('isAdmin', response.isAdmin.toString());
                        this.redirectTo(''); // Redirigi alla home
                    }
                },

                (error: HttpErrorResponse) => {
                    console.error('Errore durante la registrazione', error);
                    if (error.status === 409) {
                        alert('Il nome utente è già in uso. Per favore scegli un altro.');
                    } else if (error.status === 500) {
                        alert('Errore interno del server. Si prega di riprovare più tardi.');
                    } else {
                        alert('Registrazione fallita, riprova più tardi.');
                    }
                }
            );
    }


    private containsSpecialCharacter(str: string): boolean {
        return /[!@#$%^&*(),.?":{}|<>]/.test(str);
    }

    togglePasswordVisibility() {
        const passwordField = <HTMLInputElement>document.getElementById('password');
        const toggleButton = <HTMLElement>document.querySelector('.toggle-password');

        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            toggleButton.textContent = '👁️ Nascondi';
        } else {
            passwordField.type = 'password';
            toggleButton.textContent = '👁️ Mostra';
        }
    }
}
