import { Component } from '@angular/core';
import {PropertyService} from "../property.service";
import {Router} from "@angular/router";
import {routes} from "../app.routes";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-buy-screen',
  standalone: true,
  imports: [],
  templateUrl: './buy-screen.component.html',
  styleUrl: './buy-screen.component.css'
})
export class BuyScreenComponent {
    property: any;
    username: string = '';
    type: string = '';
    id: number = 0;
    price: number = 0;
    sqm: number = 0;
    description: string = '';
    starRating: number = 0;
    phoneNumber: string = '';
    email: string = '';

    constructor(private propertyService : PropertyService, private router: Router, private http: HttpClient) {
    }

    ngOnInit(): void {
        this.username = localStorage.getItem('username') ?? '';
        this.property = this.propertyService.getProperty();
        this.price = this.property.price;
        this.sqm = this.property.sqm;
        this.id = this.property.id;
        this.description = this.property.description;
        this.starRating = this.property.starRating;
        this.phoneNumber = this.property.phoneNumber;
        this.email = this.property.email;
        this.type = this.property.type;
    }

    buy() {
        this.buyProperty(this.username, this.id).subscribe(
            (response: boolean) => {
                if (response) {
                    alert("Perfetto! La casa è prenotata, hai diritto di precedenza sull'acquisto. Per concordare i metodi di pagamento contatta il venditore!");
                } else {
                    alert("Ops! La casa è stata già prenotata o acquistata !");
                }
                this.redirectTo();
            },
            (error) => {
                console.error("Errore nella chiamata HTTP:", error);
                this.redirectTo();
            }
        );
    }
    redirectTo(){
        this.router.navigate(['']);
    }
    buyProperty(buyer_username: string, property_id: number) {
        return this.http.put<boolean>('http://localhost:8080/buy', null, {
            params: { buyer_username: buyer_username, property_id: property_id.toString() }
        });
    }

}
