package com.ProjektJakubZimny.Projekt;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KlientRepository extends JpaRepository<Klient,Integer> {

    Klient findByIdEquals(int id);

    Klient findByEmailEquals(String email);

    List<Klient> findByImieEqualsAndNazwiskoEquals(String imie,String nazwisko);

    List<Klient> findByImieEquals(String imie);

    List<Klient> findByNazwiskoEquals(String nazwisko);



}
