import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RunnerCreationComponent } from './runner-creation.component';

describe('RunnerCreationComponent', () => {
  let component: RunnerCreationComponent;
  let fixture: ComponentFixture<RunnerCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RunnerCreationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RunnerCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
