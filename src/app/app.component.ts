import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {HomePageComponent} from './home-page/home-page.component'
import {LoginScreenComponent} from "./login-screen/login-screen.component";
import {PublishesScreenComponent} from "./publishes-screen/publishes-screen.component";
import {Appartament_characteristicsComponent} from "./announcement-screen/appartament/appartament_characteristics/appartament_characteristics.component";
import {Appartment_final_fieldsComponent} from "./announcement-screen/appartament/appartment_final_fields/appartment_final_fields.component";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {AdminComponent} from "./admin/admin.component";
import {MyannouncementsComponent} from "./myannouncements/myannouncements.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HomePageComponent, LoginScreenComponent,
    PublishesScreenComponent, Appartament_characteristicsComponent,
    Appartment_final_fieldsComponent, HttpClientModule, FormsModule,AdminComponent,MyannouncementsComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
    providers: [
        HttpClientModule,

    ],
})
export class AppComponent {
  title = 'hello';
}
