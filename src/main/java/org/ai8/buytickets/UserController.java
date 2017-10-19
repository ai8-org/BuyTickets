package org.ai8.buytickets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(ModelMap map, HttpSession session) {
        List<User> userList = userRepository.findBySession(session.getId());
        User user;
        if(userList.size() == 1) {
            user = userList.get(0);
        } else {
            user = new User();
        }
        map.addAttribute("user", user);

        List<Ticket> ticketList = new ArrayList<Ticket>();
        if(user.tickets == null) {
            map.addAttribute("tickets", ticketList);
            return "cart";
        }
        if(user.tickets.contains("-")) {
            for(String s:user.tickets.split("-")) {
                ticketList.add(ticketRepository.findOne(Integer.parseInt(s)));
            }
        } else if(user.tickets.length() > 0) {
            ticketList.add(ticketRepository.findOne(Integer.parseInt(user.tickets)));
        }
        map.addAttribute("tickets", ticketList);
        return "cart";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String Login(ModelMap map, @RequestParam("mail") String mail,
                        @RequestParam("password") String password,
                        HttpSession session) {
        User user = userRepository.findOne(mail);
        if(password.equals(user.password)) {
            user.session = session.getId();
            userRepository.save(user);
            map.addAttribute("data", "login-success");
            return "redirect:/";
        } else {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String Register(@RequestParam("mail") String mail,
                           @RequestParam("nickname") String nickname,
                           @RequestParam("password") String password,
                           HttpSession session, ModelMap map) {
        //邮箱不能重复
        if(userRepository.findOne(mail) != null)
        {
            return "redirect:/error";
        }

        //邮箱、昵称或密码不能为空
        if(mail.equals("") || nickname.equals("") || password.equals(""))
        {
            return "redirect:/error";
        }

        //新建用户
        User user = new User();
        user.mail = mail;
        user.nickname = nickname;
        user.password = password;
        user.role = "user";
        user.tickets = "";
        user.session = session.getId();
        userRepository.save(user);

        map.addAttribute("data", "register-success");
        return "redirect:/";
    }
}
