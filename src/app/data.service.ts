import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class DataService {
    private apiUrl = 'http://localhost:3000';

    constructor(private http: HttpClient) { }

    getData(): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/percorso`); // replace '/path' with the path to the API
    }

    postData(data: any): Observable<any> {
        return this.http.post<any>(`${this.apiUrl}/percorso`, data); // replace '/path' with the path to the API
    }
    // method to check if the user exists
    checkUserExists(username: string): Observable<boolean> {
        return this.http.post<boolean>(`${this.apiUrl}/users/check`, { username });
    }
}

