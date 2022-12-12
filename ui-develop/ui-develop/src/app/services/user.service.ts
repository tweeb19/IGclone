import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = environment.serverURL

  constructor(private http: HttpClient) { }

  saveUser(username: string)
  {
    let userId = 0
    return this.http.post<User>(`${this.url}/register?id=${userId}&username=${username}`, {})
  }

  getUserByName(username: string)
  {
    return this.http.get<User>(`${this.url}/users?username=${username}`)
  }

}
