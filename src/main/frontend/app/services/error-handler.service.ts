import { ErrorHandler, Injectable } from '@angular/core';
import { NotificationService } from './notification.service';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService implements ErrorHandler {

  constructor(private notificationService: NotificationService) { }

  handleError(err: any): void {
    console.log(err);
    this.notificationService.showError(err.error ? err.error.error : err.message);
  }
}
