import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { EventCreationComponent } from './event-creation/event-creation.component';
import { EventSelectionComponent } from './event-selection/event-selection.component';
import { LoginComponent } from './login/login.component';
import { MenueComponent } from './menue/menue.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent},
  { 
    path: 'menu', 
    component: MenueComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'event',
    canActivate: [AuthGuard],
    children: [
      {
        path: 'selection',
        component: EventSelectionComponent
      },
      {
        path: 'creation',
        component: EventCreationComponent
      }
    ]
  },
  { path: '',
    redirectTo: '/menu',
    pathMatch: 'full'
  },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes/*,
    { enableTracing: true } // <-- debugging purposes only*/
    )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
