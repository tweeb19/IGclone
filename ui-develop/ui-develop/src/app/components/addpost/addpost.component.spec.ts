import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { CookieService } from 'ngx-cookie-service';
import { of } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { NavbarComponent } from '../navbar/navbar.component';
import { FormsModule } from '@angular/forms';


import { AddpostComponent } from './addpost.component';
import { CdkOverlayOrigin } from '@angular/cdk/overlay';

class MockCookieService{
  get(value: any){
    return {"sub": " "}
  }
}

class MockJwtService{
    decodeToken(value: any){
      return {"sub": " "}
    }
  }

class MockUserService{
  getUserByName(value: any){
    return of(
      {
        id: 1,
        username: 'test',
        profileImg: 'http://notanimage.com/'
     })
  }
}


describe('AddpostComponent', () => {
  let component: AddpostComponent;
  let fixture: ComponentFixture<AddpostComponent>;
  let cookieService: CookieService;
  let userService: UserService;
  let jwtService: JwtHelperService;
  

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddpostComponent, NavbarComponent, CdkOverlayOrigin ],
      imports: [HttpClientTestingModule, RouterTestingModule, RouterModule, FormsModule],
      providers: [ {provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
      {provide: CookieService, useClass: MockCookieService},
      {provide: JwtHelperService, useClass: MockJwtService},
      {provide: UserService, useClass: MockUserService} ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddpostComponent);
    cookieService = TestBed.inject(CookieService);
    userService = TestBed.inject(UserService);
    jwtService = TestBed.inject(JwtHelperService)
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
