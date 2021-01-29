export class User {

    password: string;

    constructor(public username:string) { }

    getUserName(): string{
        return this.username;
    }

    setUserName(username: string): void{
        this.username = username;
    }
  
  }