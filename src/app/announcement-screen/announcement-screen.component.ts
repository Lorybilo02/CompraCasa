import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {AppComponent} from "../app.component";
import {SharedService} from "../sharedService";
declare function checkFirstPage(): boolean;
declare function upgradeFirstBar(): void;
@Component({
  selector: 'app-announcement-screen',
  standalone: true,
  imports: [
    FormsModule,
    AppComponent,
  ],
  templateUrl: './announcement-screen.component.html',
  styleUrl: './announcement-screen.component.css'
})
export class AnnouncementScreenComponent implements OnInit{
  public selectedValue: string;
  public typeBar: HTMLInputElement;
  public propertyType: HTMLInputElement;
  typeFormData = {
      propertyType: '',
      sellMode: '',
      town: '',
      address: '',
      civicNumber: ''
  }

  constructor(private router: Router, private sharedService : SharedService) {
    this.selectedValue = this.sharedService.selectValueSource;
    this.typeBar = document.getElementById('typeBar') as HTMLInputElement;
    this.propertyType = document.getElementById('propertyType') as HTMLInputElement;
  }

  ngOnInit() {
      this.selectedValue = this.sharedService.selectValueSource;
      this.typeBar = document.getElementById('typeBar') as HTMLInputElement;
      this.propertyType = document.getElementById('propertyType') as HTMLInputElement;
      if(Object.keys(this.sharedService.getTypeFormData()).length !== 0){
          this.typeFormData = this.sharedService.getTypeFormData();
          this.typeBar.value=String(100);
          this.propertyType.value = this.selectedValue;
      }
  }

    onSelectChange(event: any) {
    if (event.target.value==="Appartaments"){
      this.selectedValue = "Appartamenti"
      this.sharedService.setSelectValue("Appartamenti")
    }
    else if (event.target.value==="Villas"){
      this.selectedValue = "Ville"
      this.sharedService.setSelectValue("Ville")
    }
    else if (event.target.value==="Buildings"){
      this.selectedValue = "Palazzi"
      this.sharedService.setSelectValue("Palazzi")
    }
    else if (event.target.value==="Offices"){
      this.selectedValue = "Uffici"
      this.sharedService.setSelectValue("Uffici")
    }
    else if (event.target.value==="Shops"){
      this.selectedValue = "Negozi"
      this.sharedService.setSelectValue("Negozi")
    }
    else if (event.target.value==="Depots"){
      this.selectedValue = "Depositi"
      this.sharedService.setSelectValue("Depositi")
    }
    else if (event.target.value==="Garages"){
      this.selectedValue = "Garages"
      this.sharedService.setSelectValue("Garages")
    }
    upgradeFirstBar()
  }
  redirectTo(id: string) {
    this.router.navigate([id]);
  }
  checkRequired(id: string): void {
      if(checkFirstPage()){
          this.sharedService.setTypeFormData(this.typeFormData);
          this.router.navigate([id]);
      }
      upgradeFirstBar();
  }

  upgradeBar() : void {
      upgradeFirstBar();
  }

}
