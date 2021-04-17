import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Ship } from '../../models';

import { HttpService, NotificationService } from '../../services';
import { AppStore } from '../../store';

@Component({
  selector: 'app-ship-form',
  templateUrl: './ship-form.component.html',
  styleUrls: ['./ship-form.component.scss']
})
export class ShipFormComponent implements OnInit, OnDestroy {

  @ViewChild('form', { static: false }) public form?: FormGroupDirective;
  @Input() public headline = 'Add a ship';
  @Input() public ship: Ship = {} as Ship;
  @Input() public mode = 'create';
  @Output() public onUpdate = new EventEmitter<boolean>();

  public shipForm: FormGroup = new FormGroup({
    shipId: new FormControl(''),
    code: new FormControl('', [Validators.required, Validators.pattern('^[A-Z]{4}-[0-9]{4}-[A-Z][0-9]$')]),
    name: new FormControl('', [Validators.required]),
    length: new FormControl('', [Validators.required]),
    width: new FormControl('', [Validators.required])
  });

  private createShipSubscription?: Subscription;
  private editShipSubscription?: Subscription;

  constructor(
    private appStore: AppStore,
    private httpService: HttpService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.shipForm.get('shipId')?.setValue(this.ship.shipId || '');
    this.shipForm.get('code')?.setValue(this.ship.code || '');
    this.shipForm.get('name')?.setValue(this.ship.name || '');
    this.shipForm.get('length')?.setValue(this.ship.length || '');
    this.shipForm.get('width')?.setValue(this.ship.width || '');
  }

  public addShip(event: Event) {
    event.preventDefault();
    this.createShipSubscription = this.httpService.addShip(this.shipForm?.value).subscribe((ships: Array<Ship>) => {
      this.notificationService.showSuccess('Ship details added successfully');
      this.form?.resetForm();
      this.appStore.setShips(ships);
    });
  }

  public updateShip(event: Event) {
    event.preventDefault();
    this.createShipSubscription = this.httpService.updateShip(this.shipForm?.value).subscribe((ships: Array<Ship>) => {
      this.appStore.setShips(ships);
      this.onUpdate.emit(true);
    });
  }

  public ngOnDestroy() {
    if(this.createShipSubscription) { this.createShipSubscription.unsubscribe(); }
    if(this.editShipSubscription) { this.editShipSubscription.unsubscribe(); }
  }
}
