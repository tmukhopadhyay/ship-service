import { Injectable, NgZone } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private snackBarConfig: MatSnackBarConfig = {
    duration: 3000,
    horizontalPosition: 'center',
    verticalPosition: 'top'
  }

  constructor(
    private zone: NgZone,
    private snackBar: MatSnackBar
  ) { }

  public showSuccess(message: string) {
    this.zone.run(() => {
      this.snackBar.open(message, '', {
        ...this.snackBarConfig,
        panelClass: 'mat-success'
      });
    });
  }

  public showError(message: string) {
    this.zone.run(() => {
      this.snackBar.open(message, '', {
        ...this.snackBarConfig,
        panelClass: 'mat-error'
      });
    });
  }
}
