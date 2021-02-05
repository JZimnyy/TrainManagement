package com.ProjektJakubZimny.Projekt;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ListaPrzystankow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int listaID;
    private int nrPrzystanku;

    @ManyToOne
    private Trasa trasa;
    @ManyToOne
    private Przystanki przystanek;

    @OneToMany(mappedBy = "lista")
    private Set<Odjazdy> odjazdySet;

    protected ListaPrzystankow(){}

    public ListaPrzystankow(int nrPrzystanku, Trasa trasa, Przystanki przystanek) {
        this.nrPrzystanku = nrPrzystanku;
        this.trasa = trasa;
        this.przystanek = przystanek;
        this.odjazdySet = new HashSet<>();
    }

    public int getListaID() {
        return listaID;
    }

    public void setListaID(int listaID) {
        this.listaID = listaID;
    }

    public int getNrPrzystanku() {
        return nrPrzystanku;
    }

    public void setNrPrzystanku(int nrPrzystanku) {
        this.nrPrzystanku = nrPrzystanku;
    }

    public Trasa getTrasa() {
        return trasa;
    }

    public void setTrasa(Trasa trasa) {
        this.trasa = trasa;
    }

    public Przystanki getPrzystanek() {
        return przystanek;
    }

    public void setPrzystanek(Przystanki przystanek) {
        this.przystanek = przystanek;
    }

    public Set<Odjazdy> getOdjazdySet() {
        return odjazdySet;
    }

    public void setOdjazdySet(Set<Odjazdy> odjazdySet) {
        this.odjazdySet = odjazdySet;
    }

    public void dodajOdjazd(Odjazdy o)
    {
        odjazdySet.add(o);
    }
}
