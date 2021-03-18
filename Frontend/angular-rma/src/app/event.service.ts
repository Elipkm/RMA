import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Event } from 'src/Event';
import { RmaConstants } from './rma-constants';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private _eventsListUrl = this._rmaConstants.backEndMainRoute+"/event/list";
  private _eventsCreateUrl = this._rmaConstants.backEndMainRoute+"/event";

  constructor(private http: HttpClient,
              private _router: Router,
              private _rmaConstants: RmaConstants) { 
  }

  getEvents():Observable<any>{
    return this.http.get<any>(this._eventsListUrl);
  }

  createEvent(event: Event):Observable<any>{
    return this.http.post<any>(this._eventsCreateUrl, event);
  }
}

