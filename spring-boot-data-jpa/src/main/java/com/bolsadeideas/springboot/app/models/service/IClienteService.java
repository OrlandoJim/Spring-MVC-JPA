package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> listarTodos();
	
	public void guardar(Cliente cliente);
	
	public Cliente buscarPorId(Long id);
	
	public void eliminar(Long id);
	
	public Page<Cliente> listarTodos(Pageable pageable);
}
