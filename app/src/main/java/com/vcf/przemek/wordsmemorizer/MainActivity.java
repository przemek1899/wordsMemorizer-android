package com.vcf.przemek.wordsmemorizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.SwitchPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_view:
                showGroupListActivity();
                return true;

            case R.id.app_settings:
                showAppSettingsActivity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAppSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showGroupListActivity(){
        Intent intent = new Intent(this, GroupViewActivity.class);
        startActivity(intent);
    }

    public void addGroupDialog(View v){
        Intent intent = new Intent(this, AddGroupActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showResponse(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    public String getPrefixURL(){
        SharedPreferences settings = getSharedPreferences(getDefaultSharedPreferencesName(this), 0);

        boolean over_tsl = settings.getBoolean("tsl", false);
        String host_ip = settings.getString("host_ip", "192.168.1.4");
        String port_number = settings.getString("port_number", "8000");

        String s = null;
        if (over_tsl){
            s = "https://";
        }
        else{
            s = "http://";
        }
        String url = s + host_ip + ":" + port_number + "/";
        return url;
    }

    public void authUser(View v){
        // Get a RequestQueue
        RequestQueue queue = RequestQueueSingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        String url = getPrefixURL() + "o/token/";
        JSONObject json_params = new JSONObject();
        try {
            json_params.put("grant_type", "password");
            json_params.put("username", "dom");
            json_params.put("password", "kazik1916");
            json_params.put("duvSyUXo6Byjv3IS60v163rpmxhAoO0XUOXdkLc6",
                    "hgTUHimIiC0CbEgOtmnPGNzjmM0f0CF3Kfz47GdY7eO7jrkn5NF6E5BDgDqgVH5vM2c3wc7WMWhIWMmuGGCBELRSduDKHUT874IRAnC6RRvk9zDgS8NmKRWVvCg18ppk");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, json_params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with the response
                        showResponse("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void testApi(View v) {
        // Get a RequestQueue
        RequestQueue queue = RequestQueueSingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        String url ="http://192.168.1.4:8000/restapi/expressions/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        showResponse("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void addExpressionDialog(View v){
        Intent intent = new Intent(this, AddExpressionActivity.class);
        startActivity(intent);
    }
}
