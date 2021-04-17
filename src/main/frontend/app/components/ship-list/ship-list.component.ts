import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';

import { Ship } from '../../models';
import { HttpService, NotificationService } from '../../services';
import { AppStore } from '../../store';
import { ShipEditDialogComponent } from '../ship-edit-dialog/ship-edit-dialog.component';

@Component({
  selector: 'app-ship-list',
  templateUrl: './ship-list.component.html',
  styleUrls: ['./ship-list.component.scss']
})
export class ShipListComponent implements OnInit, OnDestroy {

  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator = null as unknown as MatPaginator;

  public ships: Array<Ship> = [];
  public dataSource: MatTableDataSource<Ship> = new MatTableDataSource<Ship>([]);
  public displayedColumns: string[] = ['code', 'name', 'length', 'width', 'createdDate', 'updatedDate', 'actions'];

  private shipSubscription?: Subscription;
  private deleteShipSubscription?: Subscription;

  constructor(
    private appStore: AppStore,
    public dialog: MatDialog,
    private httpService: HttpService,
    private notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.shipSubscription = this.appStore.ships$.subscribe((ships: Array<Ship>) => {
      this.ships = ships;
      this.dataSource = new MatTableDataSource<Ship>(ships.length ? ships : [{'code': 'No data available'} as Ship]);
      this.dataSource.paginator = this.paginator;
    });
  }

  public editShipDetails(ship: Ship) {
    const dialogRef = this.dialog.open(ShipEditDialogComponent, {
      data: ship
    });
  }

  public deleteShip(shipId: string) {
    if(confirm('Are you sure you want to delete this ship?')) {
      this.deleteShipSubscription = this.httpService.deleteShip(shipId).subscribe((ships: Array<Ship>) => {
        this.notificationService.showSuccess('Ship deleted successfully');
        this.appStore.setShips(ships)
      });
    }
  }

  public ngOnDestroy() {
    if(this.shipSubscription) { this.shipSubscription.unsubscribe(); }
    if(this.deleteShipSubscription) { this.deleteShipSubscription.unsubscribe(); }
  }
}
