import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VolsComponent } from './vols.component';

describe('VolsComponent', () => {
  let component: VolsComponent;
  let fixture: ComponentFixture<VolsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VolsComponent]
    });
    fixture = TestBed.createComponent(VolsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
