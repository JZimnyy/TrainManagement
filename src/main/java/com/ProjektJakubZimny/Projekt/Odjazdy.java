package com.ProjektJakubZimny.Projekt;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Odjazdy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int odjazdId;
    private int nrKursu;
    private String dataGodzinaOdjazdu;
    @ManyToOne
    private ListaPrzystankow lista;

    @OneToMany(mappedBy = "przystanekPoczatkowy")
    private Set<Bilet> biletyPoczatek;

    @OneToMany(mappedBy = "przystanekKoncowy")
    private Set<Bilet> biletyKoniec;

    protected Odjazdy() {}

    public Odjazdy( int nrKursu, String dataGodzinaOdjazdu, ListaPrzystankow listaPrzystankow) {
        this.nrKursu = nrKursu;
        this.dataGodzinaOdjazdu = dataGodzinaOdjazdu;
        this.lista = listaPrzystankow;
        this.biletyPoczatek = new HashSet<>();
        this.biletyKoniec = new HashSet<>();
    }

    public int getOdjazdId() {
        return odjazdId;
    }

    public void setOdjazdId(int odjazdId) {
        this.odjazdId = odjazdId;
    }

    public int getNrKursu() {
        return nrKursu;
    }

    public void setNrKursu(int nrKursu) {
        this.nrKursu = nrKursu;
    }

    public String getDataGodzinaOdjazdu() {
        return dataGodzinaOdjazdu;
    }

    public void setDataGodzinaOdjazdu(String dataGodzinaOdjazdu) {
        this.dataGodzinaOdjazdu = dataGodzinaOdjazdu;
    }

    public ListaPrzystankow getListaPrzystankow() {
        return lista;
    }

    public void setListaPrzystankow(ListaPrzystankow listaPrzystankow) {
        this.lista = listaPrzystankow;
    }

    public void dodajBiletPoczatkowy(Bilet b)
    {
        biletyPoczatek.add(b);
    }

    public void dodajBiletKoniec(Bilet b)
    {
        biletyKoniec.add(b);
    }

    public ListaPrzystankow getLista() {
        return lista;
    }

    public void setLista(ListaPrzystankow lista) {
        this.lista = lista;
    }

    public Set<Bilet> getBiletyPoczatek() {
        return biletyPoczatek;
    }

    public void setBiletyPoczatek(Set<Bilet> biletyPoczatek) {
        this.biletyPoczatek = biletyPoczatek;
    }

    public Set<Bilet> getBiletyKoniec() {
        return biletyKoniec;
    }

    public void setBiletyKoniec(Set<Bilet> biletyKoniec) {
        this.biletyKoniec = biletyKoniec;
    }
}
