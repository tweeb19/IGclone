import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { PageOfPosts } from 'src/app/models/page-of-posts';
import { PostService } from 'src/app/services/post.service';
import { Post } from 'src/app/models/post';


import { PostComponent } from './post.component';
import { User } from 'src/app/models/user';

class MockPostService{
  getPosts(value: any){
    return of(new PageOfPosts())
  }
}

describe('PostComponent', () => {
  let component: PostComponent;
  let fixture: ComponentFixture<PostComponent>;
  let service: PostService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostComponent ],
      providers: [{provide: PostService, useClass: MockPostService}]
    })
    .compileComponents();
    
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(PostService)
    // fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call get posts once', () => {
    let posts = new PageOfPosts<Post>()
    spyOn(service, 'getPosts').and.returnValue(of(posts));
    component.ngOnInit()
    expect(service.getPosts).toHaveBeenCalled();
  });

  it('should update service data', () => {
    let posts = new PageOfPosts<Post>()
    spyOn(service, 'getPosts').and.returnValue(of(posts));
    component.ngOnInit();
    expect(component.serviceData).toBe(posts)
  })

  it('should have have pages as zero-indexed', ()=> {
    expect(component.page).toBe(0);
  })

  it('should increment page on scroll', () => {
    let posts = new PageOfPosts<Post>()
    spyOn(service, 'getPosts').and.returnValue(of(posts));
    component.onScroll();
    expect(component.page).toBe(1);
    component.onScroll();
    expect(component.page).toBe(2);

  })

  it('should append to the list on scroll', () => {
    let user1: User = {id: 1, username: "test1", profileImg: "test1"};
    let posts = new PageOfPosts<Post>()
    let post: Post = {id: 1, user: user1, description: "Test", img: "test", comment: new PageOfPosts<Comment>(), createdOn: new Date()};
    posts.items = [post];
    let spy = spyOn(service, 'getPosts');
    spy.and.returnValue(of(posts))
    component.ngOnInit();
    let user2: User = {id: 2, username: "test2", profileImg: "test2"};
    let posts2 = new PageOfPosts<Post>()
    let post2: Post = {id: 2, user: user2, description: "Test2", img: "test2", comment: new PageOfPosts<Comment>(), createdOn: new Date()};
    posts2.items = [post2];
    spy.and.returnValue(of(posts2));
    component.onScroll();
    expect(component.serviceData.items.length).toBe(2);
    expect(component.serviceData.items[0].user).toBe(user1);
    expect(component.serviceData.items[1].user).toBe(user2);



  })

  
  
});
