import { Conta } from './../models/conta.model';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { Transferencia } from '../models/transferencia.model';
import { ApiService } from './api.service';
import { Saldo } from '../models/saldo.model';

@Injectable({
  providedIn: 'root'
})
export class ContaService {

  private controller = 'contas';

  constructor(private apiService: ApiService) { }

  depositar(obj: Conta) {
    return this.apiService.post(`${this.controller}/deposito`, obj);
  }

  sacar(obj: Conta) {
    return this.apiService.post(`${this.controller}/saque`, obj);
  }

  transferencia(obj: Transferencia){
    return this.apiService.post(`${this.controller}/transferencia`, obj);
  }

  saldo(obj: Saldo): Observable<any> {
    return this.apiService.get(`${this.controller}/consultar-saldo/${obj.agencia}/${obj.numeroConta}`);
  }

}
