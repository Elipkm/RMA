import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { RmaConstants } from './rma-constants';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _userListUrl = this._rmaConstants.backEndMainRoute+"/usernames";
  constructor(private http: HttpClient,
              private _router: Router,
              private _rmaConstants: RmaConstants) { 
  }

  listUsers(){
    return this.http.get<any>(this._userListUrl);
  }
}
