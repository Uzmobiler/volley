package uz.pdp.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import uz.pdp.networking.adapters.UserAdapter
import uz.pdp.networking.databinding.ActivitySecondBinding
import uz.pdp.networking.models.User

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding
    lateinit var requestQueue: RequestQueue

    var url = "https://jsonplaceholder.typicode.com/users"
    private val TAG = "SecondActivity"
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)
        VolleyLog.DEBUG = true
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {
                    val type = object : TypeToken<List<User>>() {}.type
                    var list: List<User> = Gson().fromJson(response.toString(), type)

                    userAdapter = UserAdapter(list)
                    binding.rv.adapter = userAdapter
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {

                }
            })

//        jsonArrayRequest.tag = "tag1"
        requestQueue.add(jsonArrayRequest)

//        requestQueue.cancelAll("tag1")
    }
}