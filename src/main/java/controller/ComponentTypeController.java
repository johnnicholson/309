package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.ComponentType;
import transactions.ComponentTypeTransactions.DeleteComponentType;
import transactions.ComponentTypeTransactions.GetComponentType;
import transactions.ComponentTypeTransactions.GetComponentTypeList;
import transactions.ComponentTypeTransactions.PostComponentType;
import transactions.ComponentTypeTransactions.PutComponentType;

/* This class is the Controller for the Component Type methods
 * 
 * Christiana Ushana & John Nicholson
 * Created on Feb 8 2017
 */
@RestController
@RequestMapping(value = "/api/component")
public class ComponentTypeController {
	/* GET Method
	 * This method takes in a path variable, an component type ID, a request, and a response. 
	 * It will return a component type object.
	 */
	@RequestMapping(value = "/type/{CmpTypeID}", method = RequestMethod.GET)
	public static ComponentType getComponentType(@PathVariable(value = "CmpTypeID") int cmpTypeID, 
			HttpServletRequest req, HttpServletResponse res) {
		ComponentType ct = new GetComponentType(cmpTypeID).run(req, res);
		return ct;
	}
	
	/* POST Method
	 * This method takes in a component type object, a request, and a response.
	 * It will create a new component type object and add it to the database.
	 * It will return the integer value of the component type ID.
	 */
	@RequestMapping(value = "/type", method = RequestMethod.POST)
	public static Integer postComponentType(@RequestBody ComponentType ct, HttpServletRequest req,
			HttpServletResponse res) {
		Integer cmpTypeID = new PostComponentType(ct).run(req, res);
		res.setHeader("Location", "/component" + cmpTypeID);
		return cmpTypeID;
	}
	
	/* GET (List) Method
	 * This method takes in a request and a response. 
	 * It will return a list of component type objects.
	 */
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public static List<ComponentType> getComponentTypeList(HttpServletRequest req, HttpServletResponse res) {
		List<ComponentType> cts = new GetComponentTypeList().run(req, res);
		return cts;
	}
	
	/* PUT Method
	 * This method takes in a component type object, a path variable, a request, and a response.
	 * It will reassign the new values of the component type object to the old component type object.
	 * It will return the integer value of the component type ID.
	 */
	@RequestMapping(value = "/type/{CmpTypeID}", method = RequestMethod.PUT)
	public static Integer putComponentType(@Valid @RequestBody ComponentType ct, 
			@PathVariable(value = "CmpTypeID") int cmpId, HttpServletRequest req, HttpServletResponse res) {
		Integer cmpTypeID = new PutComponentType(ct, cmpId).run(req, res);
		return cmpTypeID;
	}
	
	/* DELETE Method
	 * This method takes in a component type ID, a request, and a response. 
	 * It will delete the component type object from the database.
	 * It will return the integer value of the component type ID.
	 */
	@RequestMapping(value = "/type/{CmpTypeID}", method = RequestMethod.DELETE)
	public static Integer deleteComponentType(@PathVariable(value = "CmpTypeID") int cmpId, 
			HttpServletRequest req, HttpServletResponse res) {
		Integer cmpTypeID = new DeleteComponentType(cmpId).run(req, res);
		return cmpTypeID;
	}
}