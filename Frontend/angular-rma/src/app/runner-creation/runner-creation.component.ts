import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/User';
import { AuthService } from '../auth.service';
import { ToastService } from '../toast.service';

@Component({
  selector: 'app-runner-creation',
  templateUrl: './runner-creation.component.html',
  styleUrls: ['./runner-creation.component.css']
})
export class RunnerCreationComponent implements OnInit {
  user: User;
  formdata: FormGroup;

  constructor(private _authService: AuthService,
              private _toastService: ToastService) { }

  ngOnInit(): void {
    this.user = this._authService.getLoggedInUser();
    this.formdata = new FormGroup({
      name: new FormControl("", Validators.required),
      groups: new FormControl("", Validators.required)
   });
  }

  logOut(): void{

  }

  createRunner(): void{
    if(!this.formdata.valid){
      this._toastService.show("Bitte füllen Sie alle Pflichtfelder aus.", {
        classname: "bg-danger text-light",
        delay: 4000,
        autohide: true,
        headertext: "Error"
      });
    }else{
      let value = this.formdata.value;
      console.log(value);
      //call to backend via the runner-service
    }
  }

}
