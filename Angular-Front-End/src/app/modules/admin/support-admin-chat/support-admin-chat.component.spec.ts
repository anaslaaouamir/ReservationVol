import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupportAdminChatComponent } from './support-admin-chat.component';

describe('SupportAdminChatComponent', () => {
  let component: SupportAdminChatComponent;
  let fixture: ComponentFixture<SupportAdminChatComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SupportAdminChatComponent]
    });
    fixture = TestBed.createComponent(SupportAdminChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
