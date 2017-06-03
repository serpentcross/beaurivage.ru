package ru.beaurivage.systems.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.beaurivage.systems.management.dao.RecordDAO;
import ru.beaurivage.systems.management.model.ApiMember;
import ru.beaurivage.systems.management.model.Record;
import ru.beaurivage.systems.management.model.Response;
import ru.beaurivage.systems.management.model.Some;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/welcome")
public class MainController {

    @Autowired RecordDAO recordDAO;

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    public void getSomething(@RequestBody Some some) {

        System.out.println(some.getSomestring());
    }


    @RequestMapping(value = "/makerecord", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes="application/json")
    public Response postRecord(@RequestBody Record record) throws ParseException {
        return recordDAO.save(record);
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

    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public ResponseEntity<List<Record>> loadMessages() throws DataAccessException {
        List<Record> messages = recordDAO.loadAllRecords();
        if(messages.isEmpty()){
            return new ResponseEntity<List<Record>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Record>>(messages, HttpStatus.OK);
    }
}
