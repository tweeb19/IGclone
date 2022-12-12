import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import {MatCardModule} from '@angular/material/card';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { PostComponent } from './components/post/post.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { CommentsComponent } from './components/comments/comments.component';
import { LoginComponent } from './components/login/login.component';
import { CookieService } from 'ngx-cookie-service';
import { FormsModule } from '@angular/forms';
import { OverlayModule } from "@angular/cdk/overlay";
import { RegisterComponent } from './components/register/register.component';
import { JwtHelperService, JWT_OPTIONS } from "@auth0/angular-jwt";
import { AddpostComponent } from './components/addpost/addpost.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PostComponent,
    NavbarComponent,
    CommentsComponent,
    LoginComponent,
    RegisterComponent,
    AddpostComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCardModule,
    InfiniteScrollModule,
    FormsModule,
    OverlayModule
  ],
  providers: [CookieService,
  {provide: JWT_OPTIONS, useValue: JWT_OPTIONS }, JwtHelperService],
  bootstrap: [AppComponent]
})
export class AppModule { }
