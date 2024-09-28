import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Villa_characteristicsComponent } from './villa_characteristics.component';

describe('VillaCharacteristicsComponent', () => {
  let component: Villa_characteristicsComponent;
  let fixture: ComponentFixture<Villa_characteristicsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Villa_characteristicsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Villa_characteristicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
