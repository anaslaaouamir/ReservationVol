import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketMessagesComponent } from './ticket-messages.component';

describe('TicketMessagesComponent', () => {
  let component: TicketMessagesComponent;
  let fixture: ComponentFixture<TicketMessagesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketMessagesComponent]
    });
    fixture = TestBed.createComponent(TicketMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
