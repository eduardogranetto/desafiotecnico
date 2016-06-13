package br.com.desafiotecnico.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.desafiotecnico.model.Servico;
import br.com.desafiotecnico.service.ServicoService;

@Controller("SERVICO_API_CONTROLLER")
@RequestMapping("/api")
public class ServicoController {

	@Autowired
	private ServicoService servicoService;
	
	@ResponseBody
	@RequestMapping(value="/servicos", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Servico> listar(){
		return servicoService.buscarTodos();
	}
	
}