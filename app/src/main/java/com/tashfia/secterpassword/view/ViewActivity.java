package com.tashfia.secterpassword.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tashfia.secterpassword.Constant;
import com.tashfia.secterpassword.HomeActivity;
import com.tashfia.secterpassword.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewActivity extends AppCompatActivity {
    ListView CustomList;
    String getCell;
    ImageView imgNoData;
    private ProgressDialog loading;
    EditText txtSearch;
    Button btnSearch;

    int MAX_SIZE=999;//array size

    public String userID[]=new String[MAX_SIZE];
    public String websiteName[]=new String[MAX_SIZE];
    public String pass[]=new String[MAX_SIZE];
    public String userEmail[]=new String[MAX_SIZE];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.tashfia.secterpassword.R.layout.activity_view);
        CustomList=(ListView)findViewById(R.id.listview);
        //  imgNoData=(ImageView)findViewById(R.id.imgNoData);


        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle("Contacts");

       // btnSearch=(Button)findViewById(R.id.btn_search);
        txtSearch=(EditText)findViewById(R.id.text);

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        //Log
        Log.d("SP_CELL",getCell);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText=txtSearch.getText().toString().trim();
                getData(getText);
            }
        });

        //call function to get data
        getData("");
    }


    private void getData(String text) {


        //for showing progress dialog
        loading = new ProgressDialog(ViewActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.CONTACT_VIEW_URL+getCell+"&text="+text ;

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toast.makeText(ViewActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ViewActivity.this);
        requestQueue.add(stringRequest);
    }



    private void showJSON(String response) {

        //Create json object for receiving jason data
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);


            if (result.length()==0)
            {
                Toast.makeText(ViewActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ViewActivity.this, HomeActivity.class);

                startActivity(intent);
                //imgNoData.setImageResource(R.drawable.nodata);
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    String id = jo.getString(Constant.KEY_ID);
                    String websitename = jo.getString(Constant.KEY_NAME);
                    String password = jo.getString(Constant.KEY_PASSWORD);
                    String email = jo.getString(Constant.KEY_EMAIL);

                    //insert data into array for put extra
                    userID[i] = id;
                    websiteName[i] = websitename;
                    pass[i] = password;
                    userEmail[i] = email;

                    //put value into Hashmap
                    HashMap<String, String> user_data = new HashMap<>();
                    user_data.put(Constant.KEY_NAME, websitename);
                    user_data.put(Constant.KEY_PASSWORD, password);
                    user_data.put(Constant.KEY_EMAIL, email);

                    list.add(user_data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewActivity.this, list, R.layout.contact_list_item,
                new String[]{Constant.KEY_NAME, Constant.KEY_PASSWORD,Constant.KEY_EMAIL},
                new int[]{R.id.txt_name, R.id.etxt_password,R.id.txt_email});
        CustomList.setAdapter(adapter);


//
//        CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//                Intent intent=new Intent(ViewActivity.this,ContactDetailsActivity.class);
//                intent.putExtra("id",userID[position]);
//                intent.putExtra("name",userName[position]);
//                intent.putExtra("cell",userCell[position]);
//                intent.putExtra("email",userEmail[position]);
//
//                //for logcat
//                Log.d("ID",userID[position]);
//                Log.d("NAME",userName[position]);
//                Log.d("CELL",userCell[position]);
//                Log.d("EMAIL",userEmail[position]);
//
//                startActivity(intent);
//
//
//            }
//        });


    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
