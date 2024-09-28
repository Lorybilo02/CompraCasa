import { Component ,OnInit} from '@angular/core';
import {Router} from "@angular/router";

declare function  scrollToSection():void;
//declare function submitReport(): void;

@Component({
  selector: 'app-terms',
  standalone: true,
  imports: [],
  templateUrl: './terms.component.html',
  styleUrl: './terms.component.css'
})
export class TermsComponent {
    private currentSectionId: string | null = null;
  constructor(private router: Router) {
  }

  redirectTo(id: string) {
    this.router.navigate([id]);
  }

    scrollToSection(sectionId: string) {
        const section = document.getElementById(sectionId);
        if (section) {
            // rimuove l'evidenziazione dalla sezione precedente
            if (this.currentSectionId) {
                const prevSection = document.getElementById(this.currentSectionId);
                if (prevSection) {
                    const prevH2 = prevSection.querySelector('h2');
                    if (prevH2) {
                        prevH2.classList.remove('highlight');
                    }
                }
            }

            // evidenzia l'h2 della nuova sezione
            const h2 = section.querySelector('h2');
            if (h2) {
                h2.classList.add('highlight');
            }

            // scroll verso la nuova sezione
            section.scrollIntoView({ behavior: 'smooth' });

            // memorizza l'ID della sezione corrente
            this.currentSectionId = sectionId;
        }
    }







}


