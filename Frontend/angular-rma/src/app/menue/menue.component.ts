import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-menue',
  templateUrl: './menue.component.html',
  styleUrls: ['./menue.component.css']
})
export class MenueComponent implements OnInit {

  constructor(public _authService: AuthService) { }

  ngOnInit(): void {
  }

}
