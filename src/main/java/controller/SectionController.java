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

import model.Section;
import transactions.SectionTransactions.DeleteSection;
import transactions.SectionTransactions.GetSection;
import transactions.SectionTransactions.PostSection;
import transactions.SectionTransactions.PutSection;
import transactions.SectionTransactions.GetAllSections;


@RestController
@RequestMapping(value = "/api/section")
public class SectionController {
	@RequestMapping(value = "/{SectionID}", method = RequestMethod.GET)
	public static Section getSection(@PathVariable(value = "SectionID") int sectID, 
			HttpServletRequest req, HttpServletResponse res) {
		Section s = new GetSection(sectID).run(req, res);
		return s;
	}


	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public static List<Section> getSectionList(HttpServletRequest req, HttpServletResponse res) {
		List<Section> sects = new GetAllSections().run(req, res);
		return sects;
	}
	
	@RequestMapping(value = "/{SectionID}", method = RequestMethod.PUT)
	public static Integer putSection(@Valid @RequestBody Section sect, 
			@PathVariable(value = "SectionID") int sectID, HttpServletRequest req, HttpServletResponse res) {
		Integer sID = new PutSection(sect, sectID).run(req, res);
		return sID;
	}
	
	@RequestMapping(value = "/{SectionID}", method = RequestMethod.DELETE)
	public static Integer deleteSection(@PathVariable(value = "SectionID") int sectID, 
			HttpServletRequest req, HttpServletResponse res) {
		Integer sID = new DeleteSection(sectID).run(req, res);
		return sID;
	}
}
