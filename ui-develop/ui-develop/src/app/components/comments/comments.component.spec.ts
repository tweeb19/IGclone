import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { Comments } from 'src/app/models/comment';
import { PageOfPosts } from 'src/app/models/page-of-posts';
import { PostService } from 'src/app/services/post.service';


import { CommentsComponent } from './comments.component';

class MockPostService{
  getPosts(value: any){
    return of(new PageOfPosts())
  }

  getComments(value: any, l: any){
    return of(new PageOfPosts())
  }
}

describe('CommentsComponent', () => {
  let component: CommentsComponent;
  let fixture: ComponentFixture<CommentsComponent>;
  let service: PostService;

  // beforeEach(async () => {
  //   await TestBed.configureTestingModule({
  //     declarations: [ CommentsComponent ],
  //     providers: [{provide: PostService, useClass: MockPostService}]
  //   })
  //   .compileComponents();
  // });

  // beforeEach(() => {
  //   fixture = TestBed.createComponent(CommentsComponent);
  //   component = fixture.componentInstance;
  //   service = TestBed.inject(PostService)
  //   fixture.detectChanges();
  // });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });

  // it('should call get comments once', () => {
  //   let comments = new PageOfPosts<Comments>()
  //   spyOn(service, 'getComments').and.returnValue(of(comments));
  //   component.ngOnInit()
  //   expect(service.getComments).toHaveBeenCalled();
  // });

  // it('should update service data', () => {
  //   let comments = new PageOfPosts<Comments>()
  //   spyOn(service, 'getComments').and.returnValue(of(comments));
  //   component.ngOnInit();
  //   expect(component.comments).toBe(comments)
  // })

  // it('should have have pages as zero-indexed', ()=> {
  //   expect(component.page).toBe(0);
  // })

  // it('should increment page on view more comments', () => {
  //   let comments = new PageOfPosts<Comments>()
  //   spyOn(service, 'getComments').and.returnValue(of(comments));
  //   component.showCommentFunc();
  //   expect(component.page).toBe(1);
  //   component.showCommentFunc();
  //   expect(component.page).toBe(2);

  // })

  // it('should append to the list on view more comments', () => {
  //   let comments = new PageOfPosts<Comments>()
  //   let comment: Comments = {id: 1, postId: 1, username: "test", body: "test", createdOn: new Date()};
  //   comments.items = [comment];
  //   let spy = spyOn(service, 'getComments');
  //   spy.and.returnValue(of(comments))
  //   component.ngOnInit();
  //   let comments2 = new PageOfPosts<Comments>()
  //   let comment2: Comments = {id: 2, postId: 2, username: "Test2", body: "test2", createdOn: new Date()};
  //   comments2.items = [comment2];
  //   spy.and.returnValue(of(comments2));
  //   component.showCommentFunc();
  //   expect(component.comments.items.length).toBe(2);
  //   expect(component.comments.items[0].id).toBe(1);
  //   expect(component.comments.items[1].id).toBe(2);



  // })
  
});
