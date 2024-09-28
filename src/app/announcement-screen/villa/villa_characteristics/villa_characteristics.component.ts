import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SharedService} from "../../../sharedService";
import {FormsModule} from "@angular/forms";
declare function upgradeCharBar(): void;
declare function checkCharacteristics():boolean;
@Component({
  selector: 'app-villa-characteristics',
  standalone: true,
    imports: [
        FormsModule
    ],
  templateUrl: './villa_characteristics.component.html',
  styleUrl: './villa_characteristics.component.css'
})
export class Villa_characteristicsComponent implements OnInit{
    private charbar: HTMLInputElement;
    charFormData = {
        surface: '',
        floors: '',
        bedrooms: '',
        bathrooms: '',
        kitchens: '',
        hall: false,
        disableBathroom: false,
        internalCarPlaces: '0',
        externalCarPlaces: '0',
        warming: '',
        energyClass: '',
        propertyState: '',
        pool: false,
        poolSurface: '',
        garden: false,
        gardenSurface: '',
        furnishedStatus: ''
    }
  constructor(private router: Router,  private sharedService: SharedService) {
      this.charbar = document.getElementById('charactheristics_bar') as HTMLInputElement;
  }
    ngOnInit(): void {
        this.charbar = document.getElementById('charactheristics_bar') as HTMLInputElement;
        if(Object.keys(this.sharedService.getCharFormData()).length !== 0){
            this.charFormData = this.sharedService.getCharFormData();
            this.charbar.value = String(100);
        }
    }
    redirectTo(id: string) {
        if(id==='/Pubblica_Annuncio/Tipologia e Località'){
            this.sharedService.resetCharFormData();
            this.sharedService.clear();

        }
      this.router.navigate([id]);
    }
    checkRequired(id: string): void {
        if(checkCharacteristics()){
            this.sharedService.setCharFormData(this.charFormData);
            this.router.navigate([id]);
        }
    }

    upgradeCharBar(){
        upgradeCharBar();
    }
}
