import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Ship } from '../models';

@Injectable({
  providedIn: 'root'
})
export class AppStore {

  constructor() { }

  private readonly ships = new BehaviorSubject<Array<Ship>>([]);
  public readonly ships$ = this.ships.asObservable();

  public setShips(ships: Array<Ship>) {
    this.ships.next(ships);
  }
}
