import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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
              private _toastService: ToastService) { }

  ngOnInit(): void {
    this.formdata = new FormGroup({
      name: new FormControl("", Validators.required),
      startDate: new FormControl("", Validators.required),
      endDate: new FormControl("", Validators.required)
   });
  }

  createEvent():void{
    if(!this.formdata.valid){
      console.log("invalid");
    }else{
      let value = this.formdata.value;
      let event: Event = new Event(0, value.name, value.startDate, value.endDate);
      this._eventService.createEvent(event).subscribe(
        res => {
        this._toastService.show("Das Event wurde erfolgreich angelegt.", {
          classname: "bg-success",
          delay: 4000,
          autohide: true,
          headertext: "Success"
        });
      },
      err => {
        if(err.status === 401){
          this._toastService.show('Sie sind nicht eingeloggt!', {
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
