import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';

import { ShipFormComponent } from './ship-form.component';

describe('ShipFormComponent', () => {
  let component: ShipFormComponent;
  let fixture: ComponentFixture<ShipFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShipFormComponent],
      imports: [HttpClientModule, MatSnackBarModule, FormsModule, ReactiveFormsModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShipFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
