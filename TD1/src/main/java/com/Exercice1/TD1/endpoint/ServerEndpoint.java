package com.Exercice1.TD1.endpoint;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.Exercice1.TD1.model.Server;
import com.Exercice1.TD1.service.ServerService;

@Endpoint
public class ServerEndpoint {

    private static final String NAMESPACE_URI = "http://com/exercice1/td1/soap";

    private final ServerService serverService;

    public ServerEndpoint(ServerService serverService) {
        this.serverService = serverService;
    }

    /**
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateServerRequest")
    @ResponsePayload
    public CreateServerResponse createServer(@RequestPayload CreateServerResponse request) {
        final var newServer = new Server();
        newServer.setName(request.getName());
        newServer.setIpAddress(request.getIpAddress());
        Server created = serverService.createServer(newServer);
        CreateServerResponse response = new CreateServerResponse();
        response.setServer(toSoapServer(created));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListServersRequest")
    @ResponsePayload
    public ListServersResponse listServers(@RequestPayload ListServersResponse request) {
        List<Server> servers = serverService.listServers();
        ListServersResponse response = new ListServersResponse();
        response.getServers().add(
            (Server) servers.stream()
                   .map(this::toSoapServer)
                   .collect(Collectors.toList())
        );
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetServerStatusRequest")
    @ResponsePayload
    public GetServerStatusResponse getServerStatus(@RequestPayload GetServerStatusResponse request) {
        boolean status = serverService.getServerStatus(request.getId());
        GetServerStatusResponse response = new GetServerStatusResponse();
        response.setStatus(status);
        return response;
    }

    /**
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StartServerRequest")
    @ResponsePayload
    public ServerResponse startServer(@RequestPayload ServerRequest request) {
        Server started = serverService.startServer(((Server) request).getId());
        final ServerResponse response = new ServerResponse();
        ((CreateServerResponse) response).setServer(toSoapServer(started));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StopServerRequest")
    @ResponsePayload
    public ServerResponse stopServer(@RequestPayload ServerRequest request) {
        Server stopped = serverService.stopServer(((Server) request).getId());
        ServerResponse response = new ServerResponse();
        ((CreateServerResponse) response).setServer(toSoapServer(stopped));
        return response;
    }

    private com.Exercice1.TD1.soap.Server toSoapServer(Server server) {
        com.Exercice1.TD1.soap.Server soapServer = new com.Exercice1.TD1.soap.Server();
        soapServer.setId(server.getId());
        soapServer.setName(server.getName());
        soapServer.setIpAddress(server.getIpAddress());
        soapServer.setStatus(server.isStatus());
        return soapServer;
    }
}

