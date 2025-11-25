package com.Exercice1.TD1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Exercice1.TD1.model.Server;
import com.Exercice1.TD1.service.ServerService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/servers")
@Tag(name = "Server Management", description = "APIs for managing servers in the data center")
public class ServerRestController {

	private final ServerService serverService;

	public ServerRestController(ServerService serverService) {
		this.serverService = serverService;
	}

	@Operation(summary = "Create a new server", description = "Creates a new server with the provided details.")
	@ApiResponse(responseCode = "201", description = "Server created successfully")
	@PostMapping(value = "", path = "")
	public ResponseEntity<Server> createServer(
			@Parameter(description = "Server object to be created") @Valid @RequestBody Server server) {
		Server created = serverService.createServer(server);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@Operation(summary = "List all servers", description = "Returns a list of all servers.")
	@ApiResponse(responseCode = "200", description = "List of servers")
	@GetMapping
	public ResponseEntity<List<Server>> listServers() {
		return ResponseEntity.ok(serverService.listServers());
	}

	@Operation(summary = "Rename a server", description = "Renames the server identified by the given ID.")
	@ApiResponse(responseCode = "200", description = "Server renamed successfully")
	@PatchMapping(path = "/{id}/rename")
	public ResponseEntity<Server> renameServer(@Parameter(description = "ID of the server") @PathVariable Long id,
			@Parameter(description = "New name for the server") @RequestParam String name) {
		return ResponseEntity.ok(serverService.renameServer(id, name));
	}

	@Operation(summary = "Get server status", description = "Returns the status (running or stopped) of the server.")
	@ApiResponse(responseCode = "200", description = "Server status returned")
	@GetMapping(path = "/{id}/status")
	public ResponseEntity<Boolean> getStatus(@Parameter(description = "ID of the server") @PathVariable Long id) {
		return ResponseEntity.ok(serverService.getServerStatus(id));
	}

	@Operation(summary = "Start a server", description = "Starts the server identified by the given ID.")
	@ApiResponse(responseCode = "200", description = "Server started successfully")
	@PostMapping(path = "/{id}/start", value = "")
	public ResponseEntity<Server> startServer(@Parameter(description = "ID of the server") @PathVariable Long id) {
		return ResponseEntity.ok(serverService.startServer(id));
	}

	@Operation(summary = "Stop a server", description = "Stops the server identified by the given ID.")
	@ApiResponse(responseCode = "200", description = "Server stopped successfully")
	@PostMapping(path = "/{id}/stop", value = "")
	public ResponseEntity<Server> stopServer(@Parameter(description = "ID of the server") @PathVariable Long id) {
		return ResponseEntity.ok(serverService.stopServer(id));
	}

	@Operation(summary = "Delete a server", description = "Deletes the server identified by the given ID if it is not running.")
	@ApiResponse(responseCode = "204", description = "Server deleted successfully")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteServer(@Parameter(description = "ID of the server") @PathVariable Long id) {
		serverService.deleteServer(id);
		return ResponseEntity.noContent().build();
	}

	public ServerService getServerService() {
		return serverService;
	}
}
