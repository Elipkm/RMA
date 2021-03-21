import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Event } from 'src/Event';
import { RmaConstants } from './rma-constants';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private eventsListUrl = this._rmaConstants.backEndMainRoute+"/event/list";
  private eventsCreateUrl = this._rmaConstants.backEndMainRoute+"/event";
  private eventsDelteUrl = this._rmaConstants.backEndMainRoute+"/event/";
  private eventsUpdateUrl = this._rmaConstants.backEndMainRoute+"/event";

  constructor(private http: HttpClient,
              private _rmaConstants: RmaConstants) { 
  }

  getEvents():Observable<any>{
    return this.http.get<any>(this.eventsListUrl);
  }

  createEvent(event: Event):Observable<any>{
    return this.http.post<any>(this.eventsCreateUrl, event);
  }

  deleteEvent(event: Event):Observable<any>{
    return this.http.delete<any>(this.eventsDelteUrl+event.ID);
  }

  updateEvent(event: Event):Observable<any>{
    return this.http.put<any>(this.eventsUpdateUrl, event);
  }
}

