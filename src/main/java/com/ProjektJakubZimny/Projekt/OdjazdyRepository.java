package com.ProjektJakubZimny.Projekt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OdjazdyRepository extends JpaRepository<Odjazdy,Integer> {

    List<Odjazdy> findByListaEquals(ListaPrzystankow l);

    Odjazdy findByListaEqualsAndNrKursuEquals(ListaPrzystankow l, int nr);

    Odjazdy findByOdjazdIdEquals(int id);
}
