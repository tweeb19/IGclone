import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http"
import { Observable } from 'rxjs';
import { Post } from '../models/post';
import { PageOfPosts } from '../models/page-of-posts';
import { environment } from 'src/environments/environment.prod';
import {Comments} from 'src/app/models/comment';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  //update for production url when added.
  url = environment.serverURL;

  constructor(private http: HttpClient) { 
  }
  
  getPosts(pageNumber: Number): Observable<PageOfPosts<Post>>{    
    return this.http.get<PageOfPosts<Post>>(`${this.url}/posts?pageNumber=${pageNumber}&pageSize=5`)
  }

  addPost(post: Post) {
    return this.http.post(`${this.url}/posts`, post);
  }

  getComments(postId: Number, pageNumber: Number): Observable<PageOfPosts<Comments>>{   

    return this.http.get<PageOfPosts<Comments>>(`${this.url}/posts/${postId}/comments?pageNumber=${pageNumber}&pageSize=5`)
  }
}
