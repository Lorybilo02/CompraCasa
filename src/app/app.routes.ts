import { Routes } from '@angular/router';
import {LoginScreenComponent} from "./login-screen/login-screen.component";
import {HomePageComponent} from "./home-page/home-page.component";
import {PublishesScreenComponent} from "./publishes-screen/publishes-screen.component";
import {AnnouncementScreenComponent} from "./announcement-screen/announcement-screen.component";
import {Appartament_characteristicsComponent} from "./announcement-screen/appartament/appartament_characteristics/appartament_characteristics.component";
import {Appartment_final_fieldsComponent} from "./announcement-screen/appartament/appartment_final_fields/appartment_final_fields.component";
import {ContactsComponent} from "./announcement-screen/contacts/contacts.component";
import {
  Villa_characteristicsComponent
} from "./announcement-screen/villa/villa_characteristics/villa_characteristics.component";
import {Villa_final_fieldsComponent} from "./announcement-screen/villa/villa_final_fields/villa_final_fields.component";
import {SigninScreenComponent} from "./signin-screen/signin-screen.component";
import {
  Building_characteristicsComponent
} from "./announcement-screen/building/building_characteristcs/building_characteristics.component";
import {ShopComponent} from "./announcement-screen/shop/shop.component";
import {DepotComponent} from "./announcement-screen/depot/depot.component";
import {GarageComponent} from "./announcement-screen/garage/garage.component";
import {TermsComponent} from "./terms/terms.component";
import {FeedbackComponent} from "./feedback/feedback.component";
import {AdminComponent} from "./admin/admin.component";
import {MyannouncementsComponent} from "./myannouncements/myannouncements.component";
import {BuyScreenComponent} from "./buy-screen/buy-screen.component";



export const routes: Routes = [
  {path:'', component: HomePageComponent},
  {path:'Login', component: LoginScreenComponent},
  {path:'Pubblica', component: PublishesScreenComponent},
  {path:'Pubblica_Annuncio/Tipologia e Località', component:AnnouncementScreenComponent},
  {path:'Pubblica_Annuncio/Appartamenti/Caratteristiche', component:Appartament_characteristicsComponent},
  {path:'Pubblica_Annuncio/Appartamenti/Prezzo e Descrizione', component: Appartment_final_fieldsComponent},
  {path:'Pubblica_Annuncio/Ville/Caratteristiche', component: Villa_characteristicsComponent},
  {path:'Pubblica_Annuncio/Ville/Prezzo e Descrizione', component: Villa_final_fieldsComponent},
  {path:'Pubblica_Annuncio/Palazzi/Caratteristiche', component: Building_characteristicsComponent},
  {path:'Pubblica_Annuncio/Palazzi/Prezzo e Descrizione', component: Villa_final_fieldsComponent},
  {path:'Pubblica_Annuncio/Uffici/Caratteristiche', component: Appartament_characteristicsComponent},
  {path:'Pubblica_Annuncio/Uffici/Prezzo e Descrizione', component: Appartment_final_fieldsComponent},
  {path:'Pubblica_Annuncio/Negozi/Caratteristiche', component:ShopComponent},
  {path:'Pubblica_Annuncio/Negozi/Prezzo e Descrizione', component: Appartment_final_fieldsComponent},
  {path:'Pubblica_Annuncio/Depositi/Caratteristiche', component:DepotComponent},
  {path:'Pubblica_Annuncio/Depositi/Prezzo e Descrizione', component: Appartment_final_fieldsComponent},
  {path:'Pubblica_Annuncio/Garages/Caratteristiche', component:GarageComponent},
  {path:'Pubblica_Annuncio/Garages/Prezzo e Descrizione', component: Villa_final_fieldsComponent},
  {path:'Pubblica_Annuncio/Contatti', component: ContactsComponent},
  {path: 'Signin', component: SigninScreenComponent },
  {path: 'TermsandCondition', component :TermsComponent},
    {path: 'Valuta', component: FeedbackComponent},
    {path: 'Admin', component: AdminComponent},
    {path:'MyAnnouncements', component: MyannouncementsComponent},
    {path:'BuyProperty', component: BuyScreenComponent}
];

