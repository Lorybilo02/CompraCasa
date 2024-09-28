import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth.service";
import { CommonModule } from '@angular/common';
import { NgFor } from '@angular/common';
import {Router} from "@angular/router";



@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit {
    users: any[] = [];

    constructor(private authService: AuthService,private router: Router) { }

    ngOnInit(): void {
        this.fetchAllUsers();
    }
    redirectTo(id: string) {
        this.router.navigate([id]);
    }

    fetchAllUsers(): void {
        this.authService.getAllUsers().subscribe(
            (response) => {
                this.users = response;
            },
            (error) => {
                console.error('Errore nel recupero degli utenti:', error);
            }
        );
    }

    deleteUser(username: string): void {
        const confirmed = confirm(`Sei sicuro di voler eliminare l'utente ${username}?`);
        if (confirmed) {
            this.authService.deleteUser(username).subscribe(
                (response) => {
                    alert('Utente eliminato con successo!');
                    this.fetchAllUsers(); // Ricarica la lista degli utenti dopo l'eliminazione
                },
                (error) => {
                    console.error('Errore nell\'eliminazione dell\'utente:', error);
                }
            );
        } else {
            console.log('Eliminazione annullata dall\'utente.');
        }
    }

    promoteToAdmin(username: string): void {
        const confirmed = confirm(`Sei sicuro di voler promuovere l'utente ${username} ad amministratore?`);
        if (confirmed) {
            this.authService.promoteToAdmin(username).subscribe(
                (response) => {
                    alert('Utente promosso ad amministratore con successo!');
                    this.fetchAllUsers(); // Ricarica la lista degli utenti dopo la promozione
                },
                (error) => {
                    console.error('Errore nella promozione dell\'utente:', error);
                }
            );
        } else {
            console.log('Promozione annullata dall\'utente.');
        }
    }
}
