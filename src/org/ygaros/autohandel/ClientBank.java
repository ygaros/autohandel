package org.ygaros.autohandel;

import org.ygaros.autohandel.model.client.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientBank {
    private List<Client> availableClients;

    public ClientBank() {
        this.availableClients = new ArrayList<>();
    }
    public boolean remove(Client client){
        return this.availableClients.remove(client);
    }
    public void addClient(Client client){
        this.availableClients.add(client);
    }

    public List<Client> getAvailableClients() {
        return availableClients;
    }

    public void addAll(List<Client> clients){
        this.availableClients.addAll(clients);
    }

}
