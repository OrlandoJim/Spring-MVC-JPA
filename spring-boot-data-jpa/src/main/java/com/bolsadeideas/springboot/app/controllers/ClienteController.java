package com.bolsadeideas.springboot.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/listar")
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		
		Page<Cliente> clientes = clienteService.listarTodos(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
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
	public String editar(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		
		Cliente cliente = null;
		
		if(id > 0) {
			cliente = clienteService.buscarPorId(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la base de datos");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Editar datos del cliente");
		model.addAttribute("boton", "Guardar cambios");
		model.addAttribute("cliente",cliente);
		return "form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
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
		String mensajeFlash = (cliente.getId() != null)? "Cliente editado con éxito!":"Cliente creado con éxito";
		
		clienteService.guardar(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash, Model model) {
		if (id > 0) {
			clienteService.eliminar(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito.");
		}
		return "redirect:/listar";
	}
}
