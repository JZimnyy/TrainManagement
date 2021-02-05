package com.ProjektJakubZimny.Projekt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiletRepository extends JpaRepository<Bilet,Integer> {

    List<Bilet> findByKlientEquals(Klient k);

    List<Bilet> findByKlientEqualsAndWybranaTrasaEquals(Klient k,Trasa t);

    Bilet findByIdEquals(int id);
}
