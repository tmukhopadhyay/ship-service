import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Ship } from '../../models';
import { NotificationService } from '../../services';

@Component({
  selector: 'app-ship-edit-dialog',
  templateUrl: './ship-edit-dialog.component.html',
  styleUrls: ['./ship-edit-dialog.component.scss']
})
export class ShipEditDialogComponent {

  public ship: Ship;

  constructor(
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<ShipEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Ship
  ) {
    this.ship = data;
  }

  public onUpdate() {
    this.dialogRef.close();
    this.notificationService.showSuccess('Ship details updated successfully');
  }
}
