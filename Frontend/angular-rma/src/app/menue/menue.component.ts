import { Component, OnInit } from '@angular/core';
import { User } from 'src/User';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-menue',
  templateUrl: './menue.component.html',
  styleUrls: ['./menue.component.css']
})
export class MenueComponent implements OnInit {
  user: User;

  constructor(public _authService: AuthService) { }

  ngOnInit(): void {
    this.user = this._authService.getLoggedInUser();
  }

  newRun(): void{

  }

  results(): void{

  }

  logOut(): void{
    this._authService.logoutUser();
  }

}
