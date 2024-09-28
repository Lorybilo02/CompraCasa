import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Appartament_characteristicsComponent } from './appartament_characteristics.component';

describe('CharacteristicsScreenComponent', () => {
  let component: Appartament_characteristicsComponent;
  let fixture: ComponentFixture<Appartament_characteristicsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Appartament_characteristicsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Appartament_characteristicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
