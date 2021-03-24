import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Event } from '../../Event';
import { EventService } from '../event.service';
import { ToastService } from '../toast.service';

@Component({
  selector: 'app-event-editing',
  templateUrl: './event-editing.component.html',
  styleUrls: ['./event-editing.component.css']
})
export class EventEditingComponent implements OnInit {
  event: Event;
  formdata: FormGroup;

  constructor(private _router: Router, private _eventService: EventService,
    private _toastService: ToastService) {
    //event which is passed from the EventSelectionComponent
    let state = this._router.getCurrentNavigation().extras.state;
    if (state) {
      this.event = Event.eventOfObject(state);
    }

    this.formdata = new FormGroup({
      name: new FormControl(this.event.Name, Validators.required),
      startDate: new FormControl(this.event.StartDate, Validators.required),
      endDate: new FormControl(this.event.EndDate),
    });
  }

  ngOnInit(): void {
  }

  deleteEvent(): void {
    this._eventService.deleteEvent(this.event).subscribe(
      res => {
        this._toastService.show("Das Event wurde erfolgreich gelöscht.", {
          classname: "bg-success",
          delay: 4000,
          autohide: true,
          headertext: "Success"
        });
        this._router.navigate(['/event/selection']);
      },
      err => {
        if (err.status === 404) {
          this._toastService.show("Es wurde kein Event mit dieser ID gefunden.", {
            classname: "bg-warning",
            delay: 4000,
            autohide: true,
            headertext: "Warning"
          });
        } else {
          this._toastService.show("Es ist ein unbekannter Fehler aufgetreten.", {
            classname: "bg-danger text-light",
            delay: 4000,
            autohide: true,
            headertext: "Error"
          });
        }
      }
    )
  }

  saveChanges(): void {
    if (!this.formdata.valid) {
      this._toastService.show("Bitte füllen Sie alle Pflichtfelder aus.", {
        classname: "bg-danger text-light",
        delay: 4000,
        autohide: true,
        headertext: "Error"
      });
    } else {
      let value = this.formdata.value;
      if(value.endDate){
        this.event = new Event(value.name, value.startDate, value.endDate, this.event.ID)
      }else{
        let id: any = this.event.ID;
        this.event = new Event(value.name, value.startDate);
        this.event.ID = id;
      }
      this._eventService.updateEvent(this.event).subscribe(
        res => {
          this._toastService.show("Die Änderungen wurden gespeichert.", {
            classname: "bg-success",
            delay: 4000,
            autohide: true,
            headertext: "Success"
          });
        },
        err => {
          if (err.status === 404) {
            this._toastService.show("Es wurde kein Event mit dieser ID gefunden.", {
              classname: "bg-warning",
              delay: 4000,
              autohide: true,
              headertext: "Warning"
            });
          } else {
            this._toastService.show("Es ist ein unbekannter Fehler aufgetreten.", {
              classname: "bg-danger text-light",
              delay: 4000,
              autohide: true,
              headertext: "Error"
            });
          }
        }
      );
    }
  }

}
