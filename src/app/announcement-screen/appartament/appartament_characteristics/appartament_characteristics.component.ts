import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SharedService} from "../../../sharedService";
import {FormsModule} from "@angular/forms";
declare function checkCharacteristics(): boolean;
declare function upgradeCharBar(): boolean;
@Component({
  selector: 'app-characteristics-screen',
  standalone: true,
    imports: [
        FormsModule
    ],
  templateUrl: './appartament_characteristics.component.html',
  styleUrl: './appartament_characteristics.component.css'
})
export class Appartament_characteristicsComponent implements OnInit{
  private charbar : HTMLInputElement;
  selectValue : string = '';
  charFormData = {
    surface: '',
    floor: '',
    bedrooms: '',
    bathroom: '',
    kitchens: '',
    hall: false,
    warming: '',
    energyClass: '',
    propertyState: '',
    elevator: false,
    carPlace: false,
    disableBathroom: false,
    furnishedStatus: ''
}
  constructor(private router: Router, private sharedService : SharedService) {
      this.charbar = document.getElementById('charactheristics_bar') as HTMLInputElement;
  }
  ngOnInit(): void {
      this.charbar = document.getElementById('charactheristics_bar') as HTMLInputElement;
      this.selectValue = this.sharedService.selectValueSource;
      if(Object.keys(this.sharedService.getCharFormData()).length !== 0){
          this.charFormData = this.sharedService.getCharFormData();
          this.charbar.value= String(100);
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
