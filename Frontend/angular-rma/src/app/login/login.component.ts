import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { User } from 'src/User';
import { AuthService } from '../auth.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  users: User[] = [];

  userListLeftStyles: Number[] = [69, 299, 529]; //for showing the users shortcut fields in right column

  closeResult = ''; 

  selectedUser: User = null;


  constructor(private _router:Router,
              private _userService: UserService,
              private _authService: AuthService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this._userService.listUsers().subscribe(
      res => {
        for(let username of res){
          this.users.push(new User(username));
        }
        console.log(res)
      },
      err => console.log(err)
    )
  }

  logIn(): void{
    //login button click event triggered within login popup
    console.log(this.selectedUser.password);
    this._authService.loginUser(this.selectedUser).subscribe(
      res => {
        console.log(res);
        localStorage.setItem('token', res.token);
        this._router.navigate(['/menue']);
      },
      err => console.log("ERROR: 403")
    );
    
  }

  signUp(): void{
    this._router.navigate(['/signup'])
  }

  calculateTopStyleOfUserShortcut(i: number):number{
    return 240+236*Math.floor((i/3));
  }

  open(content, selectedUser) {
    this.selectedUser = selectedUser;
    this.modalService.open(content, 
   {ariaLabelledBy: 'modal-basic-title'}).result.then((result)  => { 
      this.closeResult = `Closed with: ${result}`; 
    }, (reason) => { 
      this.closeResult =  
         `Dismissed ${this.getDismissReason(reason)}`; 
    }); 
  } 
  
  private getDismissReason(reason: any): string { 
    if (reason === ModalDismissReasons.ESC) { 
      return 'by pressing ESC'; 
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) { 
      return 'by clicking on a backdrop'; 
    } else { 
      return `with: ${reason}`; 
    } 
  } 
} 
