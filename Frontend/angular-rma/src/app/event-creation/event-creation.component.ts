import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Event } from 'src/Event';
import { EventService } from '../event.service';
import { ToastService } from '../toast.service';

@Component({
  selector: 'app-event-creation',
  templateUrl: './event-creation.component.html',
  styleUrls: ['./event-creation.component.css']
})
export class EventCreationComponent implements OnInit {
  formdata: FormGroup;

  constructor(private _eventService: EventService,
              private _toastService: ToastService,
              private _router: Router) { }

  ngOnInit(): void {
    this.formdata = new FormGroup({
      name: new FormControl("", Validators.required),
      startDate: new FormControl("", Validators.required),
      endDate: new FormControl(""),
   });
  }

  createEvent():void{
    if(!this.formdata.valid){
      this._toastService.show("Bitte füllen Sie alle Pflichtfelder aus.", {
        classname: "bg-danger text-light",
        delay: 4000,
        autohide: true,
        headertext: "Error"
      });
    }else{
      let value = this.formdata.value;
      let event: Event = value.endDate ? new Event(value.name, value.startDate, value.endDate) : new Event(value.name, value.startDate);
      this._eventService.createEvent(event).subscribe(
        res => {
        this._toastService.show("Das Event wurde erfolgreich angelegt.", {
          classname: "bg-success",
          delay: 4000,
          autohide: true,
          headertext: "Success"
        });
        this._router.navigate(["/event/selection"]);
      },
      err => {
        if(err.status === 401){
          this._toastService.show('Sie sind nicht eingeloggt!', {
            classname: 'bg-warning',
            delay: 4000 ,
            autohide: true,
            headertext: 'Warning'
          })
        }else if(err.status === 440){
          this._toastService.show('Ihre Session is abgelaufen, bitte loggen Sie sich erneut ein!', {
            classname: 'bg-warning text-light',
            delay: 4000 ,
            autohide: true,
            headertext: 'Warning'
          })
        }else{
          this._toastService.show('Es ist ein unbekannter Fehler aufgetreten!', {
            classname: 'bg-danger text-light',
            delay: 4000 ,
            autohide: true,
            headertext: 'Error'
          })
        }
      });
    }
  }

}
