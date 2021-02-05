package com.ProjektJakubZimny.Projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class PrzystankiController {

    @Autowired
    PrzystankiRepository przystankiRepository;

    @Autowired
    ListaPrzystankowRepository listaPrzystankowRepository;

    @Autowired
    TrasaRepository trasaRepository;

    @Autowired
    KlientRepository klientRepository;

    @Autowired
    OdjazdyRepository odjazdyRepository;

    @RequestMapping("/przystanki")
    public String pokazPrzystanki(Model m, Principal p)
    {

        List<Przystanki> przystanki = przystankiRepository.findAll();
        m.addAttribute("naglowek","Lista wszystkich przystanków");
        m.addAttribute("lista",przystanki);
        TransData transData = new TransData();
        m.addAttribute("dane",transData);
        try{
            String email = p.getName();
            Klient k = klientRepository.findByEmailEquals(email);

            if(k.getRole().equals("ADMIN")) return "pokazPrzystankiAdmin";
            else return"pokazPrzystanki";

        }catch (Exception e) {
            return "pokazPrzystanki";
        }
    }

    @RequestMapping(value = "/przystanki",method = RequestMethod.POST)
    public String pokazPrzystanki(Model m, Principal p,TransData dane)
    {

        List<Przystanki> przystanki = przystankiRepository.findByNazwaContains(dane.getNazwa());
        m.addAttribute("naglowek","Lista wszystkich przystanków");
        m.addAttribute("lista",przystanki);
        TransData transData = new TransData();
        m.addAttribute("dane",transData);
        try{
            String email = p.getName();
            Klient k = klientRepository.findByEmailEquals(email);

            if(k.getRole().equals("ADMIN")) return "pokazPrzystankiAdmin";
            else return"pokazPrzystanki";

        }catch (Exception e) {
            return "pokazPrzystanki";
        }
    }

    @RequestMapping("/przystanki/dodaj")
    public String dodajPrzystanek(Model m)
    {
        TransData nowyPrzystanek = new TransData();
        m.addAttribute("nowyPrzystanek", nowyPrzystanek);
        return "dodajPrzystanek";
    }

    @RequestMapping(value = "/przystanki/dodaj", method = RequestMethod.POST)
    public String dodajPrzystanek(Model m,TransData nowyPrzystanek)
    {
        String nazwa = nowyPrzystanek.getNazwa();
        int nrPeronu = Integer.parseInt(nowyPrzystanek.getNrPeronu());
        Przystanki p1 = new Przystanki(nazwa,nrPeronu);
        przystankiRepository.save(p1);

        m.addAttribute("header","Dodano przystanek");
        m.addAttribute("message",nazwa);
        return "message";
    }

    @RequestMapping("/przystanki/edit/{id}")
    public String edytujPrzystanek(Model m, @PathVariable int id)
    {
        try {
            Przystanki p = przystankiRepository.findByPrzystanekIdEquals(id);
            if(p.getNazwa().equals(""))
            {
                m.addAttribute("header","Wystąpił błąd");
                m.addAttribute("message","Nie znaleziono rekordu o takim ID");
                return "message";
            }
            m.addAttribute("przystanek", p);

            return "edytujPrzystanek";
        }catch (Exception e)
        {
            m.addAttribute("header","Wystąpił błąd");
            m.addAttribute("message","Nie znaleziono rekordu o takim ID");
            return "message";
        }
    }

    @RequestMapping(value = "/przystanki/zapiszZmiany",method = RequestMethod.POST)
    public String edytujPrzystanek(Model m, Przystanki przystanek)
    {
        try{
            przystankiRepository.save(przystanek);
            m.addAttribute("header","Sukces!");
            m.addAttribute("message","Zmiany zostały zapisane");
            return "message";
        }catch (Exception e)
        {
            m.addAttribute("header","Wystąpił błąd");
            m.addAttribute("message","Spróbuj ponownie");
            return "message";
        }
    }

    @RequestMapping("/przystanek/sprawdzPolaczenia/{id}")
    public String sprawdzPolaczenia(Model m,@PathVariable int id)
    {
        try {
            Przystanki p = przystankiRepository.findByPrzystanekIdEquals(id);
            if (p.getNazwa().equals("")) {
                m.addAttribute("header", "Wystąpił błąd");
                m.addAttribute("message", "Nie znaleziono rekordu o takim ID");
                return "message";

            }

            List<ListaPrzystankow> listaPrzystankow = listaPrzystankowRepository.findByPrzystanekEquals(p);

            Iterator i = listaPrzystankow.iterator();

            List<Odjazdy> odjazdy = new ArrayList<>();

            while (i.hasNext())
            {
                ListaPrzystankow l = (ListaPrzystankow) i.next();
                List<Odjazdy> odjazdyList = odjazdyRepository.findByListaEquals(l);

                Iterator i2 = odjazdyList.iterator();

                while (i2.hasNext())
                {
                    Odjazdy o = (Odjazdy) i2.next();
                    odjazdy.add(o);
                }

            }

            Iterator i2 = odjazdy.iterator();

            while (i2.hasNext())
            {
                Odjazdy o = (Odjazdy) i2.next();
                System.out.println(o.getDataGodzinaOdjazdu()+" "+o.getLista().getTrasa().getNazwaTrasy()+"\n");
            }


            m.addAttribute("lista",odjazdy);
            m.addAttribute("nazwaPrzystanku",p.getNazwa());
            return "przystankiPolaczenia";

        }catch (Exception e)
        {
            m.addAttribute("header", "Wystąpił błąd");
            m.addAttribute("message", "Nie znaleziono rekordu o takim ID");
            return "message";

        }


    }

    @RequestMapping("/sprawdzPolaczenia")
    public String polaczenia(Model m)
    {
        List<Przystanki> przystanki = przystankiRepository.findAll();
        TransData transData = new TransData();
        m.addAttribute("lista",przystanki);
        m.addAttribute("dane",transData);
        return "sprawdzPolaczenia";
    }

    @RequestMapping(value = "/sprawdzPolaczenia",method = RequestMethod.POST)
    public String polaczenia(Model m,TransData dane)
    {
        try {
            Przystanki p = przystankiRepository.findByPrzystanekIdEquals(Integer.parseInt(dane.getPrzystanekID()));
            List<ListaPrzystankow> listaPrzystankow = listaPrzystankowRepository.findByPrzystanekEquals(p);

            Iterator i = listaPrzystankow.iterator();

            List<Odjazdy> odjazdy = new ArrayList<>();

            while (i.hasNext())
            {
                ListaPrzystankow l = (ListaPrzystankow) i.next();
                List<Odjazdy> odjazdyList = odjazdyRepository.findByListaEquals(l);

                Iterator i2 = odjazdyList.iterator();

                while (i2.hasNext())
                {
                    Odjazdy o = (Odjazdy) i2.next();
                    odjazdy.add(o);
                }

            }

            Iterator i2 = odjazdy.iterator();

            while (i2.hasNext())
            {
                Odjazdy o = (Odjazdy) i2.next();
                System.out.println(o.getDataGodzinaOdjazdu()+" "+o.getLista().getTrasa().getNazwaTrasy()+"\n");
            }


            m.addAttribute("lista",odjazdy);
            m.addAttribute("nazwaPrzystanku",p.getNazwa());
            return "przystankiPolaczenia";


        }catch (Exception e)
        {
            m.addAttribute("header", "Wystąpił błąd");
            m.addAttribute("message", "Nie znaleziono rekordu o takim ID");
            return "message";
        }

    }

}
