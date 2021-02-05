package com.ProjektJakubZimny.Projekt;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrasaRepository extends JpaRepository<Trasa, Integer> {
    Trasa findByTrasaIdEquals(int id);

    List<Trasa> findByNazwaTrasyContains(String s);
}
