package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> listarTodos() {
		return clienteDao.listarTodos();
	}

	@Override
	@Transactional
	public void guardar(Cliente cliente) {
		clienteDao.guardar(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return clienteDao.buscarPorId(id);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		clienteDao.eliminar(id);
	}

}
