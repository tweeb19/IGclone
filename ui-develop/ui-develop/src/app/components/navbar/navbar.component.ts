import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'; 
import { CookieService } from 'ngx-cookie-service';
import { Credentials } from 'src/app/models/credentials';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private router: Router, private authService: AuthenticationService,
     private cookieService: CookieService) { }

  ngOnInit(): void {
  }
  isUserLogged():boolean{
    return this.authService.isUserLogged();
  }
  isOpen: boolean = false;

  onLoginPage() {
    return this.router.url === '/login' || '/addpost';
  }

  onLoginTwoPage() {
    return this.router.url === '/login';
  }

  logout(){
    this.cookieService.delete('token');
    return this.router.url === ""
  }
  
 

}
