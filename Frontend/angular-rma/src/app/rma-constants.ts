import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })
export class RmaConstants {
  private _backEndMainRoute: string = "http://localhost:8080/rma_restful/rma";
  //TODO TMS: http://localhost:8080/rma_tms/rma
  private _tokenExpiryTimeSeconds: number = 7200; //2h
  
  RmaConstants(){

  }

  get backEndMainRoute():string{
      return this._backEndMainRoute;
  }

  get tokenExpiryTimeSeconds(): number{
    return this._tokenExpiryTimeSeconds;
  }
}