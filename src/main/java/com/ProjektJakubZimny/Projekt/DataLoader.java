package com.ProjektJakubZimny.Projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private KlientRepository klientRepository;
    private TrasaRepository trasaRepository;
    private ListaPrzystankowRepository listaPrzystankowRepository;
    private PrzystankiRepository przystankiRepository;
    private OdjazdyRepository odjazdyRepository;
    private BiletRepository biletRepository;

    @Autowired DataLoader(KlientRepository klientRepository,TrasaRepository trasaRepository, ListaPrzystankowRepository listaPrzystankowRepository, PrzystankiRepository przystankiRepository, OdjazdyRepository odjazdyRepository, BiletRepository biletRepository)
    {
        this.klientRepository = klientRepository;
        this.trasaRepository = trasaRepository;
        this.listaPrzystankowRepository = listaPrzystankowRepository;
        this.przystankiRepository = przystankiRepository;
        this.odjazdyRepository = odjazdyRepository;
        this.biletRepository = biletRepository;
    }


    public void run(ApplicationArguments args)
    {
        Klient k1 = new Klient("Jakub","Zimny","12345678900","985647216","Szufnarowa","57a","38-124","kuba@gmail.com","$2a$10$7Z/jpfPQYey7w06rVqCqhu.8rS6.f6x3XMIVMbpFzIM0qkvgPrd5S");
        klientRepository.save(k1);
        Klient k2 = new Klient("Jan","Nowak","12345678900","985647216","Szufnarowa","57b","38-124","kuba2@gmail.com","$2a$10$QK94tb0QIbjQRm5tkC87/e385o0L1ZkaU/Mnb93GxJvid/UN7JNOa");
        k2.setRole("ADMIN");
        klientRepository.save(k2);
        Trasa t1 = new Trasa("Rzeszów-Kraków","Rzeszów Główny","Kraków Główny");
        trasaRepository.save(t1);
        Trasa t2 = new Trasa("Kraków-Rzeszów","Krakow Główny","Rzeszów Główny");
        trasaRepository.save(t2);
        Przystanki p1 = new Przystanki("Rzeszów Główny",1);
        Przystanki p2 = new Przystanki("Dębica ",2);
        Przystanki p3 = new Przystanki("Tarnów",1);
        Przystanki p4 = new Przystanki("Bochnia",1);
        Przystanki p5 = new Przystanki("Kraków Główny",3);
        ListaPrzystankow l1 = new ListaPrzystankow(1,t1,p1);
        ListaPrzystankow l2 = new ListaPrzystankow(2,t1,p2);
        ListaPrzystankow l3 = new ListaPrzystankow(3,t1,p3);
        ListaPrzystankow l4 = new ListaPrzystankow(4,t1,p4);
        ListaPrzystankow l5 = new ListaPrzystankow(5,t1,p5);
        ListaPrzystankow l6 = new ListaPrzystankow(1,t2,p5);
        ListaPrzystankow l7 = new ListaPrzystankow(2,t2,p4);
        ListaPrzystankow l8 = new ListaPrzystankow(3,t2,p3);
        ListaPrzystankow l9 = new ListaPrzystankow(4,t2,p2);
        ListaPrzystankow l10 = new ListaPrzystankow(5,t2,p1);
        przystankiRepository.save(p1);
        przystankiRepository.save(p2);
        przystankiRepository.save(p3);
        przystankiRepository.save(p4);
        przystankiRepository.save(p5);
        listaPrzystankowRepository.save(l1);
        listaPrzystankowRepository.save(l2);
        listaPrzystankowRepository.save(l3);
        listaPrzystankowRepository.save(l4);
        listaPrzystankowRepository.save(l5);
        listaPrzystankowRepository.save(l6);
        listaPrzystankowRepository.save(l7);
        listaPrzystankowRepository.save(l8);
        listaPrzystankowRepository.save(l9);
        listaPrzystankowRepository.save(l10);
        p1.dodajDoListyPrzystankow(l1);
        p1.dodajDoListyPrzystankow(l10);
        p2.dodajDoListyPrzystankow(l2);
        p2.dodajDoListyPrzystankow(l9);
        p3.dodajDoListyPrzystankow(l3);
        p3.dodajDoListyPrzystankow(l8);
        p4.dodajDoListyPrzystankow(l4);
        p4.dodajDoListyPrzystankow(l7);
        p5.dodajDoListyPrzystankow(l5);
        p5.dodajDoListyPrzystankow(l6);


        przystankiRepository.save(p1);
        przystankiRepository.save(p2);
        przystankiRepository.save(p3);
        przystankiRepository.save(p4);
        przystankiRepository.save(p5);

        t1.dodajPrzystanek(l1);
        t1.dodajPrzystanek(l2);
        t1.dodajPrzystanek(l3);
        t1.dodajPrzystanek(l4);
        t1.dodajPrzystanek(l5);
        t2.dodajPrzystanek(l6);
        t2.dodajPrzystanek(l7);
        t2.dodajPrzystanek(l8);
        t2.dodajPrzystanek(l9);
        t2.dodajPrzystanek(l10);
        trasaRepository.save(t1);
        trasaRepository.save(t2);

        Odjazdy o1 = new Odjazdy(1,"2020.01.25 09:20",l1);
        Odjazdy o2 = new Odjazdy(1,"2020.01.25 10:03",l2);
        Odjazdy o3 = new Odjazdy(1,"2020.01.25 10:44",l3);
        Odjazdy o4 = new Odjazdy(1,"2020.01.25 11:01",l4);
        Odjazdy o5 = new Odjazdy(1,"2020.01.25 11:19",l5);

        Odjazdy o6 = new Odjazdy(2,"2020.01.26 08:20",l1);
        Odjazdy o7 = new Odjazdy(2,"2020.01.26 09:03",l2);
        Odjazdy o8 = new Odjazdy(2,"2020.01.26 09:44",l3);
        Odjazdy o9 = new Odjazdy(2,"2020.01.26 10:01",l4);
        Odjazdy o10 = new Odjazdy(2,"2020.01.26 10:19",l5);


        Odjazdy o11 = new Odjazdy(1,"2020.01.25 18:15",l6);
        Odjazdy o12 = new Odjazdy(1,"2020.01.25 19:02",l7);
        Odjazdy o13 = new Odjazdy(1,"2020.01.25 19:30",l8);
        Odjazdy o14 = new Odjazdy(1,"2020.01.25 20:01",l9);
        Odjazdy o15 = new Odjazdy(1,"2020.01.25 20:28",l10);
        Odjazdy o16 = new Odjazdy(2,"2020.01.26 09:20",l6);
        Odjazdy o17 = new Odjazdy(2,"2020.01.26 10:03",l7);
        Odjazdy o18 = new Odjazdy(2,"2020.01.26 10:44",l8);
        Odjazdy o19 = new Odjazdy(2,"2020.01.26 11:01",l9);
        Odjazdy o20 = new Odjazdy(2,"2020.01.26 11:19",l10);

        odjazdyRepository.save(o1);
        odjazdyRepository.save(o2);
        odjazdyRepository.save(o3);
        odjazdyRepository.save(o4);
        odjazdyRepository.save(o5);

        odjazdyRepository.save(o6);
        odjazdyRepository.save(o7);
        odjazdyRepository.save(o8);
        odjazdyRepository.save(o9);
        odjazdyRepository.save(o10);

        odjazdyRepository.save(o11);
        odjazdyRepository.save(o12);
        odjazdyRepository.save(o13);
        odjazdyRepository.save(o14);
        odjazdyRepository.save(o15);
        odjazdyRepository.save(o16);
        odjazdyRepository.save(o17);
        odjazdyRepository.save(o18);
        odjazdyRepository.save(o19);
        odjazdyRepository.save(o20);

        l1.dodajOdjazd(o1);
        l2.dodajOdjazd(o2);
        l3.dodajOdjazd(o3);
        l4.dodajOdjazd(o4);
        l5.dodajOdjazd(o5);
        l1.dodajOdjazd(o6);
        l2.dodajOdjazd(o7);
        l3.dodajOdjazd(o8);
        l4.dodajOdjazd(o9);
        l5.dodajOdjazd(o10);


        l6.dodajOdjazd(o11);
        l7.dodajOdjazd(o12);
        l8.dodajOdjazd(o13);
        l9.dodajOdjazd(o14);
        l10.dodajOdjazd(o15);
        l6.dodajOdjazd(o16);
        l7.dodajOdjazd(o17);
        l8.dodajOdjazd(o18);
        l9.dodajOdjazd(o19);
        l10.dodajOdjazd(o20);


        listaPrzystankowRepository.save(l1);
        listaPrzystankowRepository.save(l2);
        listaPrzystankowRepository.save(l3);
        listaPrzystankowRepository.save(l4);
        listaPrzystankowRepository.save(l5);
        listaPrzystankowRepository.save(l6);
        listaPrzystankowRepository.save(l7);
        listaPrzystankowRepository.save(l8);
        listaPrzystankowRepository.save(l9);
        listaPrzystankowRepository.save(l10);

        Bilet b1 = new Bilet(k1,t1,o1,o5,20);

        biletRepository.save(b1);

        k1.dodajBilet(b1);
        t1.dodajBilet(b1);
        o1.dodajBiletPoczatkowy(b1);
        o5.dodajBiletKoniec(b1);
        klientRepository.save(k1);
        trasaRepository.save(t1);
        odjazdyRepository.save(o1);
        odjazdyRepository.save(o5);

        System.out.println("DZIALA");
    }
}
