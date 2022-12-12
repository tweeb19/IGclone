import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {mergeMap, Observable, of} from 'rxjs'
import { environment } from 'src/environments/environment.prod';
import { CookieService } from 'ngx-cookie-service';
import { token } from '../models/token';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  url = environment.authURL;
  constructor(private http: HttpClient, private cookieService : CookieService) { }

  getToken(username: string, password: string): Observable<token>{  
    return this.http.post<token>(`${this.url}/authenticate`, {username, password})
  }

  isUserLogged(): boolean{
    if (this.cookieService.get('token') == '' || this.cookieService.get('token') == null){
      return false;
    }
    else{
      return true;
    }
  }

  validate(username: String, token: String): Observable<boolean>{
    return this.http.get<boolean>(`${this.url}/validate?token=${token}&username=${username}`)
  }

  validate_observable(obs: Observable<any>, username: String, token: String): Observable<any>{
    return this.validate(username, token).pipe(mergeMap(res => {
      if(!res){
        return of(false);
      }
      return obs;
    }))
  }

  saveUser(username: string, password: string): Observable<any>{
    return this.http.post<any>(`${this.url}/user`, {username, password}, {observe: 'response'})
  }
}
