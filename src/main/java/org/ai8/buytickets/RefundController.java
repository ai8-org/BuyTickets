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
public class RefundController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/refund", method = RequestMethod.GET)
    public String index(ModelMap map, HttpSession session,
                        @RequestParam("id") Integer ticketId) {
        List<User> userList = userRepository.findBySession(session.getId());
        User user;
        if(userList.size() == 1) {
            user = userList.get(0);
        } else {
            user = new User();
            user.mail = "";
        }
        map.addAttribute("user", user);

        if(!user.mail.equals(null)) {
            String userTicket = "";

            if(user.tickets.equals("")) {
                return "redirect:/";
            }

            for(String ticketStrId:user.tickets.split("-")) {
                if(!ticketId.toString().equals(ticketStrId)) {
                    userTicket += ticketStrId + "-";
                }
            }

            userTicket = userTicket.split("-")[0];
            if(userTicket == null || userTicket.length() == 0) {
                user.tickets = "";
            } else {
                user.tickets = userTicket;
            }
            userRepository.save(user);
        }

        return "redirect:/user/cart";
    }
}
