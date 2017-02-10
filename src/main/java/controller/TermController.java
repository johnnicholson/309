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

import model.Term;
import transactions.TermTransactions.DeleteTerm;
import transactions.TermTransactions.GetTerm;
import transactions.TermTransactions.PostTerm;
import transactions.TermTransactions.PutTerm;
import transactions.TermTransactions.GetAllTerms;


@RestController
@RequestMapping(value = "/api/term")
public class TermController {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public static Term getTerm(@PathVariable(value = "TermID") int termID, 
			HttpServletRequest req, HttpServletResponse res) {
		Term t = new GetTerm(termID).run(req, res);
		return t;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public static Integer postTerm(@RequestBody Term t, HttpServletRequest req,
			HttpServletResponse res) {
		Integer termID = new PostTerm(t).run(req, res);
		res.setHeader("Location", "/term" + termID);
		return termID;
	}
	
	@RequestMapping(value = "/term", method = RequestMethod.GET)
	public static List<Term> getTermList(HttpServletRequest req, HttpServletResponse res) {
		List<Term> ts = new GetAllTerms().run(req, res);
		return ts;
	}
	
	@RequestMapping(value = "/term/{TermID}", method = RequestMethod.PUT)
	public static Integer putTerm(@Valid @RequestBody Term t, 
			@PathVariable(value = "TermID") int tID, HttpServletRequest req, HttpServletResponse res) {
		Integer termID = new PutTerm(t, tID).run(req, res);
		return termID;
	}
	
	@RequestMapping(value = "/term/{TermID}", method = RequestMethod.DELETE)
	public static Integer deleteTerm(@PathVariable(value = "TermID") int tID, 
			HttpServletRequest req, HttpServletResponse res) {
		Integer termID = new DeleteTerm(tID).run(req, res);
		return termID;
	}
}
