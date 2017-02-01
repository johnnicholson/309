package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.ComponentType;
import transactions.ComponentTypeTransactions.GetComponentType;
import transactions.ComponentTypeTransactions.GetComponentTypeList;
import transactions.ComponentTypeTransactions.PostComponentType;

@RestController
@RequestMapping(value = "/api/component")
public class ComponentController {
	@RequestMapping(value = "/type/{CmpTypeID}", method = RequestMethod.GET)
	public static ComponentType getComponentType(@PathVariable(value = "CmpTypeID") int cmpTypeID, HttpServletRequest req, HttpServletResponse res) {
		ComponentType ct = new GetComponentType(cmpTypeID).run(req, res);
		return ct;
	}
	
	@RequestMapping(value = "/type", method = RequestMethod.POST)
	public static Integer postComponentType(@RequestBody ComponentType ct, HttpServletRequest req,
			HttpServletResponse res) {
		Integer cmpTypeID = new PostComponentType(ct).run(req, res);
		res.setHeader("Location", "/component" + cmpTypeID);
		return cmpTypeID;
	}
	
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public static List<ComponentType> getComponentTypeList(HttpServletRequest req, HttpServletResponse res) {
		List<ComponentType> cts = new GetComponentTypeList().run(req, res);
		return cts;
	}
}
