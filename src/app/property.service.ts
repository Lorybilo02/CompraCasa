import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class PropertyService {
    private selectedProperty: any;

    constructor() { }

    setProperty(property: any) {
        this.selectedProperty = property;
    }

    getProperty() {
        return this.selectedProperty;
    }
}
