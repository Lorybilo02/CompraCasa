import { Component } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-login-screen',
    standalone: true,
    imports: [
        FormsModule
    ],
    templateUrl: './login-screen.component.html',
    styleUrls: ['./login-screen.component.css']
})
export class LoginScreenComponent {
    passwordVisible: boolean = false;
    username: string = '';
    password: string = '';
    private apiUrl = 'http://localhost:8080'; // Base URL del backend

    constructor(private http: HttpClient, private router: Router) {}

    redirectTo(id: string) {
        this.router.navigate([id]);
    }

    togglePasswordVisibility() {
        this.passwordVisible = !this.passwordVisible;
        const passwordField = document.getElementById('password') as HTMLInputElement;
        const toggleButton = document.querySelector('.toggle-password') as HTMLElement;

        if (this.passwordVisible) {
            passwordField.type = 'text';
            toggleButton.textContent = '👁️ Nascondi';
        } else {
            passwordField.type = 'password';
            toggleButton.textContent = '👁️ Mostra';
        }
    }

    validateLoginForm(event: Event) {
        event.preventDefault(); // Evita il comportamento predefinito di submit del form

        // Prendo i valori dei campi username e password
        const username = (<HTMLInputElement>document.querySelector('input[name="username"]')).value.trim();
        const password = (<HTMLInputElement>document.querySelector('input[name="password"]')).value.trim();

        // Verifico che entrambi i campi siano compilati
        if (username === '' || password === '') {
            alert('Compilare username e password per poter accedere.');
            return;
        } else {
            // Se entrambi i campi sono compilati, procedo con il tentativo di login
            this.loginUser(username, password);
        }
    }

    loginUser(username: string, password: string) {
        // Chiamata GET al backend per verificare l'utente e la password
        this.http.get(`${this.apiUrl}/checkPassword`, { params: { username, password }})
            .subscribe(
                (response: any) => {

                    if (response && response.token) {
                        //memorizza il token nel localstorage
                        localStorage.setItem('authToken', response.token);

                        // Memorizza il nome utente nel localStorage
                        localStorage.setItem('username', username);
                        localStorage.setItem('isAdmin', response.isAdmin.toString());

                        alert('Bentornato!');
                        this.redirectTo(''); // Redirige l'utente alla home page o altra pagina

                    } else {
                        alert('Credenziali non valide.');
                    }
                },
                (error) => {
                    console.error('Errore durante il login:', error);
                    alert('Si è verificato un errore durante il login.');

                }
            );

    }
}
