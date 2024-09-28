import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SharedService} from "../../../sharedService";
import {FormsModule} from "@angular/forms";
declare function checkCharacteristics():boolean;
declare function upgradeCharBar(): void;
@Component({
  selector: 'app-building',
  standalone: true,
    imports: [
        FormsModule
    ],
  templateUrl: './building_characteristics.component.html',
  styleUrl: './building_characteristics.component.css'
})
export class Building_characteristicsComponent implements OnInit{
    private charbar: HTMLInputElement;
    charFormData = {
        surface: '',
        floors: '',
        buildingType: '',
        elevators: '',
        locals: '',
        bathrooms: '',
        garages: '',
        internalCarPlaces: '0',
        externalCarPlaces: '0',
        warming: '',
        energyClass: '',
        propertyState: '',
        furnishedStatus: '',
    }
  constructor(private router: Router, private sharedService: SharedService) {
      this.charbar = document.getElementById('charactheristics_bar') as HTMLInputElement;
  }
  redirectTo(id: string) {
      if(id==='/Pubblica_Annuncio/Tipologia e Località'){
          this.sharedService.resetCharFormData();
          this.sharedService.clear();

      }
    this.router.navigate([id]);
  }
    ngOnInit(): void {
        this.charbar = document.getElementById('charactheristics_bar') as HTMLInputElement;
        if(Object.keys(this.sharedService.getCharFormData()).length !== 0){
            this.charFormData = this.sharedService.getCharFormData();
            this.charbar.value=String(100);
        }
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
