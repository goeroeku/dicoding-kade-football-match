package ic.aiczone.cifbmatchapps.activity.detail

import com.google.gson.Gson
import ic.aiczone.cifbmatchapps.utils.ApiRepository
import ic.aiczone.cifbmatchapps.utils.ApiResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class EventDetailPresenter(private val view: DetailActivity,
                           private val apiMatchDetail: String,
                           private val apiHomeTeam: String,
                           private val apiAwayTeam: String,
                           private val gson: Gson){
    fun getEventDetail(){
        view.showLoading()
        doAsync {
            val matchDetail = gson.fromJson(ApiRepository().doRequest(apiMatchDetail), ApiResponse::class.java)
            val homeTeam = gson.fromJson(ApiRepository().doRequest(apiHomeTeam),ApiResponse::class.java)
            val awayTeam = gson.fromJson(ApiRepository().doRequest(apiAwayTeam), ApiResponse::class.java)

            uiThread {
                view.hideloading()
                view.showDetail(matchDetail.events[0], homeTeam.teams[0], awayTeam.teams[0])
            }
        }
    }


}