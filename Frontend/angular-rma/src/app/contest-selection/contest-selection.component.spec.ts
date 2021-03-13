import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContestSelectionComponent } from './contest-selection.component';

describe('ContestSelectionComponent', () => {
  let component: ContestSelectionComponent;
  let fixture: ComponentFixture<ContestSelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContestSelectionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContestSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
