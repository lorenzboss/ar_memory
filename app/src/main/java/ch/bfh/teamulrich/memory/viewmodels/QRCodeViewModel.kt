package ch.bfh.teamulrich.memory.viewmodels

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import org.json.JSONArray
import org.json.JSONObject

class QRCodeViewModel(application: Application) : AndroidViewModel(application) {
    var qrCode by mutableStateOf("")
        private set
    var qrKey by mutableStateOf("")
    var rows by mutableStateOf(1)
    var refreshTrigger by mutableStateOf(0)

    fun addRow() {
        if (rows < 5) rows++
    }

    fun removeRow() {
        if (rows > 0) rows--
    }

    fun setCode(code: String) {
        this.qrCode = code
    }

    fun setKey(key: String) {
        this.qrKey = key
    }

    fun clear() {
        this.qrCode = ""
        this.qrKey = ""
    }

    fun saveQRCode() {
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("QRCodeData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(this.qrKey, this.qrCode)
        editor.apply()
    }

    fun deleteQRCode(key: String) {
        refreshTrigger++
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("QRCodeData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun getQRCode(key: String): String? {
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("QRCodeData", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun sendQRCodesToLogApp() {
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("QRCodeData", Context.MODE_PRIVATE)
        val log = JSONObject()
        val intent: Intent?
        val solution: ArrayList<ArrayList<String>> = ArrayList()
        (0 until rows).forEach { row ->
            val val1: String? = sharedPreferences.getString("$row-0", null)
            val val2: String? = sharedPreferences.getString("$row-1", null)
            val valuePair: ArrayList<String> = ArrayList()
            if (val1 != null) {
                valuePair.add(val1)
            }
            if (val2 != null) {
                valuePair.add(val2)
            }
            solution.add(valuePair)
        }

        val jsonSolution = JSONArray(solution)

        log.put("task", "Memory")
        log.put("solution", jsonSolution)

        intent = Intent("ch.apprun.intent.LOG")
        intent.putExtra("ch.apprun.logmessage", log.toString())
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            getApplication<Application>().startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e("LogBookError", " LogBook application is not installed on this device.")
        }

    }

}