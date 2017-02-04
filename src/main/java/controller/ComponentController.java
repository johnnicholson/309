package controller;

import model.Component;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import transactions.ComponentTransactions;

/**
 * Created by jnicho on 2/4/2017.
 */
@RestController
@RequestMapping(value = "/api/component")
public class ComponentController {

  @RequestMapping(value = "/{cmpId}", method = RequestMethod.GET)
  public static Component getComponent(@PathVariable(value="cmpId") int cmpId, MockHttpServletRequest req, MockHttpServletResponse res) {
    Component cmp = new ComponentTransactions.GetComponent(cmpId).run(req, res);
    return cmp;
  }
}
