package org.ai8.buytickets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String Login(ModelMap map, @RequestParam("mail") String mail,
                        @RequestParam("password") String password) {
        User user = userRepository.findOne(mail);
        map.addAttribute("data", "test");
        if(password.equals(user.mail)) {
            return "redirect:../ticket/index";
        } else {
            return "error";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String Register(@RequestParam("mail") String mail,
                           @RequestParam("nickname") String nickname,
                           @RequestParam("password") String password) {
        //邮箱不能重复
        if(userRepository.findOne(mail) != null)
        {
            return "error";
        }

        //邮箱、昵称或密码不能为空
        if(mail.equals("") || nickname.equals("") || password.equals(""))
        {
            return "error";
        }

        //新建用户
        User user = new User();
        user.mail = mail;
        user.nickname = nickname;
        user.password = password;
        userRepository.save(user);

        return "redirect:../ticket/index";
    }
}
