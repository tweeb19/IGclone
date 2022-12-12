import { Component, OnInit } from '@angular/core';
import { PageOfPosts } from 'src/app/models/page-of-posts';
import { Post } from 'src/app/models/post';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  distance=0.01;
  throttle=150;
  page=0;
  index =0;
  // names = [{username:"Alice",image:""},
  // {username:"Bob",image:"assets/images/gupta.jpg"},
  // {username:"Carol",image:"assets/images/star-wars-9-poster.jpg"},]
  permanentData = new PageOfPosts<Post>();
  serviceData = new PageOfPosts<Post>();

  mores: boolean[] = []
   
  
  constructor(private postService: PostService) { }

  ngOnInit(): void {
    window.onbeforeunload = function () {
      window.scrollTo(0, 0);
    }

    this.postService.getPosts(this.page).subscribe(data=>{
      this.serviceData = data;
      console.log(this.serviceData.items);
    })    
  }
  deletePost(){
    return false;
  }
  // changeUser(index:any){
    
  //   return this.names[index%3];
  // }

  onScroll():void{
    this.postService.getPosts(++this.page).subscribe(data=>{
     this.serviceData.items= this.serviceData.items.concat(data.items);
    });       
  }

  showMore(i: any){
    this.mores[i] = true;
  }

  showAll(i: any) : Boolean{
    return this.mores[i];
  }

  increment = (postId:number) => {
    for(let post of this.serviceData.items){
      if(post.id == postId){
        post.comment.totalElements++;
        console.log(post.comment.totalElements);
      }
    }
  }

}
