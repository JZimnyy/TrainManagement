package com.ProjektJakubZimny.Projekt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class ListaPrzystankowController {

    @Autowired ListaPrzystankowRepository listaPrzystankowRepository;
    @Autowired TrasaRepository trasaRepository;
    @Autowired OdjazdyRepository odjazdyRepository;


    @RequestMapping("/trasy/odjazdy/{id}")
    public String pokazOdjazdy(@PathVariable int id, Model m)
    {
        Trasa t = trasaRepository.findByTrasaIdEquals(id);
        List<ListaPrzystankow> lista = listaPrzystankowRepository.findByTrasaEquals(t);

        Iterator iterator = lista.iterator();

        List<Odjazdy> odjazdy = new ArrayList<>();

        while (iterator.hasNext())
        {
            ListaPrzystankow l = (ListaPrzystankow) iterator.next();
           Iterator iterator2 = l.getOdjazdySet().iterator();

           while (iterator2.hasNext())
           {
               Odjazdy o = (Odjazdy) iterator2.next();
               odjazdy.add(o);
           }
        }

        Iterator iterator3 = odjazdy.iterator();

        while (iterator3.hasNext())
        {
                Odjazdy o = (Odjazdy) iterator3.next();
            System.out.println(o.getDataGodzinaOdjazdu()+" "+o.getListaPrzystankow().getPrzystanek().getNazwa() + " " + o.getListaPrzystankow().getTrasa().getNazwaTrasy());
        }

        return "message";
    }


}
