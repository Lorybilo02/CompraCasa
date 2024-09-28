import {Component, ElementRef} from '@angular/core';
import {Router} from "@angular/router";
import {CommonModule, DOCUMENT, NgClass, NgForOf, NgOptimizedImage, NgStyle} from "@angular/common";
import {HttpClient, HttpHandler, HttpHeaders, HttpParams} from '@angular/common/http';
import {AuthService} from "../auth.service";
import {FormsModule} from "@angular/forms";
import {SharedService} from "../sharedService";
import {PropertyService} from "../property.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {ImageService} from "../image.service";
import {MapSelectorComponent} from "../map-selector/map-selector.component";
import {MapSelectorHomeComponent} from "../map-selector-home/map-selector-home.component";


class Property {
    constructor(
        public id: number,
        public type: string,
        public price: number,
        public sqm: number,
        public location: string,
        public description: string,
        public starRating: number,
        public imageLink: string,
        public latitude: number,
        public longitude: number,
    )
    {}
}
@Component({
  selector: 'app-top-bar',
  standalone: true,
    imports: [
        NgOptimizedImage,
        NgClass,
        NgStyle,
        NgForOf,
        FormsModule,
        CommonModule,
        MapSelectorComponent,
        MapSelectorHomeComponent,
    ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
    imageUrls: SafeUrl[] = [];
    priceListVisible: boolean = false;
    surfaceListVIsible: boolean = false;
    selectedPosition: {lat: number, lng: number} = {lat: 0, lng: 0 };
    properties: Property[] = [];
    propertyType: string = '';
    town: string = '';
    priceMin: number = 0;
    priceMax: number = 0;
    sqmMin: number = 0;
    sqmMax: number = 0;
    public apiUrl = 'http://localhost:8080';
    isAuthenticated: boolean = false; // Stato di autenticazione dell'utente
    username: string = '';
    isAdmin: boolean = false;


    constructor(private imageService: ImageService, private sanitizer: DomSanitizer, private router: Router, private elementRef: ElementRef,private http: HttpClient, private authService: AuthService, private propertyService : PropertyService) {

    }
    ngOnInit(): void {
        // Verifica se l'utente è autenticato
        this.authService.isAuthenticated().subscribe(isAuth => {
            this.isAuthenticated = isAuth;
            if (this.isAuthenticated) {

                this.username = this.getUsernameFromLocalStorage(); // Recupera il nome utente dal localStorage
                this.isAdmin = localStorage.getItem('isAdmin') === 'true';
            }
        });
    }

    getUsernameFromLocalStorage(): string {
        return localStorage.getItem('username') || '';
    }
  redirectTo(id: string, property?:any) {
      if (id === 'BuyProperty' && property) {
          this.propertyService.setProperty(property);
      }
      this.router.navigate([id]);
  }
  updatePriceDiv(){
    const myDiv = this.elementRef.nativeElement.querySelector('#priceDiv');
    const myP = this.elementRef.nativeElement.querySelector('#priceLabel');
    const myImage = this.elementRef.nativeElement.querySelector('#priceDownArrow');
    if (this.priceListVisible) {
      myDiv.style.backgroundColor = "white";
      myP.style.color = "black";
      myImage.src = "./assets/myArrow1.png"
    }
    else{
      myDiv.style.backgroundColor = "steelblue";
      myP.style.color = "white";
      myImage.src = "./assets/myArrow.png"
    }
  }
  togglePriceList() {
    this.priceListVisible = !this.priceListVisible;
    this.updatePriceDiv()
    if(this.surfaceListVIsible){
      this.surfaceListVIsible = false;
      this.updateSurfaceDiv()
    }
  }

  handlePriceLiClick(event: Event): void {
    const regex = /^-?\d+$/;
    const clickedElement = event.target as HTMLInputElement;
    if (clickedElement.tagName === 'LI') {
      const value = clickedElement.textContent!.trim();
      this.updatePrice(value, true);
    }
    else if (clickedElement.id === 'priceFieldMin'){
        const value = clickedElement.value.trim(); //trim gli toglie spazi bianchi
        if(!value.endsWith('€')){
            this.updatePrice(value + ' €', true)
        }
        else{
            this.updatePrice(value, true);
        }
    }
    else if (clickedElement.id === 'priceFieldMax'){
        const value = clickedElement.value.trim(); //trim gli toglie spazi bianchi
        if(!value.endsWith('€')){
            this.updatePrice(value + ' €', false)
        }
        else{
            this.updatePrice(value, false);
        }
    }
  }

  updatePrice(value: string, boolean: boolean): void {
    this.togglePriceList();
    const myPriceLabel = this.elementRef.nativeElement.querySelector('#priceLabel');
    if(boolean){
        if(myPriceLabel.textContent == 'Prezzi') {
            myPriceLabel.textContent = 'Da ' + value;
        }
         else if(value == ' €'){
             const maxPriceLabel = this.elementRef.nativeElement.querySelector('#priceFieldMax');
             if(maxPriceLabel.value == ''){
                 myPriceLabel.textContent = 'Prezzi';
             }
             else {
                 const words = myPriceLabel.textContent.split(/\s+/);
                 const lastWord = words[words.length - 2];
                 myPriceLabel.textContent = 'Sotto i ' + lastWord + ' €';
             }

         }
         else{
             const words = myPriceLabel.textContent.split(/\s+/);
             const lastWord = words[words.length - 2];
             myPriceLabel.textContent = 'Da ' + value + ' a ' + lastWord + ' €'
         }

      }
     else{
         if(myPriceLabel.textContent == 'Prezzi'){
             myPriceLabel.textContent = 'Sotto i ' + value;
         }
         else if(value == ' €'){
             const minPriceLabel = this.elementRef.nativeElement.querySelector('#priceFieldMin');
             if(minPriceLabel.value == ''){
                 myPriceLabel.textContent = 'Prezzi';
             }
             else {
                 const words = myPriceLabel.textContent.split(/\s+/);
                 const firstSurface = words[1];
                 myPriceLabel.textContent = 'Da ' + firstSurface + ' €';
             }
         }
         else{
             myPriceLabel.textContent = myPriceLabel.textContent + ' a ' + value;
         }
     }
  }

  updateSurfaceDiv(){
    const myDiv = this.elementRef.nativeElement.querySelector('#surfaceDiv');
    const myP = this.elementRef.nativeElement.querySelector('#surfaceLabel');
    const myImage = this.elementRef.nativeElement.querySelector('#surfaceDownArrow');

    if (this.surfaceListVIsible) {
      myDiv.style.backgroundColor = "white";
      myP.style.color = "black";
      myImage.src = "./assets/myArrow1.png"
    }
    else{
      myDiv.style.backgroundColor = "steelblue";
      myP.style.color = "white";
      myImage.src = "./assets/myArrow.png"
    }
  }

  toggleSurfaceList() {
    this.surfaceListVIsible = !this.surfaceListVIsible;
    this.updateSurfaceDiv()
    if(this.priceListVisible){
      this.priceListVisible = false;
      this.updatePriceDiv()
    }
  }

  handleSurfaceLiClick(event: Event): void {
      const clickedElement = event.target as HTMLInputElement;
      if (clickedElement.tagName === 'LI') {
          const value = clickedElement.textContent!.trim();
          this.updateSurface(value, true);
      }
      else if (clickedElement.id === 'surfaceFieldMin'){
          const value = clickedElement.value.trim(); //trim gli toglie spazi bianchi
          if(!value.endsWith('m²')){
              this.updateSurface(value + ' m²', true)
          }
          else{
              this.updateSurface(value, true);
          }
      }
      else if (clickedElement.id === 'surfaceFieldMax'){
          const value = clickedElement.value.trim(); //trim gli toglie spazi bianchi
          if(!value.endsWith('m²')){
              this.updateSurface(value + ' m²', false)
          }
          else{
              this.updateSurface(value, false);
          }
      }
  }

  updateSurface(value: string, boolean: boolean): void {
    this.toggleSurfaceList();
    const mySurfaceLabel = this.elementRef.nativeElement.querySelector('#surfaceLabel');
    if(boolean){
        if(mySurfaceLabel.textContent == 'Superficie'){
            mySurfaceLabel.textContent = 'Da ' + value;
        }
        else if(value == ' m²'){
            const maxSurfaceLabel = this.elementRef.nativeElement.querySelector('#surfaceFieldMax');
            if(maxSurfaceLabel.value == ''){
                mySurfaceLabel.textContent = 'Superficie';
            }
            else {
                const words = mySurfaceLabel.textContent.split(/\s+/);
                const lastWord = words[words.length - 2];
                mySurfaceLabel.textContent = 'Sotto i ' + lastWord + ' m²';
            }

            }
        else{
            const words = mySurfaceLabel.textContent.split(/\s+/);
            const lastWord = words[words.length - 2];
            mySurfaceLabel.textContent = 'Da ' + value + ' a ' + lastWord + ' m²'
        }

    }
    else{
        if(mySurfaceLabel.textContent == 'Superficie'){
            mySurfaceLabel.textContent = 'Sotto i ' + value;
        }
        else if(value == ' m²'){
            const minSurfaceLabel = this.elementRef.nativeElement.querySelector('#surfaceFieldMin');
            if(minSurfaceLabel.value == ''){
                mySurfaceLabel.textContent = 'Superficie';
            }
            else {
                const words = mySurfaceLabel.textContent.split(/\s+/);
                const firstSurface = words[1];
                mySurfaceLabel.textContent = 'Da ' + firstSurface + ' m²';
            }
        }
        else{
            mySurfaceLabel.textContent = mySurfaceLabel.textContent + ' a ' + value;
        }
    }

  }

    logout(): void {
        // Visualizza un messaggio di conferma
        const isConfirmed = window.confirm('Sei sicuro di voler effettuare il logout?');


        if (isConfirmed) {
            this.authService.logout(); // Chiama il metodo di logout del servizio
            this.isAuthenticated = false; // Aggiorna lo stato di autenticazione
            this.username = ''; // Pulisce il nome utente
            this.isAdmin=false;
            this.redirectTo(''); // Reindirizza alla homepage o pagina di login
        } else {

            return;
        }
    }

    checkAuthentication(): void {
        this.authService.isAuthenticated().subscribe(
            (authenticated: boolean) => {
                this.isAuthenticated = authenticated;
                if (!this.isAuthenticated) {
                }
            },
            (error) => {
                console.error('Errore durante la verifica dell\'autenticazione', error);
            }
        );
    }
    searchProperties(location : string , priceMin: number , priceMax : number, sqmMin : number, sqmMax : number ) {
        let params = new HttpParams()
            .set('type', this.propertyType)
            .set('location', location)
            .set('priceMin', priceMin)
            .set('priceMax', priceMax)
            .set('minSqm', sqmMin)
            .set('maxSqm', sqmMax)
            .set('lat', this.selectedPosition.lat)
            .set('lng', this.selectedPosition.lng)


        this.http.get<Property[]>(`${this.apiUrl}/getPropertyByResearch`, {
            params,
            headers: { 'Accept': 'application/json' }
        })
            .subscribe(
                (data: Property[] | null) => {  // Permette a 'data' di essere anche null
                    if (data) {
                        this.properties = data;  // Assegna l'array restituito a properties
                    } else {
                        this.properties = [];  // Se data è null, assegna un array vuoto
                    }
                    this.loadImages();
                },
                (error) => {
                    console.error('Errore durante il recupero delle proprietà:', error);
                    this.properties = [];  // Assicura che properties sia almeno un array vuoto in caso di errore
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
    }
    showAlert() {
        alert("Per accedere a questa funzione, devi prima essere registrato,o se lo sei già effettua il login.");
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

    onLocationSelected(position: { lat: number; lng: number }): void {
        this.selectedPosition = position;
    }

}

