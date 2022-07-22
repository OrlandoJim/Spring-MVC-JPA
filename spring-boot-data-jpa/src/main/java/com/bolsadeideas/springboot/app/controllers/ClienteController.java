package com.bolsadeideas.springboot.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteService.listarTodos());
		return "listar";
	}
	
	@GetMapping("/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("titulo", "Formulario para agregar un cliente");
		model.addAttribute("boton", "Agregar cliente");
		model.addAttribute("cliente", cliente);
		return "form";
	}
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Model model) {
		
		Cliente cliente = null;
		
		if(id > 0) {
			cliente = clienteService.buscarPorId(id);
		} else {
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Editar datos del cliente");
		model.addAttribute("boton", "Guardar cambios");
		model.addAttribute("cliente",cliente);
		return "form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) {
		if(result.hasErrors()) {
			if(cliente.getId() != null && cliente.getId() > 0) {
				model.addAttribute("titulo", "Editar datos del cliente");
				model.addAttribute("boton", "Guardar cambios");
				return "form";
			} else {
				model.addAttribute("titulo", "Formulario para agregar un cliente");
				model.addAttribute("boton", "Agregar cliente");
				return "form";
			}
		}
		
		clienteService.guardar(cliente);
		status.setComplete();
		return "redirect:listar";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, Model model) {
		if (id > 0) {
			clienteService.eliminar(id);
		}
		return "redirect:/listar";
	}
}
