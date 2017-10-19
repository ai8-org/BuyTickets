package org.ai8.buytickets;

import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    private EntityManager em;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map, HttpSession session) {
        List<User> userList = userRepository.findBySession(session.getId());
        User user;
        if(userList.size() == 1) {
            user = userList.get(0);
        } else {
            user = new User();
        }
        map.addAttribute("user", user);

        List<Ticket> ticket = ticketRepository.findAll();
        map.addAttribute("data", ticket);
        return "index";
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String query(ModelMap map, HttpSession session,
                        @RequestParam("from_place") String from_place,
                        @RequestParam("to_place") String to_place,
                        @RequestParam("date") String date) {
        List<User> userList = userRepository.findBySession(session.getId());
        User user;
        if(userList.size() == 1) {
            user = userList.get(0);
        } else {
            user = new User();
            user.mail = "";
        }
        map.addAttribute("user", user);

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

        return "index";
    }
}
