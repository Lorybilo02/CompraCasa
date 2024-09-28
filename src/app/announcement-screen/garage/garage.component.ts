import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SharedService} from "../../sharedService";
import {FormsModule} from "@angular/forms";
declare function checkCharacteristics(): boolean;
declare function upgradeCharBar(): void;
@Component({
  selector: 'app-garage',
  standalone: true,
    imports: [
        FormsModule
    ],
  templateUrl: './garage.component.html',
  styleUrl: './garage.component.css'
})
export class GarageComponent implements OnInit{
    private charbar: HTMLInputElement;
    charFormData = {
        surface: '',
        floor: '',
        garageType: '',
        warming: '',
        energyClass: '',
        doorWidth: 0,
        electricCharge: 0,
        h24Accessibility: false,
        disableAcess: false,
        guards: false,
        alarm: false,
        propertyState: ''
    }
    constructor(private router: Router, private sharedService: SharedService) {
        this.charbar = document.getElementById('charactheristics_bar') as HTMLInputElement;

    }

    ngOnInit(): void {
        this.charbar = document.getElementById('charactheristics_bar') as HTMLInputElement;
        if(Object.keys(this.sharedService.getCharFormData()).length !== 0){
            this.charFormData = this.sharedService.getCharFormData();
            this.charbar.value=String(100);
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
