import { StringDecoder } from "string_decoder";

export class User {

    password: string;

    constructor(private username:string) { }

    getUserName(): string{
        return this.username;
    }

    setUserName(username: string): void{
        this.username = username;
    }
  
  }