package controller;

import model.Component;
import org.springframework.web.bind.annotation.*;
import transactions.ComponentTransactions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jnicho on 2/4/2017.
 */
@RestController
@RequestMapping(value = "/api/component")
public class ComponentController {

  @RequestMapping(value = "/{cmpId}", method = RequestMethod.GET)
  public static Component getComponent(@PathVariable(value="cmpId") int cmpId, HttpServletRequest req, HttpServletResponse res) {
    Component cmp = new ComponentTransactions.GetComponent(cmpId).run(req, res);
    return cmp;
  }

  @RequestMapping(value = "/{cmpId}", method = RequestMethod.PUT)
  public static void editComponent(@RequestBody Component cmp, @PathVariable(value="cmpId") int cmpId, HttpServletRequest req, HttpServletResponse res) {
    new ComponentTransactions.PutComponent(cmp, cmpId).run(req, res);
  }

}
