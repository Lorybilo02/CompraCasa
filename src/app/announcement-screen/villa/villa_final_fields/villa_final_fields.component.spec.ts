import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Villa_final_fieldsComponent } from './villa_final_fields.component';

describe('VillaFinalFieldsComponent', () => {
  let component: Villa_final_fieldsComponent;
  let fixture: ComponentFixture<Villa_final_fieldsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Villa_final_fieldsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Villa_final_fieldsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
