import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { RmaConstants } from './rma-constants';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private _eventsUrl = this._rmaConstants.backEndMainRoute+"/event/list";

  constructor(private http: HttpClient,
              private _router: Router,
              private _rmaConstants: RmaConstants) { 
  }

  getEvents(){
    return this.http.get<any>(this._eventsUrl);
  }
}

