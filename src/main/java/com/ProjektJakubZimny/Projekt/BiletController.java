package com.ProjektJakubZimny.Projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.Principal;
import java.util.List;


@Controller
public class BiletController {

    @Autowired
    TrasaRepository trasaRepository;

    @Autowired
    BiletRepository biletRepository;

    @Autowired
    OdjazdyRepository odjazdyRepository;

    @Autowired
    ListaPrzystankowRepository listaPrzystankowRepository;

    @Autowired
    KlientRepository klientRepository;

    @RequestMapping("/bilet/trasa")
    public String wybierzTrase(Model m)
    {
        TransData nowyBilet = new TransData();
        List<Trasa> trasy = trasaRepository.findAll();
        m.addAttribute("nowyBilet",nowyBilet);
        m.addAttribute("listaTras",trasy);
        return "wybierzTrase";
    }

    @RequestMapping(value = "/bilet/trasa/przystanki",method = RequestMethod.POST)
    public String wybierzPrzystanki(Model m, TransData nowyBilet)
    {
        try {
            int idTrasy = Integer.parseInt(nowyBilet.getTrasaID());
            Trasa t = trasaRepository.findByTrasaIdEquals(idTrasy);
            List<ListaPrzystankow> listaPrzystankow = listaPrzystankowRepository.findByTrasaEquals(t);
            String nazwaTrasy = t.getNazwaTrasy();
            m.addAttribute("nowyBilet", nowyBilet);
            m.addAttribute("listaPrzystankow", listaPrzystankow);
            m.addAttribute("nazwaTrasy", nazwaTrasy);
            return "wybierzPrzystanki";
        }catch (Exception e)
        {
            m.addAttribute("header", "Błąd");
            m.addAttribute("message", "Spróbuj ponownie");
            return "message";
        }
    }

    @RequestMapping(value = "/bilet/trasa/przystanki/odjazdy",method = RequestMethod.POST)
    public String wybierzKurs(Model m,TransData nowyBilet)
    {
        try {

            int idPocz = Integer.parseInt(nowyBilet.getPrzystanekPoczatkowy());
            ListaPrzystankow l1 = listaPrzystankowRepository.findByListaIDEquals(idPocz);
            List<Odjazdy> listaOdjazdowPocz = odjazdyRepository.findByListaEquals(l1);
            int idKon = Integer.parseInt(nowyBilet.getPrzystanekKoncowy());
            ListaPrzystankow l2 = listaPrzystankowRepository.findByListaIDEquals(idKon);

            if(l1.getNrPrzystanku()==l2.getNrPrzystanku())
            {
                m.addAttribute("header", "Błąd");
                m.addAttribute("message", "Nie można wybrać tych samych przystanków");
                return "message";
            }

            if(l1.getNrPrzystanku()==l2.getNrPrzystanku())
            {
                m.addAttribute("header", "Błąd");
                m.addAttribute("message", "Nie można wybrać przystanków w tej kolejności");
                return "message";
            }

            List<Odjazdy> listaOdjazdowKon = odjazdyRepository.findByListaEquals(l2);
            Trasa t = trasaRepository.findByTrasaIdEquals(Integer.parseInt(nowyBilet.getTrasaID()));
            m.addAttribute("listaPocz", listaOdjazdowPocz);
            m.addAttribute("listaKon", listaOdjazdowKon);
            m.addAttribute("nowyBilet", nowyBilet);
            m.addAttribute("nazwaPrzysPocz", l1.getPrzystanek().getNazwa());
            m.addAttribute("nazwaPrzysKon", l2.getPrzystanek().getNazwa());
            m.addAttribute("nazwaTrasy", t.getNazwaTrasy());
            System.out.println(nowyBilet.getTrasaID());
            return "wybierzOdjazd";
        }catch (Exception e)
        {
            m.addAttribute("header", "Błąd");
            m.addAttribute("message", "Nie ma takiego biletu");
            return "message";
        }
    }

    @RequestMapping(value = "/bilet/potwierdz",method = RequestMethod.POST)
    public String potwierdzBilet(Model m,TransData nowyBilet, Principal principal)
    {
        try {
            Odjazdy o1 = odjazdyRepository.findByOdjazdIdEquals(Integer.parseInt(nowyBilet.getOdjazdId()));
            int nrKursu = o1.getNrKursu();
            System.out.println(nowyBilet.getPrzystanekKoncowy());
            int idKon = Integer.parseInt(nowyBilet.getPrzystanekKoncowy());
            ListaPrzystankow l2 = listaPrzystankowRepository.findByListaIDEquals(idKon);
            Odjazdy o2 = odjazdyRepository.findByListaEqualsAndNrKursuEquals(l2, nrKursu);

            //dodać wybór klienta do którego będzie przypisany bilet
            //dodać potwierdzenie biletu tj.wyswietlic i sprawdzic czy wszystko sie zgadza
            System.out.println(">>>KLIENT ID>>>" + nowyBilet.getKlientId());
            Trasa t = trasaRepository.findByTrasaIdEquals(Integer.parseInt(nowyBilet.getTrasaID()));
            String nazwaTrasy = t.getNazwaTrasy();

            String nazwaPrzysPocz = o1.getListaPrzystankow().getPrzystanek().getNazwa();
            String nazwaPrzysKon = o2.getListaPrzystankow().getPrzystanek().getNazwa();

            String dataOdjazdu = o1.getDataGodzinaOdjazdu();
            String dataPrzyjazdu = o2.getDataGodzinaOdjazdu();

            String email = principal.getName();

            Klient klient = klientRepository.findByEmailEquals(email);
            m.addAttribute("nazwaTrasy", nazwaTrasy);
            m.addAttribute("nazwaPrzysPocz", nazwaPrzysPocz);
            m.addAttribute("nazwaPrzysKon", nazwaPrzysKon);
            m.addAttribute("dataOdjazdu", dataOdjazdu);
            m.addAttribute("dataPrzyjazdu", dataPrzyjazdu);
            m.addAttribute("klient", klient);


            m.addAttribute("nowyBilet", nowyBilet);
            return "potwierdzBilet";
        }catch (Exception e)
        {
            m.addAttribute("header", "Błąd");
            m.addAttribute("message", "Nie ma takiego biletu");
            return "message";
        }
    }

    @RequestMapping(value = "/bilet/ok",method = RequestMethod.POST)
    public String zapiszBilet(Model m,TransData nowyBilet, Principal principal)
    {
        try {
            Odjazdy o1 = odjazdyRepository.findByOdjazdIdEquals(Integer.parseInt(nowyBilet.getOdjazdId()));
            int nrKursu = o1.getNrKursu();
            System.out.println(nowyBilet.getPrzystanekKoncowy());
            int idKon = Integer.parseInt(nowyBilet.getPrzystanekKoncowy());
            ListaPrzystankow l2 = listaPrzystankowRepository.findByListaIDEquals(idKon);
            Odjazdy o2 = odjazdyRepository.findByListaEqualsAndNrKursuEquals(l2, nrKursu);

            Trasa t = trasaRepository.findByTrasaIdEquals(Integer.parseInt(nowyBilet.getTrasaID()));
            String nazwaTrasy = t.getNazwaTrasy();

            String nazwaPrzysPocz = o1.getListaPrzystankow().getPrzystanek().getNazwa();
            String nazwaPrzysKon = o2.getListaPrzystankow().getPrzystanek().getNazwa();

            String dataOdjazdu = o1.getDataGodzinaOdjazdu();
            String dataPrzyjazdu = o2.getDataGodzinaOdjazdu();

            String email = principal.getName();

            Klient klient = klientRepository.findByEmailEquals(email);

            float cena = 5 * (float) (o2.getListaPrzystankow().getNrPrzystanku() - o1.getListaPrzystankow().getNrPrzystanku());
            System.out.println(cena);
            Bilet bilet = new Bilet(klient, t, o1, o2, cena);


            // biletRepository.save(bilet);
            System.out.println(bilet.getCena() + " " + bilet.getKlient().getImie());


            biletRepository.save(bilet);
            return "biletOK";
        }catch (Exception e)
        {
            m.addAttribute("header", "Błąd");
            m.addAttribute("message", "Nie ma takiego biletu");
            return "message";
        }
    }

    @RequestMapping("/bilety/moje")
    public String mojeBilety(Model m, Principal principal)
    {

        String email = principal.getName();
        Klient k = klientRepository.findByEmailEquals(email);
        List<Bilet> bilety = biletRepository.findByKlientEquals(k);
        String naglowek = "Lista biletów użytkownika " + k.getImie()+" "+k.getNazwisko()+":";
        List<Trasa> trasy = trasaRepository.findAll();
        m.addAttribute("listaTras",trasy);
        TransData transData = new TransData();
        m.addAttribute("dane",transData);
        m.addAttribute("naglowek",naglowek);
        m.addAttribute("bilety",bilety);
        return "mojeBilety";
    }

    @RequestMapping(value = "/bilety/moje",method = RequestMethod.POST)
    public String mojeBilety(Model m,Principal principal,TransData dane)
    {
        try {
            String email = principal.getName();
            Klient k = klientRepository.findByEmailEquals(email);
            Trasa t = trasaRepository.findByTrasaIdEquals(Integer.parseInt(dane.getTrasaID()));
            List<Bilet> bilety = biletRepository.findByKlientEqualsAndWybranaTrasaEquals(k, t);
            String naglowek = "Lista biletów użytkownika " + k.getImie() + " " + k.getNazwisko() + ":";
            List<Trasa> trasy = trasaRepository.findAll();
            m.addAttribute("listaTras", trasy);
            TransData transData = new TransData();
            m.addAttribute("dane", transData);
            m.addAttribute("naglowek", naglowek);
            m.addAttribute("bilety", bilety);
            return "mojeBilety";
        }catch (Exception e)
        {
            m.addAttribute("header", "Wystąpił łąd");
            m.addAttribute("message", "Spróbuj ponownie");
            return "message";
        }
    }

    @RequestMapping("/bilety")
    public String pokazBilety(Model m)
    {
        List<Bilet> bilety = biletRepository.findAll();
        m.addAttribute("bilety",bilety);
        return "pokazBilety";
    }

    @RequestMapping("/mojeBilety/details/{id}")
    public String szczegolyBilet(@PathVariable int id,Model m,Principal p)
    {
        Bilet bilet = biletRepository.findByIdEquals(id);
        try {
            if ((!bilet.getKlient().getEmail().equals(p.getName()))) {
                m.addAttribute("header", "Błąd");
                m.addAttribute("message", "Brak dostępu");
                return "message";
            }
            m.addAttribute("bilet", bilet);
            return "szczegolyBilet";
        }catch (Exception e)
        {
            m.addAttribute("header", "Błąd");
            m.addAttribute("message", "Nie ma takiego biletu");
            return "message";
        }
    }

    @RequestMapping("/mojeBilety/detailsAdmin/{id}")
    public String szczegolyBiletAdmin(@PathVariable int id,Model m,Principal p)
    {
        Bilet bilet = biletRepository.findByIdEquals(id);
        try {

            m.addAttribute("bilet", bilet);
            return "szczegolyBilet";
        }catch (Exception e)
        {
            m.addAttribute("header", "Błąd");
            m.addAttribute("message", "Nie ma takiego biletu");
            return "message";
        }
    }
}
