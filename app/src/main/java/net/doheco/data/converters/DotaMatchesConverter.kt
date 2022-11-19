package net.doheco.data.converters

import com.google.gson.Gson
import net.doheco.domain.model.DotaMatch
import net.doheco.domain.model.opendota.OpenDotaMatch

fun <T> Any.convert(classOfT: Class<T>): T = Gson().fromJson(Gson().toJson(this), classOfT)

class DotaMatchesConverter{

   companion object {
       fun doForward(a: OpenDotaMatch): DotaMatch {
           return a.convert(DotaMatch::class.java)
       }

       fun doBackward(b: DotaMatch): OpenDotaMatch {
           return b.convert(OpenDotaMatch::class.java)
       }
   }
}