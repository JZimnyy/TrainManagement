package com.ProjektJakubZimny.Projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
public class TrasaController {

    @Autowired
    TrasaRepository trasaRepository;

    @Autowired
    ListaPrzystankowRepository listaPrzystankowRepository;

    @Autowired
    PrzystankiRepository przystankiRepository;

    @Autowired
    KlientRepository klientRepository;

    @RequestMapping("/trasy")
    public String wyswietlTrasy(Model m, Principal p)
    {
        try {
            List<Trasa> listaTras = trasaRepository.findAll();
            m.addAttribute("naglowek", "Lista wszystkich tras");
            m.addAttribute("lista", listaTras);

            String email = p.getName();
            Klient k = klientRepository.findByEmailEquals(email);

            TransData transData = new TransData();
            m.addAttribute("dane",transData);
            if (k.getRole().equals("ADMIN")) return "pokazTrasyAdmin";
            else return "pokazTrasy";
        }catch (Exception e)
        {
            List<Trasa> listaTras = trasaRepository.findAll();
            TransData transData = new TransData();
            m.addAttribute("dane",transData);
            m.addAttribute("naglowek", "Lista wszystkich tras");
            m.addAttribute("lista", listaTras);
            return "pokazTrasy";
        }
    }

    @RequestMapping(value = "/trasy",method = RequestMethod.POST)
    public String wyswietlTrasy(Model m, Principal p,TransData dane)
    {
        try {
            List<Trasa> listaTras = trasaRepository.findByNazwaTrasyContains(dane.getNazwaTrasy());
            m.addAttribute("naglowek", "Lista wszystkich tras");
            m.addAttribute("lista", listaTras);

            String email = p.getName();
            Klient k = klientRepository.findByEmailEquals(email);

            TransData transData = new TransData();
            m.addAttribute("dane",transData);
            if (k.getRole().equals("ADMIN")) return "pokazTrasyAdmin";
            else return "pokazTrasy";
        }catch (Exception e)
        {
            List<Trasa> listaTras = trasaRepository.findByNazwaTrasyContains(dane.getNazwaTrasy());
            TransData transData = new TransData();
            m.addAttribute("dane",transData);
            m.addAttribute("naglowek", "Lista wszystkich tras");
            m.addAttribute("lista", listaTras);
            return "pokazTrasy";
        }
    }


    @RequestMapping("/trasy/przystanki/{id}")
    public String przystankiNaTrasie(@PathVariable int id, Model m)
    {
        try {
            Trasa trasa = trasaRepository.findByTrasaIdEquals(id);
            Set<ListaPrzystankow> listaPrzystankow = trasa.getListaPrzystankowSet();

            Iterator i = listaPrzystankow.iterator();

            List<ListaPrzystankow> lista = listaPrzystankowRepository.findByTrasaEquals(trasa);


            String nazwa = trasa.getNazwaTrasy();
            m.addAttribute("naglowek", nazwa);
            m.addAttribute("lista", lista);

            return "listaPrzystankowNaTrasie";
        }catch (Exception e)
        {
            m.addAttribute("header", "Błąd");
            m.addAttribute("message", "Nie ma takiej trasy");
            return "message";
        }
    };

    @RequestMapping("/trasy/dodaj")
    public String dodajTrase(Model m)
    {

        TransData nowaTrasa = new TransData();
        m.addAttribute("nowaTrasa",nowaTrasa);
        return "dodajTrase";
    };

    @RequestMapping(value = "/trasy/dodaj", method = RequestMethod.POST)
    public String dodajTrase(Model m,TransData nowaTrasa)
    {
        String nazwa = nowaTrasa.getNazwaTrasy();
        String p1 = nowaTrasa.getPrzystanekPoczatkowy();
        String p2 = nowaTrasa.getPrzystanekKoncowy();

        trasaRepository.save(new Trasa(nazwa,p1,p2));


        m.addAttribute("header","Dodano nową trasę");
        m.addAttribute("message","Trasa: " + nazwa);
        return "message";
    }


    @RequestMapping("/trasy/dodajPrzystanek")
    public String dodajPrzystanekDoTrasy(Model m)
    {
        List<Trasa> trasy = trasaRepository.findAll();
        List<Przystanki> przystanki = przystankiRepository.findAll();
        TransData nowyPrzystanek = new TransData();
        m.addAttribute("trasy",trasy);
        m.addAttribute("nowyPrzystanek",nowyPrzystanek);
        m.addAttribute("przystanki", przystanki);
        return "dodajPrzystanekDoTrasy";
    }

    @RequestMapping(value = "/trasy/dodajPrzystanek", method = RequestMethod.POST)
    public String dodajPrzystanekDotrasy(Model m, TransData nowyPrzystanek)
    {
        int trasaid = Integer.parseInt(nowyPrzystanek.getTrasaID());
        int przystanekid = Integer.parseInt(nowyPrzystanek.getPrzystanekID());
        int nrPrzystanku = Integer.parseInt(nowyPrzystanek.getNrPrzystanku());
        Trasa trasa = trasaRepository.findByTrasaIdEquals(trasaid);
        Przystanki przystanek = przystankiRepository.findByPrzystanekIdEquals(przystanekid);
        ListaPrzystankow l1 = new ListaPrzystankow(nrPrzystanku,trasa,przystanek);
        listaPrzystankowRepository.save(l1);
        trasa.dodajPrzystanek(l1);
        przystanek.dodajDoListyPrzystankow(l1);
        trasaRepository.save(trasa);
        przystankiRepository.save(przystanek);
        m.addAttribute("header",trasa.getNazwaTrasy()+" "+przystanek.getNazwa()+" "+nowyPrzystanek.getNrPrzystanku());
        return "message";
    }

    @RequestMapping("/trasy/edytuj/{id}")
    public String edytujTrase(@PathVariable int id, Model m)
    {
        try {
            Trasa trasa = trasaRepository.findByTrasaIdEquals(id);
            if(trasa.getNazwaTrasy().equals(""))
            {
                m.addAttribute("header", "Błąd");
                m.addAttribute("message", "Nie ma takiej trasy");
                return "message";
            }
            m.addAttribute("trasa", trasa);
            return "edytujTrase";
        }catch (Exception e)
        {
            m.addAttribute("header", "Błąd");
            m.addAttribute("message", "Nie ma takiej trasy");
            return "message";
        }
    }

    @RequestMapping(value = "/trasy/zapiszZmiany",method = RequestMethod.POST)
    public String edytujTrase(Model m,Trasa trasa)
    {
        try {

            trasaRepository.save(trasa);
            m.addAttribute("header","Sukces");
            m.addAttribute("message","Zmiany zostały zapisane");
            return "message";

        }catch (Exception e)
        {
            m.addAttribute("header","Wystąpił błąd");
            m.addAttribute("message","Spróbuj ponownie");
            return "message";
        }
    }



}
