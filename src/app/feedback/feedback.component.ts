import {Component, ElementRef} from '@angular/core';
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../auth.service";
import {FormsModule} from "@angular/forms";
import {ImageService} from "../image.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";


class Property {
    constructor(
        public id: number,
        public type: string,
        public price: number,
        public sqm: number,
        public location: string,
        public imageLink: string
        // aggiungi altri campi se necessario
    ) {}
}

class Feedback {
    constructor(
        public username: string,
        public propertyId: number,
        public content: string,
        public stars: number,
    ) {}
}
@Component({
  selector: 'app-feedback',
  standalone: true,
    imports: [
        NgForOf,
        FormsModule,
        NgClass,
        NgIf
    ],
  templateUrl: './feedback.component.html',
  styleUrl: './feedback.component.css'
})
export class FeedbackComponent {
    imageUrls: SafeUrl[] = [];
    protected readonly console = console;
    id: number = 0;
    properties: Property[] = [];
    propertyType: string = '';
    town: string = '';
    priceMin: number = 0;
    priceMax: number = 0;
    sqmMin: number = 0;
    sqmMax: number = 0;
    public apiUrl = 'http://localhost:8080';
    starValue: number = 0;
    isAuthenticated = false;
    constructor(private imageService: ImageService, private sanitizer: DomSanitizer, private http: HttpClient, private authService: AuthService) {}
    searchProperties(location : string , priceMin: number , priceMax : number, sqmMin : number, sqmMax : number ) {

        let params = new HttpParams()
            .set('type', this.propertyType)
            .set('location', location)
            .set('priceMin', priceMin)
            .set('priceMax', priceMax)
            .set('minSqm', sqmMin)
            .set('maxSqm', sqmMax)

        this.http.get<Property[]>(`${this.apiUrl}/getPropertyByResearch`, {
            params,
            headers: { 'Accept': 'application/json' }
        })
            .subscribe(
                (data: Property[] | null) => {
                    if (data) {
                        this.properties = data;
                    } else {
                        this.properties = [];
                    }
                    console.log(this.properties.length);
                    this.loadImages();
                },
                (error) => {
                    console.error('Errore durante il recupero delle proprietà:', error);
                    this.properties = [];
                }
            );


    }
    onSelectChange(event: any) {
        if (event.target.value==="appartaments"){
            this.propertyType = "Appartamenti"
        }
        else if (event.target.value==="villas"){
            this.propertyType = "Ville"
        }
        else if (event.target.value==="buildings"){
            this.propertyType = "Palazzi"
        }
        else if (event.target.value==="offices"){
            this.propertyType = "Uffici"
        }
        else if (event.target.value==="shops"){
            this.propertyType = "Negozi"
        }
        else if (event.target.value==="depots"){
            this.propertyType = "Depositi"
        }
        else if (event.target.value==="garages"){
            this.propertyType = "Garages"
        }
        console.log(this.propertyType)
    }
    sendFeedback() {
        const content = (document.getElementById('description') as HTMLTextAreaElement).value;
        const username = localStorage.getItem('username');

        if (username === null) {
            alert("Devi effettuare il login prima di effettuare una valutazione!");
        } else {
            this.authService.isAuthenticated().subscribe(isAuth => {
                this.isAuthenticated = isAuth;
                if (this.isAuthenticated) {
                    console.log(this.starValue)
            const feedback = new Feedback(username, this.id, content, this.starValue);
            this.http.put<boolean>(`${this.apiUrl}/saveFeedback`, feedback).subscribe({
                next: (response) => {
                    if (response) {
                        alert("Feedback inviato con successo!");
                    } else {
                        alert("Errore durante l'invio del feedback.");
                    }
                },
                error: (err) => {
                    console.error('Errore durante l\'invio del feedback:', err);
                    alert("Errore durante l'invio del feedback.");
                }
            });
        }
            else {
                    alert("Devi effettuare il login prima di effettuare una valutazione!");
                }
            })
        }
    }
    selectedProperty: any = null;

    selectProperty(property: any) {
        this.selectedProperty = property;
        this.id = property.id;
    }

    isSelected(property: any): boolean {
        return this.selectedProperty === property;
    }

    loadImages(): void {
        this.properties.forEach((property, index) => {
            if (property.imageLink) {  // Verifica che imageUrl sia definito e non vuoto
                this.imageService.getImage(property.imageLink).subscribe(
                    (blob: Blob) => {
                        const objectURL = URL.createObjectURL(blob);
                        this.imageUrls[index] = this.sanitizer.bypassSecurityTrustUrl(objectURL);
                    },
                    error => {
                        console.error('Errore nel caricamento dell\'immagine:', error);
                    }
                );
            } else {
                console.error('imageUrl è null o undefined per la proprietà:', property);
            }
        });
    }
}
