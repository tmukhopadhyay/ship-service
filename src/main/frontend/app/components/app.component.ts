import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Ship } from '../models';
import { HttpService } from '../services';
import { AppStore } from '../store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  private allShipSubscription?: Subscription;

  constructor(
    private appStore: AppStore,
    private httpService: HttpService
  ) {}

  public ngOnInit() {
    this.allShipSubscription = this
      .httpService
      .getAllShips()
      .subscribe((ships: Array<Ship>) => {
        this.appStore.setShips(ships);
      });
  }

  public ngOnDestroy() {
    if(this.allShipSubscription) {
      this.allShipSubscription.unsubscribe();
    }
  }
}
