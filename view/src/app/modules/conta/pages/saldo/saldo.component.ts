import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FormBase } from 'src/app/core/classes/form-base';
import { Saldo } from 'src/app/core/models/saldo.model';
import { ContaService } from 'src/app/core/services/conta.service';
import { SweetalertCustom } from 'src/app/shared/utils/sweetalert-custom';

@Component({
  selector: 'app-saldo',
  templateUrl: './saldo.component.html',
  styleUrls: ['./saldo.component.scss']
})
export class SaldoComponent extends FormBase implements OnInit, AfterViewInit {
  form: any;
  saldo: number;

  constructor(
    private formBuilder: FormBuilder,
    private contaService: ContaService,
    public router: Router
  ) {
    super();
  }

  ngOnInit() {
    this.createFormGroup();
    this.validateMensageError();
  }

  createFormGroup(){
    this.form = this.formBuilder.group({
      agencia:      ['', Validators.required],
      numeroConta:      ['', Validators.required],
      
    });
  }

  /**
   * Seta a mensagem de validação que irá ser exibida ao usuário
   */
  validateMensageError(){
    this.createValidateFieldMessage({
      agencia: {
        required: 'Agência de Destino Obrigatória.',
      },
      numeroConta: {
        required: 'Número da Conta Destino Obrigatório.',
      },
    });
  }

  onSubmit(){
    if (this.form.valid) {
      const conta = new Saldo(this.form.value);
      this.contaService.consultarSaldo(conta).subscribe(
        response => {
          this.saldo = response.body
          SweetalertCustom.showAlertConfirm(`Seu saldo é: R$ ${this.saldo},00`, {type: 'success'}).then(
            result => {
              if (result.dismiss) {
                this.router.navigate(['conta/operacoes']);
              }
            }
          );
        },
        erro => {
          if (erro.error.detalhes) {
            SweetalertCustom.showAlertConfirm(erro.error.detalhes[0], { type: 'error' });
          } else {
            SweetalertCustom.showAlertConfirm('Falha na operação.', { type: 'error' });
          }
        }
      );
    }
  }
      



}