package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import model.CoursePref;
import model.TimePref;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.Person;
import transactions.PersonTransactions;
import transactions.PersonTransactions.ChangePassword;
import transactions.PersonTransactions.GetAllPeople;
import transactions.PersonTransactions.GetPerson;
import transactions.PersonTransactions.PostPerson;
import transactions.PersonTransactions.PutPerson;

@RestController
@RequestMapping(value = "/api/prss")
public class PersonController {

  public static class PasswordChange {
    public String newPassword;
    public String oldPassword;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public static List<Person> getAllPeople(HttpServletRequest req, HttpServletResponse res) {
    List<Person> prss = new GetAllPeople().run(req, res);

    return prss;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public static void postPerson(@RequestBody Person person, HttpServletRequest req,
      HttpServletResponse res) {
    Integer prsId = new PostPerson(person).run(req, res);
    res.setHeader("Location", "prss/" + prsId);
    return;
  }

  @RequestMapping(value = "/{PrsId}", method = RequestMethod.PUT)
  public static void putPerson(@Valid @RequestBody Person person,
      @PathVariable(value = "PrsId") int prsId, HttpServletRequest req, HttpServletResponse res) {
    new PutPerson(person, prsId).run(req, res);
    return;
  }

  @RequestMapping(value = "/{PrsId}", method = RequestMethod.GET)
  public static Person getPerson(@PathVariable(value = "PrsId") int prsId, HttpServletRequest req,
      HttpServletResponse res) {
    Person p = new GetPerson(prsId).run(req, res);
    return p;
  }

  @RequestMapping(value = "/{PrsId}/timeprefs", method = RequestMethod.GET)
  public static List<TimePref> getPersonTimePrefs(@PathVariable(value = "PrsId") int prsId, HttpServletRequest req,
      HttpServletResponse res) {
    List<TimePref> prefs = new PersonTransactions.GetPersonTimePrefs(prsId).run(req, res);
    return prefs;
  }

  @RequestMapping(value = "/{PrsId}/courseprefs", method = RequestMethod.GET)
  public static List<CoursePref> getPersonCoursePrefs(@PathVariable(value = "PrsId") int prsId, HttpServletRequest req,
      HttpServletResponse res) {
    List<CoursePref> prefs = new PersonTransactions.GetPersonCoursePrefs(prsId).run(req, res);
    return prefs;
  }

  @RequestMapping(value = "/{PrsId}", method = RequestMethod.DELETE)
  public static void deletePerson(@PathVariable(value = "PrsId") int prsId, HttpServletRequest req,
      HttpServletResponse res) {
    new PersonTransactions.DeletePerson(prsId).run(req, res);
    return;
  }

  @RequestMapping(value = "/{PrsId}/password", method = RequestMethod.PUT)
  public static Integer changePassword(@PathVariable(value = "PrsId") int prsId,
      @RequestBody PasswordChange pwChange, HttpServletRequest req, HttpServletResponse res) {
    return new ChangePassword(pwChange, prsId).run(req, res);
  }

}
