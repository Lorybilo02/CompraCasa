import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyannouncementsComponent } from './myannouncements.component';

describe('MyannouncementsComponent', () => {
  let component: MyannouncementsComponent;
  let fixture: ComponentFixture<MyannouncementsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyannouncementsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MyannouncementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
