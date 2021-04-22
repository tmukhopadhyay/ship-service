import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Ship, ShipPayload } from '../models';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  private serverBaseUrl = environment.serverBaseUrl;

  constructor(private httpClient: HttpClient) { }

  public getAllShips(): Observable<Array<Ship>> {
    return this.httpClient.get<Array<Ship>>(`${this.serverBaseUrl}/ships`);
  }

  public getShipDetails(shipId: string): Observable<Ship> {
    return this.httpClient.get<Ship>(`${this.serverBaseUrl}/ship/${shipId}`);
  }

  public addShip(shipPayload: ShipPayload): Observable<Ship> {
    return this.httpClient.post<Ship>(`${this.serverBaseUrl}/ships`, shipPayload);
  }

  public deleteShip(shipId: string) {
    return this.httpClient.delete(`${this.serverBaseUrl}/ships/${shipId}`);
  }

  public updateShip(shipPayload: ShipPayload): Observable<Ship> {
    return this.httpClient.put<Ship>(`${this.serverBaseUrl}/ships`, shipPayload);
  }
}
