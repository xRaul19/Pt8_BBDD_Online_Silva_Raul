package com.example.pt8_bbdd_online_silva_raul;

public class Vehicle {
    private String matricula;
    private String nom;
    private String cognoms;
    private String telefon;
    private String marca;
    private String model;

    public Vehicle() {}

    public Vehicle(String matricula, String nom, String cognoms, String telefon, String marca, String model) {
        this.matricula = matricula;
        this.nom = nom;
        this.cognoms = cognoms;
        this.telefon = telefon;
        this.marca = marca;
        this.model = model;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCognoms() { return cognoms; }
    public void setCognoms(String cognoms) { this.cognoms = cognoms; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}