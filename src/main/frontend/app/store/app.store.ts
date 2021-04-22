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

  public addShip(ship: Ship) {
    this.ships.next([ ship, ...this.ships.getValue() ]);
  }

  public updateShip(ship: Ship) {
    this.ships.next([
      ship,
      ...this.ships.getValue().filter(s => s.shipId !== ship.shipId)
    ]);
  }

  public deleteShip(shipId: string) {
    this.ships.next(this.ships.getValue().filter(s => s.shipId !== shipId));
  }
}
