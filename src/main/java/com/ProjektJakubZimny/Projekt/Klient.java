package com.ProjektJakubZimny.Projekt;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Klient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String imie;
    private String nazwisko;
    private String PESEL;
    private String nrTelefonu;
    private String miejscowosc;
    private String nrDomu;
    private String kodPocztowy;
    @Column(unique = true)
    private String email;
    private String haslo;
    private String enabled;
    private String role;
    @OneToMany(mappedBy = "klient")
    private Set<Bilet> bilety;


    protected Klient(){}

    public Klient(String imie, String nazwisko, String PESEL, String nrTelefonu, String miejscowosc, String nrDomu, String kodPocztowy, String email, String haslo) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.PESEL = PESEL;
        this.nrTelefonu = nrTelefonu;
        this.miejscowosc = miejscowosc;
        this.nrDomu = nrDomu;
        this.kodPocztowy = kodPocztowy;
        this.email = email;
        this.haslo = haslo;
        this.bilety = new HashSet<>();
        enabled = "true";
        role = "USER";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public String getNrTelefonu() {
        return nrTelefonu;
    }

    public void setNrTelefonu(String nrTelefonu) {
        this.nrTelefonu = nrTelefonu;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getNrDomu() {
        return nrDomu;
    }

    public void setNrDomu(String nrDomu) {
        this.nrDomu = nrDomu;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public Set<Bilet> getBilety() {
        return bilety;
    }

    public void setBilety(Set<Bilet> bilety) {
        this.bilety = bilety;
    }

    public void dodajBilet(Bilet b)
    {
        this.bilety.add(b);
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
