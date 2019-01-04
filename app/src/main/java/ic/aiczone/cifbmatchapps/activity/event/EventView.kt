package ic.aiczone.cifbmatchapps.activity.event

import ic.aiczone.cifbmatchapps.entities.MatchDetail


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

interface EventView {
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<MatchDetail>)

}