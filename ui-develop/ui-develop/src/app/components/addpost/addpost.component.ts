import { Component, OnInit } from '@angular/core';
import { CookieService} from 'ngx-cookie-service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';
import { Post } from 'src/app/models/post';
import { PostService } from 'src/app/services/post.service'

import { PageOfPosts } from 'src/app/models/page-of-posts';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserService } from 'src/app/services/user.service';
import { mergeMap, of } from 'rxjs';




@Component({
  selector: 'app-addpost',
  templateUrl: './addpost.component.html',
  styleUrls: ['./addpost.component.css']
})
export class AddpostComponent implements OnInit {

  uploading: boolean = false;

  constructor(private cookieService: CookieService, 
    private http: AuthenticationService, 
    private router: Router, private postService: PostService, 
    private jwtHelperService: JwtHelperService, private userService: UserService) {
      
      this.userService.getUserByName(this.jwtHelperService.decodeToken(this.cookieService.get("token")).sub).subscribe(data => {
        this.uploadForm.user = data;
        console.log(data);
  
        // return this.postService.addPost(this.uploadForm).subscribe();
      });
      
    }

    message = ""

  uploadForm: Post = {
    id:1,
    user: {
      id: 1,
      username: 'test',
      profileImg: 'http://notanimage.com/'
    },
    img: '',
    description: '',
    createdOn: new Date(),
    comment: new PageOfPosts<Comment>()
  }

  ngOnInit(): void {
  }

  isUserLogged(){
    if (this.http.isUserLogged()){
      this.router.navigate(['/'])
    }
      
      return this.http.isUserLogged();
  }

  createPost(event:any){
    console.log(event);
    console.log(this.uploadForm);
  }

  onFileSelected(event: any) {
    this.message = "";
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.uploadForm.img = reader.result as string;
      console.log(this.uploadForm.img);
      console.log(file)
      this.message = file.name;
    }; 
  }

  onSubmit() {
    if(this.uploading == false)
    {
      this.uploading = true;
    console.log(this.uploadForm);

    this.http.validate_observable(
      this.postService.addPost(this.uploadForm),
      this.uploadForm.user.username, 
      this.cookieService.get("token")).subscribe(data => {
        if(typeof data === "boolean")
        {
          this.cookieService.set("expired", "true");
          this.cookieService.delete('token');
          this.router.navigate(['/login'])
        }
        else{
          console.log(data);
          this.router.navigate(["/"]);
        }
      });
    }
  }
}
