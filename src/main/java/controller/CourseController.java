package controller;

import model.Component;
import model.Course;
import org.springframework.web.bind.annotation.*;
import transactions.ComponentTransactions;
import transactions.CourseTransactions;
import transactions.CourseTransactions.GetAllCourses;
import transactions.CourseTransactions.GetCourse;
import transactions.CourseTransactions.PostCourse;
import transactions.CourseTransactions.PutCourse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/course")
public class CourseController {
  @RequestMapping(value = "", method = RequestMethod.GET)
  public static List<Course> getAllCourses(HttpServletRequest req, HttpServletResponse res) {
    List<Course> Course = new GetAllCourses().run(req, res);

    return Course;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public static void postCourse(@RequestBody Course Course, HttpServletRequest req,
      HttpServletResponse res) {
    Integer CourseId = new PostCourse(Course).run(req, res);
    res.setHeader("Location", "Course/" + CourseId);
    return;
  }

  @RequestMapping(value = "/{CourseId}", method = RequestMethod.PUT)
  public static void putCourse(@Valid @RequestBody Course Course,
      @PathVariable(value = "CourseId") int CourseId, HttpServletRequest req, HttpServletResponse res) {
    new PutCourse(Course, CourseId).run(req, res);
    return;
  }

  @RequestMapping(value = "/{CourseId}", method = RequestMethod.GET)
  public static Course getCourse(@PathVariable(value = "CourseId") int CourseId, HttpServletRequest req,
      HttpServletResponse res) {
    Course r = new GetCourse(CourseId).run(req, res);
    return r;
  }

  @RequestMapping(value = "/{crsId}", method = RequestMethod.DELETE)
  public static void deleteCourse(@PathVariable(value="crsId") int crsId, HttpServletRequest req, HttpServletResponse res) {

    new CourseTransactions.DeleteCourse(crsId).run(req, res);

  }

  @RequestMapping(value = "/{crsId}/component", method = RequestMethod.POST)
  public static Integer postComponent(@RequestBody Component cmp, @PathVariable(value="crsId") int crsId, HttpServletRequest req,
      HttpServletResponse res) {
    Integer cmpId = new ComponentTransactions.PostComponent(cmp, crsId).run(req, res);
    return cmpId;
  }


  @RequestMapping(value = "/{crsId}/component", method = RequestMethod.GET)
  public static List<Component> getComponents(@PathVariable(value = "crsId") int crsId, HttpServletRequest req, HttpServletResponse res) {
  List<Component> cmps = new CourseTransactions.getComponents(crsId).run(req, res);

  return cmps;

  }
}
