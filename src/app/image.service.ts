import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ImageService {

    private baseUrl = 'http://localhost:8080';

    constructor(private http: HttpClient) { }

    getImage(filename: string): Observable<Blob> {
        const url = `${this.baseUrl}/image?filename=${encodeURIComponent(filename)}`;
        return this.http.get(url, { responseType: 'blob' });  // Richiedi l'immagine come blob
    }
}
