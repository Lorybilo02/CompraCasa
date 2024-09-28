import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyScreenComponent } from './buy-screen.component';

describe('BuyScreenComponent', () => {
  let component: BuyScreenComponent;
  let fixture: ComponentFixture<BuyScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuyScreenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BuyScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
