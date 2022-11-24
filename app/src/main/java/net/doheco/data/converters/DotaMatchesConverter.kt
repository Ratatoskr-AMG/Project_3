package net.doheco.data.converters

import android.util.Log
import com.google.gson.Gson
import net.doheco.domain.model.DotaMatch
import net.doheco.domain.model.Hero
import net.doheco.domain.model.opendota.OpenDotaMatch
import java.text.SimpleDateFormat
import java.util.*

fun <T> Any.convert(classOfT: Class<T>): T = Gson().fromJson(Gson().toJson(this), classOfT)

class DotaMatchesConverter{

   companion object {
       fun doForward(a: OpenDotaMatch,hero: Hero): DotaMatch {
           val openDotaMatch = a.convert(OpenDotaMatch::class.java)

           val startTimeFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
           startTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
           var startTimeFormatted = startTimeFormat.format(openDotaMatch.startTime!! * 1000).toString()

           var durationFormatted = timeToString(openDotaMatch.duration!!)

           var dotaMatch=DotaMatch(
               openDotaMatch.assists,
               openDotaMatch.averageRank,
               openDotaMatch.deaths,
               openDotaMatch.duration,
               openDotaMatch.gameMode,
               openDotaMatch.heroId,
               openDotaMatch.kills,
               openDotaMatch.leaverStatus,
               openDotaMatch.lobbyType,
               openDotaMatch.matchId,
               openDotaMatch.partySize,
               openDotaMatch.playerSlot,
               openDotaMatch.radiantWin,
               openDotaMatch.skill,
               openDotaMatch.startTime,
               openDotaMatch.version,
               hero.icon,
               startTimeFormatted,
               durationFormatted,
           )
           Log.e("TOHAMATCH",dotaMatch.toString())
           return dotaMatch
           //return a.convert(OpenDotaMatch::class.java)
       }

       fun doBackward(b: DotaMatch): OpenDotaMatch {
           return b.convert(OpenDotaMatch::class.java)
       }

       private fun timeToString(secs: Long): String {
           val hour = secs / 3600
           val min = secs / 60 % 60
           val sec = secs / 1 % 60
           return String.format("%02d:%02d:%02d", hour, min, sec)
       }
   }
}