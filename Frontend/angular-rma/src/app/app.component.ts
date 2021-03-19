import { Component } from '@angular/core';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-rma';

  constructor(private _authService: AuthService){
    this._authService.activateAutoTokenRefresh();
  }
}
