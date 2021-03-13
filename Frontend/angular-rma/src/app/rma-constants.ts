import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })
export class RmaConstants {
  private _backEndMainRoute: string = "http://localhost:8080/RMA/rma";
  
  RmaConstants(){

  }

  get backEndMainRoute():string{
      return this._backEndMainRoute;
  }
}