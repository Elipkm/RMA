import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _userListUrl = "http://localhost:8080/RMA_Restful_Service/rma/usernames";
  //private _userListUrl =  "https://jsonplaceholder.typicode.com/todos/";
  constructor(private http: HttpClient,
              private _router: Router) { 
  }

  listUsers(){
    return this.http.get<any>(this._userListUrl);
    /*//list with mock data for testing purpose
    return new Observable<any>((observer) => {
      observer.next(this.usersMock);
      observer.complete();
    });*/
  }
}
