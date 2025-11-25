package com.Exercice1.TD1.service;

import java.util.List;

import com.Exercice1.TD1.model.Server;

public interface ServerService {

	Server createServer(Server server);

	List<Server> listServers();

	Server renameServer(Long id, String newName);

	boolean getServerStatus(Long id);

	Server startServer(Long id);

	Server stopServer(Long id);

	void deleteServer(Long id);

}
