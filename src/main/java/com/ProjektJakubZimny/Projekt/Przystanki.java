package com.ProjektJakubZimny.Projekt;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Przystanki {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int przystanekId;
    private String nazwa;
    private int nrPeronu;

    @OneToMany(mappedBy = "przystanek")
    private Set<ListaPrzystankow> listaPrzystankowSet;

    protected Przystanki(){}

    public Przystanki(String nazwa, int nrPeronu) {
        this.nazwa = nazwa;
        this.nrPeronu = nrPeronu;
        listaPrzystankowSet = new HashSet<ListaPrzystankow>();
    }

    public int getPrzystanekId() {
        return przystanekId;
    }

    public void setPrzystanekId(int przystanekId) {
        this.przystanekId = przystanekId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getNrPeronu() {
        return nrPeronu;
    }

    public void setNrPeronu(int nrPeronu) {
        this.nrPeronu = nrPeronu;
    }

    public Set<ListaPrzystankow> getListaPrzystankowSet() {
        return listaPrzystankowSet;
    }

    public void setListaPrzystankowSet(Set<ListaPrzystankow> listaPrzystankowSet) {
        this.listaPrzystankowSet = listaPrzystankowSet;
    }

    public void dodajDoListyPrzystankow(ListaPrzystankow l)
    {
        listaPrzystankowSet.add(l);
    }
}
