package controller

import model.CoursePref
import org.springframework.web.bind.annotation.*
import transactions.CoursePrefTransactions
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by jnicho on 2/23/2017.
 */
@RestController
@RequestMapping(value = "/api/pref/crs")
class CoursePrefController {

    @RequestMapping(value = "", method = arrayOf(RequestMethod.GET))
    fun getAllPref(req: HttpServletRequest, res: HttpServletResponse): List<CoursePref>? {
        return CoursePrefTransactions.GetAllPref().run(req,res);
    }

    @RequestMapping(value = "/{prefId}", method = arrayOf(RequestMethod.GET))
    fun getPref(@PathVariable(value="prefId") prefId: Int, req: HttpServletRequest, res : HttpServletResponse)
            = CoursePrefTransactions.GetPref(prefId).run(req, res);

    @RequestMapping(value = "", method = arrayOf(RequestMethod.POST))
    fun postPref(@RequestBody crsPref : CoursePref, req : HttpServletRequest, res : HttpServletResponse)
        = CoursePrefTransactions.PostPref(crsPref).run(req, res);

    @RequestMapping(value = "/{prefId}", method = arrayOf(RequestMethod.PUT))
    fun putPref(@PathVariable(value="prefId") prefId: Int, @RequestBody crsPref : CoursePref, req : HttpServletRequest, res : HttpServletResponse)
        = CoursePrefTransactions.PutPref(prefId, crsPref).run(req, res);

    @RequestMapping(value = "/{prefId}", method = arrayOf(RequestMethod.DELETE))
    fun deletePref(@PathVariable(value="prefId") prefId: Int, req : HttpServletRequest, res : HttpServletResponse)
            = CoursePrefTransactions.DeletePref(prefId).run(req, res);
}

