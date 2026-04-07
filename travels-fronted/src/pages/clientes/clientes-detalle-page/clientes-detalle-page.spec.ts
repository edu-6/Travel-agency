import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientesDetallePage } from './clientes-detalle-page';

describe('ClientesDetallePage', () => {
  let component: ClientesDetallePage;
  let fixture: ComponentFixture<ClientesDetallePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientesDetallePage],
    }).compileComponents();

    fixture = TestBed.createComponent(ClientesDetallePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
