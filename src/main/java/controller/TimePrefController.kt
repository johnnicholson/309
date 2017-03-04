package controller

import model.TimePref
import org.springframework.web.bind.annotation.*
import transactions.TimePrefTransactions
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value="/api/pref/time")
class TimePrefController {

    @RequestMapping(value="", method = arrayOf(RequestMethod.GET))
    fun getAllTimePref(req: HttpServletRequest, res: HttpServletResponse) = TimePrefTransactions.GetAllPref().run(req, res);


    @RequestMapping(value="/{prefId}", method = arrayOf(RequestMethod.GET))
    fun getTimePref(@PathVariable(value = "prefId") prefId : Int, req: HttpServletRequest, res: HttpServletResponse)
            = TimePrefTransactions.GetPref(prefId).run(req, res);

    @RequestMapping(value="", method = arrayOf(RequestMethod.POST))
    fun postTimePref(@RequestBody pref : TimePref, req: HttpServletRequest, res: HttpServletResponse)
            = TimePrefTransactions.PostPref(pref).run(req, res);

    @RequestMapping(value="/{prefId}", method = arrayOf(RequestMethod.DELETE))
    fun deleteTimePref(@PathVariable(value = "prefId") prefId : Int, req: HttpServletRequest, res: HttpServletResponse)
            = TimePrefTransactions.DeletePref(prefId).run(req, res);

    @RequestMapping(value="/{prefId}", method = arrayOf(RequestMethod.PUT))
    fun putTimePref(@PathVariable(value = "prefId") prefId : Int,@RequestBody pref : TimePref, req: HttpServletRequest, res: HttpServletResponse)
            = TimePrefTransactions.PutPref(prefId, pref).run(req, res);

}