import { TestBed } from '@angular/core/testing';
import { HttpClient } from "@angular/common/http"

import { PostService } from './post.service';
import { PageOfPosts } from '../models/page-of-posts';
import { Post } from '../models/post';
import { of } from 'rxjs';
import { Comments } from '../models/comment';

describe('PostService', () => {
  let service: PostService;
  let http: jasmine.SpyObj<HttpClient>;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    http = jasmine.createSpyObj('HttpClient', ['get']);
    service = new PostService(http);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return expected posts(called once)', (done: DoneFn) => {
    const expectedPosts: PageOfPosts<Post> = { items: [], hasNext: false, totalElements: 0 };
    http.get.and.returnValue(of(expectedPosts));
    service.getPosts(0).subscribe({
      next: posts => {
        expect(posts)
        .withContext("expected posts")
        .toEqual(expectedPosts);
        done();
      },
      error: done.fail
    });
    expect(http.get.calls.count())
    .withContext('one call')
    .toBe(1);
  })

  it('should return expected comments(called once)', (done: DoneFn) => {
    const expectedComments: PageOfPosts<Comments> = { items: [], hasNext: false, totalElements: 0 };
    http.get.and.returnValue(of(expectedComments));
    service.getComments(0,0).subscribe({
      next: comments => {
        expect(comments)
        .withContext("expected posts")
        .toEqual(expectedComments);
        done();
      },
      error: done.fail
    });
    expect(http.get.calls.count())
    .withContext('one call')
    .toBe(1);
  })
});