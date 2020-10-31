package com.indracompany.treinamento.controller.rest;


import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.service.ClienteService;


@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("rest/clientes")
public class ClienteRest extends GenericCrudRest<Cliente, Long, ClienteService>{

	@Autowired
	private ClienteService clienteService; 
	
	@RequestMapping(value = "/buscar-por-cpf/{cpf}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<Cliente> buscarClientePorCpf(final @RequestParam String cpf) {
		Cliente retorno = clienteService.buscarClientePorCpf(cpf);
		return  new ResponseEntity<>(retorno, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buscar-por-nome/{nome}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<List<Cliente>> buscarClientePorNome(final @RequestParam String nome) {
		List<Cliente> retorno = clienteService.buscarClientePorNome(nome);
		return  new ResponseEntity<>(retorno, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buscar-por-email/{email}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<Cliente> buscarClientePorEmail(final @RequestParam String email){
		Cliente retorno = clienteService.buscarClientePorEmail(email);
		return  new ResponseEntity<>(retorno, HttpStatus.OK);
	}
	
}
