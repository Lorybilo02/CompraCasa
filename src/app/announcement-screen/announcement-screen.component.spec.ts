import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnouncementScreenComponent } from './announcement-screen.component';

describe('AnnouncementScreenComponent', () => {
  let component: AnnouncementScreenComponent;
  let fixture: ComponentFixture<AnnouncementScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnouncementScreenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnnouncementScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
