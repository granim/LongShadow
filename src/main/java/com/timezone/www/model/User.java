package com.timezone.www.model;

import lombok.Builder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table()
@Transactional
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public Set<Client> clients = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public Set<Coworker> coworkers = new HashSet<>();


    public User(){}

    @Builder
    public User(String userName, String email, String password, Collection<Role> roles, Set<Client> clients, Set<Coworker> coworkers) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.clients = clients;
        this.coworkers = coworkers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public Set<Coworker> getCoworkers() {
        return coworkers;
    }

    public void setCoworkers(Set<Coworker> coworkers) {
        this.coworkers = coworkers;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    protected Set<Client> getClientsInternal(){
        if(this.clients == null) {
            this.clients = new HashSet<>();
        }
        return this.clients;
    }

    public void addClient(Client client){
        if(client.isNew()){
            getClientsInternal().add(client);
        }
        client.setUser(this);
    }

    public Client getBaseClient(String name, boolean ignoreNew){
        name = name.toLowerCase();
        for(Client client : clients) {
            if(!ignoreNew || !client.isNew()) {
                String compName = client.getCompanyName();
                compName = compName.toLowerCase();
                if(compName.equals(name)) {
                    return client;
                }
            }
        }
        return null;
    }

    protected Set<Coworker> getCoworkersInternal(){
        if(this.coworkers == null) {
            this.coworkers = new HashSet<>();
        }
        return this.coworkers;
    }

    protected void setCoworkersInternal(Set<Coworker> coworkers){
        this.coworkers = coworkers;
    }

    public void addCoworker(Coworker coworker){
        if(coworker.isNew()){
            getCoworkersInternal().add(coworker);
        }
        coworker.setUser(this);
    }

    public Coworker getCoworker(String name) {
        return getCoworker(name, false);
    }

    public Coworker getCoworker(String name, boolean ignoreNew){
        name = name.toLowerCase();
        for(Coworker coworker : coworkers) {
            if(!ignoreNew || !coworker.isNew()) {
                String compName = coworker.getfName();
                compName = compName.toLowerCase();
                if(compName.equals(name)) {
                    return coworker;
                }
            }
        }
        return null;
    }







}
