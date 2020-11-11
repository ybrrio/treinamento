import { Injectable } from '@angular/core';
import { $ } from 'protractor';
import { Conta } from '../models/conta.model';
import { Transferencia } from '../models/transferencia.model';
import { ApiService } from './api.service';

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

  transferir(obj:Transferencia){
    return this.apiService.post( `${this.controller}/transferir`, obj);
  }

}
