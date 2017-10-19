package org.ai8.buytickets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BuyController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    public String index(ModelMap map, HttpSession session,
                        @RequestParam("id") Integer ticketId) {
        List<User> userList = userRepository.findBySession(session.getId());
        User user;
        if(userList.size() == 1) {
            user = userList.get(0);
        } else {
            return "redirect:/error";
        }

        String userTicket = user.tickets;

        if(userTicket == null || userTicket.equals("")) {
            userTicket = ticketId.toString();
        } else {
            if(userTicket.contains("-")) {
                for(String s:userTicket.split("-")) {
                    if(s.equals(ticketId)) {
                        return "redirect:/error";
                    }
                }
            }

            userTicket += "-" + ticketId;
        }

        user.tickets = userTicket;
        userRepository.save(user);
        return "redirect:/user/cart";
    }
}
