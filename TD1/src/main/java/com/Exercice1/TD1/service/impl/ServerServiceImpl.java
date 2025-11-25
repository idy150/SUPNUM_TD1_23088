package com.Exercice1.TD1.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Exercice1.TD1.model.Server;
import com.Exercice1.TD1.repository.ServerRepository;
import com.Exercice1.TD1.service.ServerService;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {

	private final ServerRepository serverRepository;

	public ServerServiceImpl(ServerRepository serverRepository) {
		this.serverRepository = serverRepository;
	}

	@Override
	public Server createServer(Server server) {
		return serverRepository.save(server);
	}

	@Override
	public List<Server> listServers() {
		return serverRepository.findAll();
	}

	@Override
	public Server renameServer(Long id, String newName) {
		Server s = serverRepository.findById(id).orElseThrow(() -> new RuntimeException("Server not found"));
		s.setName(newName);
		return serverRepository.save(s);
	}

	@Override
	public boolean getServerStatus(Long id) {
		return serverRepository.findById(id).map(Server::isStatus).orElseThrow(() -> new RuntimeException("Server not found"));
	}

	@Override
	public Server startServer(Long id) {
		Server s = serverRepository.findById(id).orElseThrow(() -> new RuntimeException("Server not found"));
		s.setStatus(true);
		return serverRepository.save(s);
	}

	@Override
	public Server stopServer(Long id) {
		Server s = serverRepository.findById(id).orElseThrow(() -> new RuntimeException("Server not found"));
		s.setStatus(false);
		return serverRepository.save(s);
	}

	@Override
	public void deleteServer(Long id) {
		Server s = serverRepository.findById(id).orElseThrow(() -> new RuntimeException("Server not found"));
		if (!s.isStatus()) {
		} else {
                    throw new RuntimeException("Cannot delete a running server");
            }
		serverRepository.deleteById(id);
	}
}
