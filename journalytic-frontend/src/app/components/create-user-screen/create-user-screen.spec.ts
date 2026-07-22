import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUserScreen } from './create-user-screen';

describe('CreateUserScreen', () => {
  let component: CreateUserScreen;
  let fixture: ComponentFixture<CreateUserScreen>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateUserScreen],
    }).compileComponents();

    fixture = TestBed.createComponent(CreateUserScreen);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
