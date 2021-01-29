import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/User';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  tempUser: User = new User("");

  constructor(private _authService: AuthService,
              private _router: Router) { }

  ngOnInit(): void {
  }

  signUp(): void{
    //signup button click event
    this._authService.registerUser(this.tempUser).subscribe(
      res => {
        localStorage.setItem('token', res.token);
        this._router.navigate(['/menue']);
      },
      err => {
        if(err.status === 409){
          alert("Username already in use!");
        }else{
          alert("Unknown error occurred!");
        }
      }
    );
  }

}
