import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublishesScreenComponent } from './publishes-screen.component';

describe('PublishesScreenComponent', () => {
  let component: PublishesScreenComponent;
  let fixture: ComponentFixture<PublishesScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PublishesScreenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PublishesScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
