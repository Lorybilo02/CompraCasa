import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SharedService} from "../../sharedService";
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {AuthService} from "../../auth.service";
import {catchError} from "rxjs/operators";
import {lastValueFrom, Observable, throwError} from "rxjs";
import {FormsModule} from "@angular/forms";
declare function checkIt():boolean;
declare function upgradeContactsBar(): void;
declare function checkContacts(): boolean;
@Component({
  selector: 'app-contacts',
  standalone: true,
    imports: [
        FormsModule
    ],
  templateUrl: './contacts.component.html',
  styleUrl: './contacts.component.css'
})
export class ContactsComponent implements OnInit{
  isAuthenticated : boolean = false;
  selectValue : string;
  phoneNumber: string = '';
  email: string = '';
  typeFormData: any = {};
  charFormData: any = {};
  descriptionFormData: any = {};
  contactsFormData: any = {
      phoneNumber: '',
      email: '',
      username: ''
  };
  jsonData : any = {
      typeFormData: {
          propertyType: '',
          sellMode: '',
          town: '',
          address: '',
          civicNumber: ''
      },
      charFormData: {
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
      },
      descriptionFormData: {
          images: [],
          filename: '',
          price: '',
          condominialBills: '',
          availability: '',
          description: ''
      },
      contactsFormData: {
          phoneNumber: '',
          email: '',
          username: ''
      }
  };
  constructor(private router: Router, private sharedService : SharedService,private http: HttpClient, private authService : AuthService) {
      this.selectValue = "sium";
      this.charFormData = sharedService.firstFormData;
      this.typeFormData = sharedService.typeFormData;
      this.descriptionFormData = sharedService.secondFormData;

  }
  ngOnInit(): void {
      this.selectValue = this.sharedService.selectValueSource;
      this.charFormData = this.sharedService.firstFormData;
      this.typeFormData = this.sharedService.typeFormData;
      this.descriptionFormData = this.sharedService.secondFormData;
  }

  redirectTo(id: string) {
      if(id==='/Pubblica_Annuncio/Tipologia e Località'){
          this.sharedService.clear();
          this.sharedService.resetCharFormData();
      }
      if(id===''){
          this.sharedService.clear();
          this.sharedService.resetCharFormData();
          this.finished();
      }
      this.router.navigate([id]);
  }
  finished(){
    alert("Perfetto! L'annuncio è stato completato. Sarà visibile nella sezione 'I miei Annunci'!")
  }
    checkRequired(id: string): void {
        if(checkIt()){
            this.router.navigate([id]);
        }

        else {
            alert('CI SONO DEI CAMPI OBBLIGATORI CHE NON SONO STATI COMPILATI. DEVI COMPILARLI ' +
                'SE VUOI PROSEGUIRE OLTRE!');
        }
    }

    upgradeContactsBar(){
      upgradeContactsBar();
    }


    validateEmail() {
      if(checkContacts()){
          this.contactsFormData.username = localStorage.getItem('username')
          this.contactsFormData.email = this.email;
          this.contactsFormData.phoneNumber = this.phoneNumber;
          this.sendData();
          this.redirectTo('');
      }
    }

    async sendData() {

        try {
            this.jsonData = {
                typeFormData: this.typeFormData,
                charFormData: this.charFormData,
                descriptionFormData: this.descriptionFormData,
                contactsFormData: this.contactsFormData,
            };

            const response = await fetch("http://localhost:8080/saveAnnouncment", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(this.jsonData),
            });

            if (!response.ok) {
                throw new Error("Errore durante la chiamata alla servlet.");
            }

            const data = await response.text();
        } catch (error) {
            console.error("Errore durante la chiamata alla servlet:", error);
        }
    }

    private handleError(error: HttpErrorResponse) {
        console.error('An error occurred:', error);
        return throwError(() => new Error('Si è verificato un errore durante il caricamento del file. Per favore riprova più tardi.'));
    }


    private async convertUrlToDataUri(url: string): Promise<string> {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Failed to fetch blob from URL');
        }

        const blob = await response.blob();
        const reader = new FileReader();

        return new Promise((resolve, reject) => {
            reader.onloadend = () => {
                const base64Data = (reader.result as string).split(',')[1];
                const mimeType = blob.type || 'application/octet-stream';
                const dataUri = `data:${mimeType};base64,${base64Data}`;
                resolve(dataUri);
            };

            reader.onerror = reject;
            reader.readAsDataURL(blob);
        });
    }

    private blobToBase64(blob: Blob): Promise<string> {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(blob);
            reader.onloadend = () => {
                resolve(reader.result as string);
            };
            reader.onerror = (error) => {
                reject(error);
            };
        });
    }
}
