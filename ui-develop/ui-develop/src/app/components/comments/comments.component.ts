import { Component, Input, OnInit } from '@angular/core';
import { PageOfPosts } from 'src/app/models/page-of-posts';
import { PostService } from 'src/app/services/post.service';
import {Comments} from 'src/app/models/comment';
import { CommentService } from 'src/app/services/comment.service';
import { CookieService } from 'ngx-cookie-service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';
import { mergeMap, of } from 'rxjs';



@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  @Input() postId = 1;
  @Input() increment = (postId:number) =>{}

  comments: PageOfPosts<Comments> = {items: [{
    "postId": 2,
    "username": "cgonzalez",
    "body": "The movie was better than I could have ever hoped for!",
    "createdOn": new Date(),
    "id": 1
  },
  {
    "postId": 1,
    "username": "npradeep",
    "body": "I know! I can't wait to see it!",
    "createdOn": new Date(),
    "id": 1
  },
  {
    "postId": 1,
    "username": "npradeep",
    "body": "I know! I can't wait to see it!",
    "createdOn": new Date(),
    "id": 1
  },
  {
    "postId": 1,
    "username": "cgonzalez",
    "body": "I know! I can't wait to see it!",
    "createdOn": new Date(),
    "id": 1
  },
  {
    "postId": 1,
    "username": "mlopez",
    "body": "I know! I can't wait to see it!",
    "createdOn": new Date(),
    "id": 1
  },
  {
    "postId": 1,
    "username": "dsilver",
    "body": "I know! I can't wait to see it!",
    "createdOn": new Date(),
    "id": 1
  }],
  hasNext: false,
  totalElements: 5
}

likes = [4, 10, 15, 17, 7, 6];

  showComment = false;
  page = 0;


  mores: boolean[] = []

  uploadComment: Comments = {
    id: 1,
    postId:1,
    username: "",
    body: "",
    createdOn: new Date()
  }

  constructor(private postService: PostService, 
    private commentService: CommentService,
    private cookieService: CookieService,
    private jwtHelperService: JwtHelperService,
    private http:AuthenticationService,
    private router: Router){
  }

  ngOnInit(): void {
    this.postService.getComments(this.postId, this.page).subscribe(data => {
      this.comments = data;
      this.showComment = data.hasNext;
      this.mores = [false, false, false,false, false];
    })
    console.log(this.postId)

  }

  showCommentFunc(){
    this.postService.getComments(this.postId, ++this.page).subscribe(data => { 
      console.log(this.page)
      this.comments.items = this.comments.items.concat(data.items);
      console.log(this.comments.items);      
      this.mores = this.mores.concat([false, false,false, false, false])
      this.showComment = data.hasNext;
    })    
  }

  showMore(i: any){
    this.mores[i] = true;
  }

  showAll(i: any) : Boolean{
    return this.mores[i];
  }

  isUserLogged(){
     return this.http.isUserLogged();
  }

  onSubmit(){
    this.uploadComment.username = this.jwtHelperService.decodeToken(this.cookieService.get("token")).sub
    this.uploadComment.postId = this.postId;
    console.log(this.uploadComment);
    
    this.http.validate_observable(this.commentService.addComment(this.uploadComment, this.uploadComment.postId), 
    this.uploadComment.username, 
    this.cookieService.get("token")).subscribe(data => {
        if(typeof data === "boolean")
        {
          this.cookieService.delete('token');
          this.cookieService.set("expired", "true");
          this.router.navigate(['/login'])
        }
        else{
          this.uploadComment.body = "";
          this.increment(this.postId);
          if((this.comments.items.length % 5==0 && this.comments.items.length >= 5) || this.showComment){  
            this.showComment = true;
            this.comments.items = [data].concat(this.comments.items).slice(0,this.comments.items.length)
            this.mores = [false].concat(this.mores).slice(0, this.mores.length)  
          } else {
            this.comments.items = [data].concat(this.comments.items)
            this.mores = [false].concat(this.mores)
          }
        }
      });
  }
}

