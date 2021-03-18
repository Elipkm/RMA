import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/User';
import { RmaConstants } from './rma-constants';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private registerUrl = this._rmaConstants.backEndMainRoute+"/register";
  private loginUrl = this._rmaConstants.backEndMainRoute+"/login";
  private tokenRefreshUrl = this._rmaConstants.backEndMainRoute+"/renewToken";

  private jwtHelper: JwtHelperService;

  constructor(private http: HttpClient,
              private _router: Router,
              private _rmaConstants: RmaConstants) {
    this.jwtHelper = new JwtHelperService(); 
  }

  registerUser(user){
    return this.http.post<any>(this.registerUrl, user);
  }

  loginUser(user){
    return this.http.post<any>(this.loginUrl, user);
  }

  loggedIn(){
    return !!localStorage.getItem('token') && !!localStorage.getItem('loggedInUsername');
  }

  logoutUser(){
    localStorage.removeItem('token')
    localStorage.removeItem('loggedInUser');
    this._router.navigate(['/login'])
  }

  getToken(){
    return localStorage.getItem('token')
  }

  refreshToken():Observable<any>{
    return this.http.post<any>(this.tokenRefreshUrl, null);
  }

  getLoggedInUser(): User{
    if(localStorage.getItem("loggedInUsername")!=undefined){
      return new User(localStorage.getItem('loggedInUsername'));
    }
    return null;
  }


  activateAutoTokenRefresh():void{
    setInterval( () => {
      if(this.loggedIn()){
        let token = this.jwtHelper.decodeToken(this.getToken());
        //Token is refreshed after half of the expiry time is passed
        if(token.exp-(new Date).getTime()/1000 < (this._rmaConstants.tokenExpiryTimeSeconds/2)){
          this.refreshToken().subscribe(
            res => {
              console.log(res.token);
              localStorage["token"] = res.token;
            },
            err => {
              console.log("Error while refreshing token!");
            }
          );
        }
      }
    }, 5000); //5s
  }


}
