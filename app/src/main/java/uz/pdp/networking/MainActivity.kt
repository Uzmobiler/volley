package uz.pdp.networking

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import uz.pdp.networking.databinding.ActivityMainBinding
import uz.pdp.networking.utils.NetworkHelper

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var networkHelper: NetworkHelper

    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        networkHelper = NetworkHelper(this)

        if (networkHelper.isNetworkConnected()) {

            requestQueue = Volley.newRequestQueue(this)
            fetchImageLoad()

            fetchObjectLoad()

        } else {
            binding.tv.text = "Disconnected"
        }
    }

    private fun fetchObjectLoad() {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, "http://ip.jsontest.com/", null, object : Response.Listener<JSONObject> {
            override fun onResponse(response: JSONObject?) {
                val str = response?.getString("ip")
                binding.tv.text = str
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {
                binding.tv.text = error?.message
            }
        })
        requestQueue.add(jsonObjectRequest)
    }

    private fun fetchImageLoad() {
        val imageRequest = ImageRequest("https://i.imgur.com/Nwk25LA.jpg", { response ->
            binding.imageView.setImageBitmap(response)
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888) { error ->
            binding.tv.text = error?.message
        }
        requestQueue.add(imageRequest)
    }
}