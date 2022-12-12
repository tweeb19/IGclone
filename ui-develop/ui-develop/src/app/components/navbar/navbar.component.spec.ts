import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CdkOverlayOrigin, OverlayModule } from "@angular/cdk/overlay";
import { NavbarComponent } from './navbar.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
// import { Router } from '@angular/router';
// import { NgModule } from '@angular/core';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OverlayModule, RouterTestingModule, HttpClientModule ],
      declarations: [ NavbarComponent, CdkOverlayOrigin ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should not be null',()=>{
    expect(component.isUserLogged).not.toBe(null);
  })
});
