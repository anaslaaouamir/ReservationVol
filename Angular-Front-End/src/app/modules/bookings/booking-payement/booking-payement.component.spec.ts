import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingDetailComponent } from './booking-payement.component';

describe('BookingDetailComponent', () => {
  let component: BookingDetailComponent;
  let fixture: ComponentFixture<BookingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookingDetailComponent]
    });
    fixture = TestBed.createComponent(BookingDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
