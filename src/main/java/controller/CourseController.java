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

import model.Course;
import transactions.CourseTransactions.GetAllCourses;
import transactions.CourseTransactions.GetCourse;
import transactions.CourseTransactions.PostCourse;
import transactions.CourseTransactions.PutCourse;

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

}
