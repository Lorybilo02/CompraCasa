import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-publishes-screen',
  standalone: true,
  imports: [],
  templateUrl: './publishes-screen.component.html',
  styleUrl: './publishes-screen.component.css'
})
export class PublishesScreenComponent {
  constructor(private router: Router) {}
  redirectTo(id: string) {
    this.router.navigate([id]);
  }
}
