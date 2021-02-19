import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _userListUrl = "http://localhost:8080/RMA_Restful_Service/rma/usernames";
  constructor(private http: HttpClient,
              private _router: Router) { 
  }

  listUsers(){
    return this.http.get<any>(this._userListUrl);
  }
}
