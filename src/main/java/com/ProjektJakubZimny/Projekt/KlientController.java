package com.ProjektJakubZimny.Projekt;


import org.attoparser.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.TextUtils;

import java.security.Principal;
import java.util.List;

@Controller
public class KlientController {

    @Autowired
    KlientRepository klientRepository;

    @RequestMapping("/klienci")
    public String pokazKlientow(Model m,Principal p)
    {
        Klient k = klientRepository.findByEmailEquals(p.getName());

        TransData dane = new TransData();
        m.addAttribute("dane",dane);
        List<Klient> listaKlientow = klientRepository.findAll();
        m.addAttribute("naglowek","Lista klientów");
        m.addAttribute("lista",listaKlientow);
        return "pokazKlientow";
    }

    @RequestMapping(value = "/klienci",method = RequestMethod.POST)
    public String pokazKlientow(Model m,TransData dane,Principal p)
    {
        Klient k = klientRepository.findByEmailEquals(p.getName());

        List<Klient> lista;
        if((!dane.getImie().equals(""))&&(!dane.getNazwisko().equals("")))
        {
              lista = klientRepository.findByImieEqualsAndNazwiskoEquals(dane.getImie(),dane.getNazwisko());
        }else if((dane.getImie().equals(""))&&(!dane.getNazwisko().equals("")))
        {
             lista = klientRepository.findByNazwiskoEquals(dane.getNazwisko());
        }else if((!dane.getImie().equals(""))&&(dane.getNazwisko().equals("")))
        {
            lista = klientRepository.findByImieEquals(dane.getImie());
        }else lista = klientRepository.findAll();

        TransData t1 = new TransData();
        m.addAttribute("dane",t1);
        m.addAttribute("naglowek","Lista klientów");
        m.addAttribute("lista",lista);
        return "pokazKlientow";
    }

    @RequestMapping("/klienci/edit/{id}")
    public String edytujKlienta(@PathVariable int id,Model m)
    {
        try {
            Klient klient = klientRepository.findByIdEquals(id);
            if(klient.getEmail().equals(""))
            {
                m.addAttribute("header", "Błąd");
                m.addAttribute("message", "Nie ma takiego klienta");
                return "message";
            }

            m.addAttribute("nowyKlient", klient);
            return "edytujKlienta";
        }catch (Exception e)
        {
            m.addAttribute("header", "Błąd");
            m.addAttribute("message", "Nie ma takiego klienta");
            return "message";
        }
    }


    @RequestMapping(value = "/zapiszZmiany",method = RequestMethod.POST)
    public String zapiszZmiany(Model m, Klient nowyKlient)
    {

        klientRepository.save(nowyKlient);
        m.addAttribute("header","Sukces");
        m.addAttribute("message","Zmiany zostały zapisane");
        return "message";
    }

    @RequestMapping("/klienci/details/{id}")
    public String szczegolyKlienta(@PathVariable int id,Model m)
    {
        try {
            Klient k = klientRepository.findByIdEquals(id);
            if(k.getEmail().equals(""))
            {
                m.addAttribute("header", "Błąd");
                m.addAttribute("message", "Nie ma takiego klienta");
                return "message";
            }
            m.addAttribute("klient", k);
            return "szczegolyKlient";
        }catch (Exception e)
        {
            m.addAttribute("header", "Błąd");
            m.addAttribute("message", "Nie ma takiego biletu");
            return "message";
        }
    }

    @RequestMapping(value = "/rejestracja",method = RequestMethod.GET)
    public String addUser(Model m)
    {
        TransData nowyKlient = new TransData();
        m.addAttribute("nowyKlient",nowyKlient);
        return "dodajKlienta";
    }

    @RequestMapping(value = "/rejestracja", method = RequestMethod.POST)
    public String addUserPost(Model m,TransData nowyKlient)
    {

        String imie = nowyKlient.getImie();
        String nazwisko = nowyKlient.getNazwisko();
        String pesel = nowyKlient.getPESEL();
        String nrTelefonu = nowyKlient.getNrTelefonu();
        String miejscowosc = nowyKlient.getMiejscowosc();
        String nrDomu = nowyKlient.getNrDomu();
        String kodPocztowy = nowyKlient.getKodPocztowy();
        String email = nowyKlient.getEmail();
        String haslo = nowyKlient.getHaslo();

        Klient k = klientRepository.findByEmailEquals(email);
        if(!k.getEmail().equals(""))
        {
            m.addAttribute("message","Ten email jest już używany");
            m.addAttribute("nowyKlient",nowyKlient);
            return "dodajKlienta";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(haslo);

        klientRepository.save(new Klient(imie,nazwisko,pesel,nrTelefonu,miejscowosc,nrDomu,kodPocztowy,email,password));


        return "/login";
    }
    @RequestMapping(value = "/rejestracjaAdmin",method = RequestMethod.GET)
    public String addAdmin(Model m)
    {
        TransData nowyKlient = new TransData();
        m.addAttribute("nowyKlient",nowyKlient);
        return "dodajAdmina";
    }

    @RequestMapping(value = "/rejestracjaAdmin", method = RequestMethod.POST)
    public String addAdmin(Model m,TransData nowyKlient)
    {
        String imie = nowyKlient.getImie();
        String nazwisko = nowyKlient.getNazwisko();
        String pesel = nowyKlient.getPESEL();
        String nrTelefonu = nowyKlient.getNrTelefonu();
        String miejscowosc = nowyKlient.getMiejscowosc();
        String nrDomu = nowyKlient.getNrDomu();
        String kodPocztowy = nowyKlient.getKodPocztowy();
        String email = nowyKlient.getEmail();
        String haslo = nowyKlient.getHaslo();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(haslo);

        Klient admin = new Klient(imie,nazwisko,pesel,nrTelefonu,miejscowosc,nrDomu,kodPocztowy,email,password);
        admin.setRole("ADMIN");
        klientRepository.save(admin);


        return "/adminPanel";
    }

    @RequestMapping("/mojeKonto")
    public String myAccount(Model m, Principal principal)
    {
        String email = principal.getName();
        Klient klient = klientRepository.findByEmailEquals(email);

        m.addAttribute("klient",klient);
        return "mojeKonto";
    }

    @RequestMapping("/mojeKonto/edytuj")
    public String myAccountEdit(Model m, Principal p)
    {
        String email = p.getName();
        Klient klient = klientRepository.findByEmailEquals(email);

        m.addAttribute("nowyKlient",klient);
        return "edytujDane";

    }

    @RequestMapping("/mojeKonto/zmianaHasla")
    public String myAccountPassChange(Model m)
    {
        TransData transData = new TransData();
        m.addAttribute("dane",transData);
        return "zmienHaslo";
    }

    @RequestMapping(value = "/mojeKonto/zmianaHasla",method = RequestMethod.POST)
    public String myAccountPassChange(Model m,Principal principal,TransData dane)
    {
        if(dane.getHaslo().equals(dane.getHaslo2()))
        {
            String email = principal.getName();
            Klient k = klientRepository.findByEmailEquals(email);

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(dane.getHaslo());

            k.setHaslo(password);
            klientRepository.save(k);

            return "zmienionoHaslo";
        }else
        {
            m.addAttribute("error","Hasła muszą się zgadzać");
            TransData transData = new TransData();
            m.addAttribute("dane",transData);
            return "zmienHaslo";
        }


    }




    @ExceptionHandler
    public String handlerException(Model m,Exception e)
    {

        return "message";
    }

    @RequestMapping("/403")
    public String error(Model m)
    {


            m.addAttribute("header","Wystąpił błąd");
            m.addAttribute("message","Brak dostępu do strony");
            return "message";

    }

}
