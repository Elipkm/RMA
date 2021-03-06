import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _registerUrl = "http://localhost:8080/RMA_Restful_Service/rma/register";
  private _loginUrl = "http://localhost:8080/RMA_Restful_Service/rma/login";

  constructor(private http: HttpClient,
              private _router: Router) { 
  }

  registerUser(user){
    return this.http.post<any>(this._registerUrl, user);
  }

  loginUser(user){
    return this.http.post<any>(this._loginUrl, user);
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

  getLoggedInUser(): User{
    if(localStorage.getItem("loggedInUsername")!=undefined){
      return new User(localStorage.getItem('loggedInUsername'));
    }
    return null;
  }


}
