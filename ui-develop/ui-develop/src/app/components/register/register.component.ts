import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService} from 'ngx-cookie-service';
import { catchError } from 'rxjs';
import {Credentials} from 'src/app/models/credentials'
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  tokenCheck: boolean = true;
  usernameFlag: boolean = true;
  passwordFlag: boolean = true;
  uniqueFlag: boolean = true;
  attempts: number = 0;
  constructor(private cookieService: CookieService, private authService: AuthenticationService,
     private router: Router, private userService: UserService) {}
  
  ngOnInit(): void {}

  checkInputUser(){
    this.usernameFlag = true;
    this.uniqueFlag = true;
  }
  checkInputPass(){
    this.passwordFlag = true;
  }
  isUserLogged(){
    if (this.authService.isUserLogged()){
      this.router.navigate([''])
    }
      
      return this.authService.isUserLogged();
  }
  // validCredentials(user: string, pass: string): boolean{
  //   this.cookieService.set('token', '');
  //   console.log(user.length)
  //   if (user.length != 0 && pass.length != 0){
  //       this.authService.getToken(user, pass).subscribe(data=>{this.cookieService.set('token', data.token);
  //       if (this.cookieService.get('token').length==0){
  //         this.router.navigate([''])
  //         //login if the login credentials are new
  //         return true;
  //       }else{
  //         //do not log in if the credentials are taken / the username is taken
  //         return false
  //       }
  //               //error if the token exists- that is, error if username is taken
  //     },error=>{this.tokenCheck = true;});
  //   }else{
  //     if (user.length == 0)
  //       this.usernameFlag = false;
  //     if (pass.length == 0)
  //       this.passwordFlag = false;
  //     return false;
  //   }
  //   return false;

  validCredentials(user: string, pass: string) {
    if(user.length != 0 && pass.length != 0)
    {
    this.authService.saveUser(user, pass).subscribe(response => 
      {
        if(response.status == 200)
        {
         this.saveNewUser(user)
         this.cookieService.set('token', response.body.token)
         this.router.navigate([''])
        }
    }, error => 
    {
      if(error.status == 400)
      {
        this.duplicateUsername(user)
      }
    }
    )
  }
  else
  {
    if(user.length == 0)
    {
      this.usernameFlag = false;
    }
    if(pass.length == 0)
    {
      this.passwordFlag = false;
    }
  }
  }

  saveNewUser(username: string)
  {
    this.userService.saveUser(username).subscribe()
  }

  duplicateUsername(user: string)
  {
    this.uniqueFlag = false;
  }
}