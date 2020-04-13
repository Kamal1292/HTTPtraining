package com.example.httptraining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.httptraining.Adapters.AdapterUser;
import com.example.httptraining.Pojo.User;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView usersRecyclerView;
    private AdapterUser adapterUser;
    private OkHttpClient client;
    private Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usersRecyclerView = findViewById(R.id.recycler_view_users);


        get();
        initRecyclerView();
        searchUsers();

    }

    private void get(){
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(Constans.URL.GET_USERS)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
                String jsonString = response.body().string();
                Log.d(TAG, "onResponse: STRING########## " + jsonString);
                Constans.LIST_RESPONSE = parseJson(jsonString);
            }
        });
    }

    private List<User> parseJson(String response) {
        List<User> userList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(response);
            Log.d(TAG, "parseJson: "  + array);
            for (int a = 0; a < array.length(); a++) {
                JSONObject joUser = array.getJSONObject(a);
                int userId = joUser.getInt(Constans.KEY_USER_ID);
                String userName = joUser.getString(Constans.KEY_USER_NAME);
                String userNickName = joUser.getString(Constans.KEY_USER_NICK);
                String emailAddress = joUser.getString(Constans.KEY_EMAIL);
                JSONObject joAddress = joUser.getJSONObject(Constans.KEY_ADDRESS);
                String street = joAddress.getString(Constans.KEY_STREET);
                String suite = joAddress.getString(Constans.KEY_SUITE);
                String city = joAddress.getString(Constans.KEY_CITY);
                String zipcode = joAddress.getString(Constans.KEY_ZIPCODE);
                String phone = joUser.getString(Constans.KEY_PHONE);
                String website = joUser.getString(Constans.KEY_WEBSITE);
                JSONObject joCompany = joUser.getJSONObject(Constans.KEY_COMPANY);
                String nameCompany = joCompany.getString(Constans.KEY_COMPANY_NAME);
                String catchPhrase = joCompany.getString(Constans.KEY_PHRASE);
                String bs = joCompany.getString(Constans.KEY_BS);

                User user = new User(userId, userName, userNickName, emailAddress,
                        street, suite, city, zipcode, phone, website, nameCompany,
                        catchPhrase, bs);
                userList.add(user);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }

    private void initRecyclerView() {
        usersRecyclerView = findViewById(R.id.recycler_view_users);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        AdapterUser.OnUserClickListener onUserClickListener = new AdapterUser.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra(UserInfoActivity.USER_ID, user.getId());
                startActivity(intent);
            }
        };
        adapterUser = new AdapterUser(onUserClickListener);
        usersRecyclerView.setAdapter(adapterUser);
    }

    private void searchUsers() {
        List<User> users = Constans.LIST_RESPONSE;
        adapterUser.setItems(users);
    }
}
