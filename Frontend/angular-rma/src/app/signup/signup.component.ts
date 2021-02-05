import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/User';
import { AuthService } from '../auth.service';
import { ToastService } from '../toast.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  tempUser: User = new User("");

  constructor(private _authService: AuthService,
              private _router: Router,
              private _toastService: ToastService) { }

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
          this._toastService.show('Der Benutzername ist bereits vergeben!', {
            classname: 'bg-warning text-light',
            delay: 4000 ,
            autohide: true,
            headertext: 'Warning'
          })
        }else{
          this._toastService.show('Es ist ein unbekannter Fehler aufgetreten!', {
            classname: 'bg-danger text-light',
            delay: 4000 ,
            autohide: true,
            headertext: 'Error'
          })
        }
      }
    );
  }

}
