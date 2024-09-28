import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapSelectorHomeComponent } from './map-selector-home.component';

describe('MapSelectorHomeComponent', () => {
  let component: MapSelectorHomeComponent;
  let fixture: ComponentFixture<MapSelectorHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapSelectorHomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapSelectorHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
