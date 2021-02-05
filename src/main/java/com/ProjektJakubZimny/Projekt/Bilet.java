package com.ProjektJakubZimny.Projekt;


import javax.persistence.*;

@Entity
public class Bilet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Klient klient;

    @ManyToOne
    private Trasa wybranaTrasa;

    @ManyToOne
    private Odjazdy przystanekPoczatkowy;

    @ManyToOne
    private Odjazdy przystanekKoncowy;

    private float cena;

    public Bilet(Klient klient, Trasa trasa, Odjazdy przystanekPoczatkowy, Odjazdy przystanekKoncowy,float cena) {
        this.klient = klient;
        this.wybranaTrasa = trasa;
        this.przystanekPoczatkowy = przystanekPoczatkowy;
        this.przystanekKoncowy = przystanekKoncowy;
        this.cena = cena;
    }

    protected Bilet() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    public Trasa getTrasa() {
        return wybranaTrasa;
    }

    public void setTrasa(Trasa trasa) {
        this.wybranaTrasa = trasa;
    }

    public Odjazdy getPrzystanekPoczatkowy() {
        return przystanekPoczatkowy;
    }

    public void setPrzystanekPoczatkowy(Odjazdy przystanekPoczatkowy) {
        this.przystanekPoczatkowy = przystanekPoczatkowy;
    }

    public Odjazdy getPrzystanekKoncowy() {
        return przystanekKoncowy;
    }

    public void setPrzystanekKoncowy(Odjazdy przystanekKoncowy) {
        this.przystanekKoncowy = przystanekKoncowy;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public Trasa getWybranaTrasa() {
        return wybranaTrasa;
    }

    public void setWybranaTrasa(Trasa wybranaTrasa) {
        this.wybranaTrasa = wybranaTrasa;
    }
}
