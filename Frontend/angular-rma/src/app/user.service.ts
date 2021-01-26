import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersMock: User[] = [new User("RonaldUnger"), new User("Herbst123"), new User("Sherminator"),
                              new User("ronaldunger2")]

  private _userListUrl = ".../rma/users";
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
