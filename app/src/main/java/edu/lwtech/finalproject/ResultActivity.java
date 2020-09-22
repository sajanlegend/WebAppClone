package edu.lwtech.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ProductAdapter mProductAdapter;
    private ArrayList<Product> mProductList;
    private RequestQueue mRequestQueue;
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        firebaseAuth = FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProductList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);

        parseJSON();

    }

    private void parseJSON()
    {

        Intent intent = getIntent();
        String result = intent.getStringExtra("searchItem");
        result.replace(" ","+");

        String url = "https://pixabay.com/api/?key=15625658-cd6281b36e6d72186659b1a9c&q="+result+"&image_type=photo";

       // String url ="https://open.api.ebay.com/shopping?callname=FindProducts&responseencoding=JSON&appid=sajanpok-Personal-PRD-569e8f59f-c8ed2a58&siteid=0&version=967&QueryKeywords="+result+"&AvailableItemsOnly=true&MaxEntries=10";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for(int i=0; i<jsonArray.length();i++)
                    {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String productName = hit.getString("user");
                        String imageUrl = hit.getString("largeImageURL");
                        int price = hit.getInt("user_id");


                        mProductList.add(new Product(imageUrl,productName,price));
                    }
                    mProductAdapter = new ProductAdapter(ResultActivity.this,mProductList );
                    mRecyclerView.setAdapter(mProductAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

                mRequestQueue.add(request);
    }

    private void logout()
    {

        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ResultActivity.this, MainActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.logoutMenu:
            {
                logout();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
       Product clickedItem = mProductList.get(position);

        detailIntent.putExtra("EXTRA_URL", clickedItem.getmImageUrl());
        detailIntent.putExtra("EXTRA_CREATOR", clickedItem.getmProductName());
        detailIntent.putExtra("EXTRA_LIKES", clickedItem.getUser());

        startActivity(detailIntent);
    }
}
