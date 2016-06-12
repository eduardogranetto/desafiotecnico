package br.com.desafiotecnico.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.desafiotecnico.model.Cliente;
import br.com.desafiotecnico.model.Tipo;
import br.com.desafiotecnico.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	private static final String INDEX = "clientes/index";
	private static final String FORM = "clientes/form";
	private static final String REMOVER = "clientes/remover";
	
	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView index(){
		return new ModelAndView(INDEX).addObject("clientes", clienteService.buscarTodos());
	}
	
	@RequestMapping(value="/novo", method=RequestMethod.GET)
	public ModelAndView novo(){
		return form(new Cliente()); 
	}

	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public ModelAndView salvar(@Valid @ModelAttribute Cliente cliente, BindingResult result, RedirectAttributes attributes){
		ModelAndView modelAndView = new ModelAndView("redirect:/clientes/");
		if(result.hasErrors()){
			return form(cliente);
		}
		clienteService.salvar(cliente);
		attributes.addFlashAttribute("msgSuccess", "Cliente salvo com sucesso!");
		return modelAndView;
	}

	@RequestMapping(value="/{id}/editar", method=RequestMethod.GET)
	public ModelAndView editar(@PathVariable(value="id") Long id){
		return form(clienteService.buscarPorId(id)); 
	}
	
	@RequestMapping(value="/{id}/remover", method=RequestMethod.GET)
	public ModelAndView remover(@PathVariable(value="id") Long id){
		return new ModelAndView(REMOVER).addObject("cliente", clienteService.buscarPorId(id));
	}

	@RequestMapping(value="/{id}/remover", method=RequestMethod.DELETE)
	public ModelAndView excluir(@PathVariable(value="id") Long id, RedirectAttributes redirectAttributes){
		redirectAttributes.addAttribute("msgSuccess", "Cliente removido com sucesso!");
		clienteService.remover(id);
		return new ModelAndView("redirect:/clientes/");
	}
	
	private ModelAndView form(Cliente cliente) {
		return new ModelAndView(FORM).addObject("cliente", cliente).addObject("tipos", Tipo.values());
	}
	
}