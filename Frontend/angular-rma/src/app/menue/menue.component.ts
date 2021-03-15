import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/User';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-menue',
  templateUrl: './menue.component.html',
  styleUrls: ['./menue.component.css']
})
export class MenueComponent implements OnInit {
  user: User;

  constructor(private _authService: AuthService,
              private _router: Router) { }

  ngOnInit(): void {
    this.user = this._authService.getLoggedInUser();
  }

  newRun(): void{
    this._router.navigate(['/event/selection']);
  }

  results(): void{

  }

  logOut(): void{
    this._authService.logoutUser();
  }

}
