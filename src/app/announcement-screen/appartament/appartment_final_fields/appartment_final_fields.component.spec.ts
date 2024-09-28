import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Appartment_final_fieldsComponent } from './appartment_final_fields.component';

describe('AppartmentFinalFieldsComponent', () => {
  let component: Appartment_final_fieldsComponent;
  let fixture: ComponentFixture<Appartment_final_fieldsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Appartment_final_fieldsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Appartment_final_fieldsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
