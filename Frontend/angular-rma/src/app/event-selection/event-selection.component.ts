import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/User';
import { AuthService } from '../auth.service';
import { EventService } from '../event.service';
import { ToastService } from '../toast.service';
import { Event } from '../../Event';

@Component({
  selector: 'app-event-selection',
  templateUrl: './event-selection.component.html',
  styleUrls: ['./event-selection.component.css']
})
export class EventSelectionComponent implements OnInit {
  user: User;
  events: Array<Event> = [];
  designOrientedEventList: Event[][] = [];

  selectedEvent: Event;


  constructor(private _authService: AuthService,
              private _eventService: EventService,
              private _toastService: ToastService,
              private _router: Router) { }

  ngOnInit(): void {
    this.user = this._authService.getLoggedInUser();
    this._eventService.getEvents().subscribe(
      res => {
        for(let rawEvent of res){
          this.events.push(Event.eventOfObject(rawEvent));
        }
        this.createDesignOrientedEventList();
      },
      err => {
          this._toastService.show('Es ist ein unbekannter Fehler aufgetreten!', {
            classname: 'bg-danger text-light',
            delay: 4000 ,
            autohide: true,
            headertext: 'Error'
          })
        this.createDesignOrientedEventList();
      }
    );
  }

  logOut(): void{
    this._authService.logoutUser();
  }

  createDesignOrientedEventList(): void{
    let row = 0;
    this.designOrientedEventList[row] = [];
    this.designOrientedEventList[row][0] = null;
    
    for(let i = 0; i<this.events.length;i++){
      if((i+1) % 3 === 0 && i!=0){
        row++;
      }

      if(this.designOrientedEventList[row]==undefined){
        this.designOrientedEventList[row] = [];
      }
      this.designOrientedEventList[row][(i+1)%3] = this.events[i];
    }
  }

  setSelectedEvent(event: Event):void{
    this.selectedEvent = event;
  }

  back():void{
    this._router.navigate(["/menu"]);
  }

  confirmAndOpenEvent():void{
    
  }

  editEvent():void{

  }
}
