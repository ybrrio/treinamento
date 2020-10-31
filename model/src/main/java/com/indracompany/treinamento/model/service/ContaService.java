package com.indracompany.treinamento.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.entity.Conta;
import com.indracompany.treinamento.model.entity.OperacaoEnum;
import com.indracompany.treinamento.model.repository.ContaRepository;

@Service
public class ContaService extends GenericCrudService<Conta, Long, ContaRepository>{

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ExtratoService extratoService;
	
	
	public double consultarSaldo(String agencia, String numeroConta) {
		Conta conta = this.carregarContaPorNumero(agencia, numeroConta);
		return conta.getSaldo();
	}
	
	public Conta consultarConta(String agencia, String numeroConta) {
		Conta conta = this.carregarContaPorNumero(agencia, numeroConta);
		return conta;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void saque(Conta conta, double valor, boolean isTransfer) {
		OperacaoEnum operacao = OperacaoEnum.SAQUE;
		if (conta.getSaldo() < valor) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_SALDO_CONTA_INSUFICIENTE);
		}
		conta.setSaldo(conta.getSaldo() - valor);
		this.salvar(conta);			
		
		if (!isTransfer)
			extratoService.realizarOperacao(conta, valor * (-1), operacao);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deposito(Conta conta, double valor, boolean isTransfer) {
		OperacaoEnum operacao = OperacaoEnum.DEPOSITO;
		conta.setSaldo(conta.getSaldo() + valor);
		this.salvar(conta);	
		
		if (!isTransfer)
			extratoService.realizarOperacao(conta, valor, operacao);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void transferencia (Conta contaOrigem, Conta contaDestino, double valor) {
		OperacaoEnum operacao = OperacaoEnum.TRANSFERENCIA;
		this.saque(contaOrigem, valor, true);
		this.deposito(contaDestino, valor, true);
		
		extratoService.realizarOperacao(contaOrigem, valor * (-1), operacao);
		
		extratoService.realizarOperacao(contaDestino, valor, operacao);
	}
	
	public Conta carregarContaPorNumero(String agencia, String numeroConta) {
		Conta conta = repository.findByAgenciaAndNumeroConta(agencia, numeroConta);
		if (conta == null) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_CONTA_INEXISTENTE);
		}
		return conta;
	}
	
	public List<Conta> obterContasDoCliente(String cpf){
		Cliente cli = clienteService.buscarClientePorCpf(cpf);
		if (cli != null) {
			return contaRepository.findByCliente(cli);
		}
		return null;
	}
}
