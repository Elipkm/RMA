import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/User';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  users: User[] = [];

  userListLeftStyles: Number[] = [69, 299, 529]; //for showing the users shortcut fields in right column

  constructor(private _router:Router,
              private _userService: UserService) { }

  ngOnInit(): void {
    this._userService.listUsers().subscribe(
      res => {this.users = res},
      err => console.log(err)
    )
  }

  logIn(): void{
    //login button click event
    this._router.navigate(['/signup'])
  }

  calculateTopStyleOfUserShortcut(i: number):number{
    console.log(240+236*Math.floor((i/3)));
    return 240+236*Math.floor((i/3));
  }

}
