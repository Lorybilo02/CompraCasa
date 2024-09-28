import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";
import {SharedService} from "../../../sharedService";
import {FormsModule} from "@angular/forms";
import {MapSelectorComponent} from "../../../map-selector/map-selector.component";
declare function upgradePriceBar(images: boolean): void;
declare function checkFinalFields(): boolean;
@Component({
  selector: 'app-villa-final-fields',
  standalone: true,
    imports: [
        NgForOf,
        NgIf,
        FormsModule,
        MapSelectorComponent
    ],
  templateUrl: './villa_final_fields.component.html',
  styleUrl: './villa_final_fields.component.css'
})
export class Villa_final_fieldsComponent implements OnInit{
  private priceBar: HTMLInputElement;
  selectedPosition: { lat: number; lng: number } = { lat: 0, lng: 0 };
  selectValue: string = '';
  loadedPhotos: { url: string }[] = []; // Array per memorizzare le foto caricate
  descriptionFormData: any = {
      price: '',
      description: '',
      images: '',
      imageLink: '',
      lat: 0.0,
      lng: 0.0
  };
  constructor(private router: Router, private sharedService: SharedService) {
      this.priceBar = document.getElementById('priceBar') as HTMLInputElement;
      this.selectValue = this.sharedService.selectValueSource;

  }
    redirectTo(id: string) {
        if(id==='/Pubblica_Annuncio/Tipologia e Località'){
            this.sharedService.clear();
            this.sharedService.resetCharFormData();
            this.sharedService.priceBarValue = '';
        }
        this.sharedService.priceBarValue = this.priceBar.value;
        this.router.navigate([id]);
    }
    ngOnInit(): void {
        this.priceBar = document.getElementById('priceBar') as HTMLInputElement;
        this.priceBar.value = this.sharedService.priceBarValue;
        const savedFormData = this.sharedService.get('descriptionFormData');
        if (savedFormData) {
            this.descriptionFormData = savedFormData;
        }

        const savedPhotos = this.sharedService.get('loadedPhotos');
        if (savedPhotos) {
            this.loadedPhotos = savedPhotos;
        }
    }
    saveState(): void {
        this.descriptionFormData.images = this.loadedPhotos;
        // Salva lo stato attuale
        this.sharedService.set('descriptionFormData', this.descriptionFormData);
        this.sharedService.set('loadedPhotos', this.loadedPhotos);
    }
    upgradePriceBar(){

        if(this.loadedPhotos.length === 0){
            upgradePriceBar(false);
        }
        else{
            upgradePriceBar(true);
        }
        this.saveState();
    }

    checkRequired(id: string): void {
        if(checkFinalFields()){
            this.sharedService.setDescriptionFormData(this.descriptionFormData);
            if(this.loadedPhotos.length === 0) {
                alert("Devi inserire almeno un immagine...");
            }
            else{
                this.router.navigate([id]);
            }

        }
    }

    loadPhoto() {
        if (this.loadedPhotos.length > 0) {
            alert("Puoi caricare massimo 1 foto!");
        }
        else {
            const fileInput = document.querySelector('#image_box input[type="file"]') as HTMLInputElement;
            fileInput.click();
        }
    }

  removePhoto(){
      if(this.loadedPhotos.length>0){
          this.loadedPhotos.pop();
          if(this.loadedPhotos.length===0){
              this.priceBar.value = (parseFloat(this.priceBar.value) - 33.33).toFixed(2);
          }
      }
      else{
          alert("Non hai foto da rimuovere!");
      }
  }
    onLocationSelected(position: { lat: number; lng: number }): void {
        this.selectedPosition = position;  // Usa il tipo corretto qui
        this.descriptionFormData.lat = this.selectedPosition.lat;
        this.descriptionFormData.lng = this.selectedPosition.lng;
    }
    onFileSelected(event: Event): void {
        const input = event.target as HTMLInputElement;
        if (input.files) {
            const files = Array.from(input.files);
            const formData = new FormData();

            for (const file of files) {
                formData.append('image', file);
                // Invia il nome del file come richiesto dal backend
                formData.append('filename', file.name);
                const reader = new FileReader();
                reader.onload = (e: any) => {
                    this.loadedPhotos.push({ url: e.target.result });
                };
                reader.readAsDataURL(file);
            }

            // Send the form data to the server
            this.uploadImages(formData);
        }
    }
    async uploadImages(formData: FormData): Promise<void> {
        try {
            const response = await fetch('http://localhost:8080/upload', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                // Legge la risposta come stringa
                this.descriptionFormData.imageLink = await response.text();
                // Qui puoi utilizzare la stringa restituita dal server
            } else {
                const errorText = await response.text();  // Legge l'errore restituito come stringa
                console.error('Failed to upload images:', errorText);
            }
        } catch (error) {
            alert("La foto che hai inserito è troppo grande!")
            this.loadedPhotos.pop()
            console.error('Error uploading images:', error);
        }
    }

}

