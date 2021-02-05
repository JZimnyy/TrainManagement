package com.ProjektJakubZimny.Projekt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrzystankiRepository extends JpaRepository<Przystanki,Integer> {
    Przystanki findByPrzystanekIdEquals(int id);

    List<Przystanki> findByNazwaContains(String s);
}
