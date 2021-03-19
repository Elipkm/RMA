import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { MenueComponent } from './menue/menue.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AuthService } from './auth.service';
import { AuthGuard } from './auth.guard';
import { TokenInterceptorService } from './token-interceptor.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from './user.service';
import { ToastComponent } from './toast/toast.component';
import { LoadingScreenComponent } from './loading-screen/loading-screen.component';
import { LoadingScreenInterceptor } from './loading-screen.interceptor';
import { EventSelectionComponent } from './event-selection/event-selection.component';
import { EventCreationComponent } from './event-creation/event-creation.component';
import { ReactiveFormsModule } from '@angular/forms';
import {JwtHelperService} from '@auth0/angular-jwt';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    MenueComponent,
    PageNotFoundComponent,
    ToastComponent,
    LoadingScreenComponent,
    EventSelectionComponent,
    EventCreationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule
  ],
  providers: [
    AuthService,
    UserService,
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadingScreenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
