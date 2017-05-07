package ru.beaurivage.systems.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.beaurivage.systems.management.dao.CustomerRecordDAO;
import ru.beaurivage.systems.management.model.ApiMember;
import ru.beaurivage.systems.management.model.CustomerRecord;
import ru.beaurivage.systems.management.model.Record;
import ru.beaurivage.systems.management.model.Some;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/welcome")
public class MainController {

    @Autowired CustomerRecordDAO customerRecordDAO;

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    public void getSomething(@RequestBody Some some) {

        System.out.println(some.getSomestring());
    }


    @RequestMapping(value = "/makerecord", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void postRecord(@ModelAttribute Record record) throws ParseException {
        System.out.println("MAKE RECORD");
        System.out.println("sdsdf");
        System.out.println(record);
    }

    @RequestMapping(value = "/api/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void signUp(@ModelAttribute ApiMember apiMember) {
        System.out.println(apiMember.getEmail());
        System.out.println(apiMember.getPassword());
        System.out.println(apiMember.getUsername());
    }

    @RequestMapping(value="/csrf-token", method=RequestMethod.GET)
    public @ResponseBody
    String getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
        return token.getToken();
    }
}
