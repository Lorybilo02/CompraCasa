import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import * as L from 'leaflet';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-map-selector-home',
    standalone: true,
    templateUrl: './map-selector-home.component.html',
    styleUrls: ['./map-selector-home.component.css'],
    imports: [CommonModule]
})
export class MapSelectorHomeComponent implements OnInit {
    map: any;
    selectedPosition: { lat: number; lng: number } = { lat: 0, lng: 0 };

    @Output() selectedPositionChange = new EventEmitter<{ lat: number; lng: number }>();

    constructor() {}

    ngOnInit(): void {
        this.map = L.map('map').setView([41.9028, 12.4964], 13);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap contributors'
        }).addTo(this.map);

        this.map.on('click', (e: L.LeafletMouseEvent) => {
            const { lat, lng } = e.latlng;  // Estrae lat e lng dall'evento Leaflet
            this.selectedPosition = { lat, lng };  // Assegna correttamente la posizione
            this.selectedPositionChange.emit(this.selectedPosition);  // Emette solo le coordinate { lat, lng }

            L.marker([lat, lng]).addTo(this.map)
                .bindPopup(`Posizione Selezionata: ${lat.toFixed(4)}, ${lng.toFixed(4)}`)
                .openPopup();
        });
    }

    isValidPosition(position: any): boolean {
        try {
            JSON.stringify(position);  // Prova a serializzare l'oggetto
            return true;
        } catch (e) {
            return false;  // Se genera un'eccezione, restituisci false
        }
    }
}
