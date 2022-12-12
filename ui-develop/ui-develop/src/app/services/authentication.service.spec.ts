import { TestBed } from '@angular/core/testing';
import { AuthenticationService } from './authentication.service';
import {of} from 'rxjs'


describe('AuthenticationService', () => {
  let service: AuthenticationService;
  let http: jasmine.SpyObj<any>;
  let cookieService: jasmine.SpyObj<any>

  beforeEach(() => {
    TestBed.configureTestingModule({});
    http = jasmine.createSpyObj('HttpClient', ['post']);
    service = new AuthenticationService(http, cookieService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should return Token', (done: DoneFn) => {
    const expectedToken: any = '';
    http.post.and.returnValue(of(expectedToken));
    service.getToken("user1", "pass1").subscribe({
      next: string => {
        expect(string)
        .withContext("expected posts")
        .toBe(expectedToken);
        done();
      },
      error: done.fail
    });
    expect(http.post.calls.count())
    .withContext('one call')
    .toBe(1);
  })
  });
