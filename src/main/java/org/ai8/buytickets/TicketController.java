package org.ai8.buytickets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/ticket", method = RequestMethod.GET)
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    private EntityManager em;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap map) {
        List<Ticket> ticket = ticketRepository.findAll();
        map.addAttribute("data", ticket);
        return "ticket";
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String query(ModelMap map, @RequestParam("from_place") String from_place,
                        @RequestParam("to_place") String to_place,
                        @RequestParam("date") String date) {
        String station = from_place + "-" + to_place;
//        String a = "select * from ticket";
//        Query query = em.createNativeQuery(a);
//        query.setParameter(1, station);
        List<Ticket> ticketList = new ArrayList<Ticket>();
        for(Object ticket:ticketRepository.findByStation("1"))
        {
            System.out.println("Hi " + ((Ticket)ticket).id);
            ticketList.add((Ticket)ticket);
        }
        map.addAttribute("data", ticketList);

        return "ticket";
    }
}
