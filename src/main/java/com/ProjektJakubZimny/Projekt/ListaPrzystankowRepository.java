package com.ProjektJakubZimny.Projekt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListaPrzystankowRepository extends JpaRepository<ListaPrzystankow,Integer> {

    List<ListaPrzystankow> findByTrasaEquals(Trasa t);

    ListaPrzystankow findByListaIDEquals(int id);

    List<ListaPrzystankow> findByPrzystanekEquals(Przystanki p);

}
