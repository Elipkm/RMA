import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventEditingComponent } from './event-editing.component';

describe('EventEditingComponent', () => {
  let component: EventEditingComponent;
  let fixture: ComponentFixture<EventEditingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EventEditingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EventEditingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
