package ic.aiczone.cifbmatchapps.activity.detail

import ic.aiczone.cifbmatchapps.entities.MatchDetail


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

interface DetailView {
    fun showLoading()
    fun hideloading()
    fun showDetail(eventDetail: MatchDetail, hometeam: MatchDetail, awayTeam: MatchDetail)

}