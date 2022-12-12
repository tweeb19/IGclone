import { TokenType } from '@angular/compiler/src/ml_parser/tokens';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService} from 'ngx-cookie-service';
import { catchError } from 'rxjs';
import {Credentials} from 'src/app/models/credentials'
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  tokenCheck: boolean = true;
  usernameFlag: boolean = true;
  passwordFlag: boolean = true;
  sessionFlag: boolean = false;
  attempts: number = 0;
  constructor(private cookieService: CookieService, private http: AuthenticationService, private router: Router) {}
  
  ngOnInit(): void {
    if(this.cookieService.get("expired") == "true"){
      this.cookieService.delete("expired");
      this.sessionFlag = true;
    }
  }

  checkInputUser(){
    this.usernameFlag = true;
    this.sessionFlag = false;
  }
  checkInputPass(){
    this.passwordFlag = true;
    this.sessionFlag = false;
  }

  registerclick(){
    this.router.navigate(['/register'])
  }

  isUserLogged(){
    if (this.http.isUserLogged()){
      this.router.navigate(['/'])
    }
      
      return this.http.isUserLogged();
  }
  validCredentials(user: string, pass: string): boolean{
    this.sessionFlag = false;
    this.cookieService.set('token', '');
    console.log(user.length)
    if (user.length != 0 && pass.length != 0){
        this.http.getToken(user, pass).subscribe(data=>{this.cookieService.set('token', data.token);
        if (this.cookieService.get('token').length==0){
          return false
        }else{
          this.router.navigate([''])
          return true;
        }
        
      },error=>{this.tokenCheck = false;});
    }else{
      if (user.length == 0)
        this.usernameFlag = false;
      if (pass.length == 0)
        this.passwordFlag = false;
      return false;
    }
    return false;
  }
}
