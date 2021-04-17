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

  public addShip(shipPayload: ShipPayload): Observable<Array<Ship>> {
    return this.httpClient.post<Array<Ship>>(`${this.serverBaseUrl}/ships`, shipPayload);
  }

  public deleteShip(shipId: string): Observable<Array<Ship>> {
    return this.httpClient.delete<Array<Ship>>(`${this.serverBaseUrl}/ships/${shipId}`);
  }

  public updateShip(shipPayload: ShipPayload): Observable<Array<Ship>> {
    return this.httpClient.put<Array<Ship>>(`${this.serverBaseUrl}/ships`, shipPayload);
  }
}
