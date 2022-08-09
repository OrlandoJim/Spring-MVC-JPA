package com.bolsadeideas.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;




@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	
	
	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadFileService;

	@Secured("ROLE_USER")
	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.fetchClienteByIdWithFacturas(id);//clienteService.buscarPorId(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle del cliente: " + cliente.getNombre() + " " + cliente.getApellido());

		return "ver";
	}

	@GetMapping({"/listar", "/"})
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication) {
		
		
		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Cliente> clientes = clienteService.listarTodos(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("titulo", "Formulario para agregar un cliente");
		model.addAttribute("boton", "Agregar cliente");
		model.addAttribute("cliente", cliente);
		return "form";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.buscarPorId(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la base de datos");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Editar datos del cliente");
		model.addAttribute("boton", "Guardar cambios");
		model.addAttribute("cliente", cliente);
		return "form";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			if (cliente.getId() != null && cliente.getId() > 0) {
				model.addAttribute("titulo", "Editar datos del cliente");
				model.addAttribute("boton", "Guardar cambios");
				return "form";
			} else {
				model.addAttribute("titulo", "Formulario para agregar un cliente");
				model.addAttribute("boton", "Agregar cliente");
				return "form";
			}
		}

		if (!foto.isEmpty()) {

			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {
				uploadFileService.delete(cliente.getFoto());
			}

			String uniqueFileName = null;
			try {
				uniqueFileName = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFileName + "'");
			cliente.setFoto(uniqueFileName);

		}
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito";

		clienteService.guardar(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {
		if (id > 0) {
			Cliente cliente = clienteService.buscarPorId(id);

			clienteService.eliminar(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito.");

			if (uploadFileService.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info", "Imagen " + cliente.getFoto() + " eliminado con éxito!");
			}

		}
		return "redirect:/listar";
	}
	
	public boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if (context == null) {
			return false;
		}
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())){
				return true;
			}
		}
		return true;
	}
}
