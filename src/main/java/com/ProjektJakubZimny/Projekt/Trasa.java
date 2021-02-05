package com.ProjektJakubZimny.Projekt;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Trasa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int trasaId;
    private String nazwaTrasy;
    private String przystanekPoczatkowy;
    private String przystanekKoncowy;

    @OneToMany(mappedBy = "trasa")
    private Set<ListaPrzystankow> listaPrzystankowSet;

    @OneToMany(mappedBy = "wybranaTrasa")
    private Set<Bilet> bilety;


    public Trasa(String nazwaTrasy, String przystanekPoczatkowy, String przystanekKoncowy) {
        this.nazwaTrasy = nazwaTrasy;
        this.przystanekPoczatkowy = przystanekPoczatkowy;
        this.przystanekKoncowy = przystanekKoncowy;
        listaPrzystankowSet = new HashSet<ListaPrzystankow>();
        this.bilety = new HashSet<>();
    }

    protected Trasa() {}

    public int getTrasaId() {
        return trasaId;
    }

    public void setTrasaId(int trasaId) {
        this.trasaId = trasaId;
    }

    public String getNazwaTrasy() {
        return nazwaTrasy;
    }

    public void setNazwaTrasy(String nazwaTrasy) {
        this.nazwaTrasy = nazwaTrasy;
    }

    public String getPrzystanekPoczatkowy() {
        return przystanekPoczatkowy;
    }

    public void setPrzystanekPoczatkowy(String przystanekPoczatkowy) {
        this.przystanekPoczatkowy = przystanekPoczatkowy;
    }

    public String getPrzystanekKoncowy() {
        return przystanekKoncowy;
    }

    public void setPrzystanekKoncowy(String przystanekKoncowy) {
        this.przystanekKoncowy = przystanekKoncowy;
    }

    public Set<ListaPrzystankow> getListaPrzystankowSet() {
        return listaPrzystankowSet;
    }

    public void setListaPrzystankowSet(Set<ListaPrzystankow> listaPrzystankowSet) {
        this.listaPrzystankowSet = listaPrzystankowSet;
    }

    public void dodajPrzystanek(ListaPrzystankow listaPrzystankow)
    {
        this.listaPrzystankowSet.add(listaPrzystankow);
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
}
