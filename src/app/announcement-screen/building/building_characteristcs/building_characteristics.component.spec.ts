import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Building_characteristicsComponent } from './building_characteristics.component';

describe('BuildingComponent', () => {
  let component: Building_characteristicsComponent;
  let fixture: ComponentFixture<Building_characteristicsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Building_characteristicsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Building_characteristicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
