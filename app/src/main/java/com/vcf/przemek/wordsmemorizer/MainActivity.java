package com.vcf.przemek.wordsmemorizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.SwitchPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.vcf.przemek.wordsmemorizer.custom_oauth.CustomOAuthToken;
import com.vcf.przemek.wordsmemorizer.custom_oauth.CustomVolleyAuthRequest;
import com.vcf.przemek.wordsmemorizer.custom_oauth.CustomVolleyRequestAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    String client_id = "duvSyUXo6Byjv3IS60v163rpmxhAoO0XUOXdkLc6";
    String client_secret = "hgTUHimIiC0CbEgOtmnPGNzjmM0f0CF3Kfz47GdY7eO7jrkn5NF6E5BDgDqgVH5vM2c3wc7WMWhIWMmuGGCBELRSduDKHUT874IRAnC6RRvk9zDgS8NmKRWVvCg18ppk";

    String client_id_2 = "77sxCComSu0vriJx1tu2LsyNKU3Z1s9o0FJIybcE";
    String client_secret_2 = "iKpABZIlIlKFXMuHZjtLacPwyEzObAo4UkQBTlmzUWlr5zs9ZajrabkJVviNRUQYCHT5oM52O0VQyiDTo0frC3443m7hriD3gyEre89L2Q5cuP4maHlYSOYpKHxkRhEp";


    public String getAuthCredentials(){
        SharedPreferences settings = getSharedPreferences(getDefaultSharedPreferencesName(this), 0);

        boolean use_credentials_2 = settings.getBoolean("use_credentials_2", false);

        if (use_credentials_2){
            return client_id_2 + ":" + client_secret_2;
        }
        return client_id + ":" + client_secret;
    }

    private void saveAuthonticationToken(JSONObject response){
        CustomOAuthToken token = CustomOAuthToken.getInstance();
        try{
            token.setAccess_token(response.getString("access_token"));
            token.setToken_type(response.getString("token_type"));
            token.setExpires_in(response.getString("expires_in"));
            token.setScope(response.getString("scope"));
            token.setRefresh_token(response.getString("refresh_token"));
        }
        catch (JSONException e){

        }
    }

    public void authUser(View v){
        String url = getPrefixURL() + "o/token/";

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", "dom");
        params.put("password", "kazik1916");

        CustomVolleyAuthRequest customRequest = new CustomVolleyAuthRequest(
                Request.Method.POST, url, params, getAuthCredentials(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //showResponse("Response: ", response.toString());
                        saveAuthonticationToken(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError response)
                    {
                        showResponse("Response: Error", response.toString());
                    }
                }
        );
        RequestQueueSingleton.getInstance(this).addToRequestQueue(customRequest);
    }

    public void testApi(View v) {
        // Get a RequestQueue
//        RequestQueue queue = RequestQueueSingleton.getInstance(this.getApplicationContext()).
//                getRequestQueue();

        String url = getPrefixURL() + "restapi/expressions/";

        Map<String, String> params = new HashMap<>();
        String oauth_token = CustomOAuthToken.getInstance().getAccess_token();

        CustomVolleyRequestAPI customRequest = new CustomVolleyRequestAPI(
                Request.Method.GET, url, params, oauth_token,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showResponse("Response: ", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError response)
                    {
                        showResponse("Response: Error", response.toString());
                    }
                }
        );

        RequestQueueSingleton.getInstance(this).addToRequestQueue(customRequest);
    }

    public void addExpressionDialog(View v){
        Intent intent = new Intent(this, AddExpressionActivity.class);
        startActivity(intent);
    }
}
