import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { Comments } from 'src/app/models/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  url = environment.serverURL;

  constructor(private http: HttpClient) { }

  addComment(comment: Comments, postId: number): Observable<Comments>
  {
    console.log(comment);
    return this.http.post<Comments>(`${this.url}/posts/comments?postId=${postId}`, comment)
  }
}
