package ic.aiczone.cifbmatchapps.activity.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import ic.aiczone.cifbmatchapps.R
import ic.aiczone.cifbmatchapps.R.color.colorAccent
import ic.aiczone.cifbmatchapps.entities.MatchDetail
import ic.aiczone.cifbmatchapps.utils.DBApi
import ic.aiczone.cifbmatchapps.utils.changeFormatDate
import ic.aiczone.cifbmatchapps.utils.strToDate
import kotlinx.android.synthetic.main.match_item.*
import org.jetbrains.anko.support.v4.onRefresh


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class DetailActivity: AppCompatActivity(), DetailView {
    private lateinit var event: MatchDetail
    private lateinit var presenter: EventDetailPresenter

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.match_item)

        event = intent.getParcelableExtra("EVENT")

        val date = strToDate(event.eventDate)
        tv_date.text = changeFormatDate(date)

        home_club.text = event.homeTeam
        home_score.text = event.homeScore

        away_club.text = event.awayTeam
        away_score.text = event.awayScore

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"

        val apiMatchDetail = DBApi(event.eventId).getMatchDetail()
        val apiHomeTeam = DBApi(event.homeTeamId).getTeamDetail()
        val apiAwayTeam = DBApi(event.awayTeamId).getTeamDetail()
        val gson = Gson()
        presenter = EventDetailPresenter(this, apiMatchDetail, apiHomeTeam, apiAwayTeam, gson)
        presenter.getEventDetail()

        swipe_match.onRefresh {
            presenter.getEventDetail()
        }
        swipe_match.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }

    override fun showLoading() {
        swipe_match.isRefreshing = true
    }

    override fun hideloading() {
        swipe_match.isRefreshing = false
    }

    override fun showDetail(eventDetail: MatchDetail, homeTeam: MatchDetail, awayTeam: MatchDetail) {
        Picasso.get().load(homeTeam.teamBadge).into(home_img)
        home_club.text = eventDetail.homeTeam
        home_score.text = eventDetail.homeScore
        home_formation.text = eventDetail.homeFormation
        home_goals.text = if(eventDetail.homeGoalDetails.isNullOrEmpty()) "-" else eventDetail.homeGoalDetails?.replace(";", ";\n")
        home_shots.text = eventDetail.homeShots ?: "-"
        home_goalkeeper.text = if(eventDetail.homeLineupGoalKeeper.isNullOrEmpty()) "-" else eventDetail.homeLineupGoalKeeper?.replace("; ", ";\n")
        home_defense.text = if(eventDetail.homeLineupDefense.isNullOrEmpty()) "-" else eventDetail.homeLineupDefense?.replace("; ", ";\n")
        home_midfield.text = if(eventDetail.homeLineupMidfield.isNullOrEmpty()) "-" else eventDetail.homeLineupMidfield?.replace("; ", ";\n")
        home_forward.text = if(eventDetail.homeLineupForward.isNullOrEmpty()) "-" else eventDetail.homeLineupForward?.replace("; ", ";\n")
        home_subtitutes.text = if(eventDetail.homeLineupSubstitutes.isNullOrEmpty()) "-" else eventDetail.homeLineupSubstitutes?.replace("; ", ";\n")

        Picasso.get().load(awayTeam.teamBadge).into(away_img)
        away_club.text = eventDetail.awayTeam
        away_score.text = eventDetail.awayScore
        away_formation.text = eventDetail.awayFormation
        away_goals.text = if(eventDetail.awayGoalsDetails.isNullOrEmpty()) "-" else eventDetail.awayGoalsDetails?.replace(";", ";\n")
        away_shots.text = eventDetail.awayShots ?: "-"
        away_goalkeeper.text = if(eventDetail.awayLineupGoalKeeper.isNullOrEmpty()) "-" else eventDetail.awayLineupGoalKeeper?.replace("; ", ";\n")
        away_defense.text = if(eventDetail.awayLineupDefense.isNullOrEmpty()) "-" else eventDetail.awayLineupDefense?.replace("; ", ";\n")
        away_midfield.text = if(eventDetail.awayLineupMidfield.isNullOrEmpty()) "-" else eventDetail.awayLineupMidfield?.replace("; ", ";\n")
        away_forward.text = if(eventDetail.awayLineupForward.isNullOrEmpty()) "-" else eventDetail.awayLineupForward?.replace("; ", ";\n")
        away_subtitutes.text = if(eventDetail.awayLineupSubstitutes.isNullOrEmpty()) "-" else eventDetail.awayLineupSubstitutes?.replace("; ", ";\n")

        hideloading()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}