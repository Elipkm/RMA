import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { User } from 'src/User';
import { AuthService } from '../auth.service';
import { ToastService } from '../toast.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  users: User[] = [];

  designOrientedUserList: User[][] = []; //normal userlist split into thirs for ngFor-directive in template

  userListLeftStyles: Number[] = [69, 299, 529]; //for showing the users shortcut fields in right column

  closeResult = ''; 

  selectedUser: User = null;


  constructor(private _router:Router,
              private _userService: UserService,
              private _authService: AuthService,
              private modalService: NgbModal,
              private _toastService: ToastService) { }

  ngOnInit(): void {
    this._userService.listUsers().subscribe(
      res => {
        for(let username of res){
          this.users.push(new User(username));
        }
        this.createDesignOrietendUserList();
      },
      err => {
        console.log(err);
        this.createDesignOrietendUserList();
      }
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
      err => {
        if(err.status === 403){
          this._toastService.show('Das Passwort ist falsch!', {
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
        
      }
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

  createDesignOrietendUserList(): void{
    let row = 0;
    this.designOrientedUserList[row] = [];
    this.designOrientedUserList[row][0] = null;
    
    for(let i = 0; i<this.users.length;i++){
      if((i+1) % 3 === 0 && i!=0){
        row++;
      }

      if(this.designOrientedUserList[row]==undefined){
        this.designOrientedUserList[row] = [];
      }
      this.designOrientedUserList[row][(i+1)%3] = this.users[i];
    }
  }
} 
