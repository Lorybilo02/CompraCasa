import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SharedService} from "../../sharedService";
import {FormsModule} from "@angular/forms";
declare function checkCharacteristics():boolean;
declare function upgradeCharBar(): void;
@Component({
  selector: 'app-shop',
  standalone: true,
    imports: [
        FormsModule
    ],
  templateUrl: './shop.component.html',
  styleUrl: './shop.component.css'
})
export class ShopComponent implements OnInit{
    private charbar : HTMLInputElement;
    charFormData = {
        surface: '',
        floor: '',
        depot: false,
        square: false,
        office: false,
        garage: false,
        alarm: false,
        locals: '',
        shopWindows: '',
        bathrooms: '',
        disableBathroom: false,
        warming: '',
        energyClass: '',
        propertyState: '',
        furnishedStatus: '',
        shopType: ''
    }
    constructor(private router: Router, private sharedService: SharedService) {
        this.charbar = document.getElementById('charactheristics_bar') as HTMLInputElement;
    }

    ngOnInit(): void {
        this.charbar = document.getElementById('charactheristics_bar') as HTMLInputElement;
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
