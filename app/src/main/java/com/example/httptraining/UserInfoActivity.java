package com.example.httptraining;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httptraining.Adapters.AdapterPost;
import com.example.httptraining.Pojo.Post;
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

public class UserInfoActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    public static final String USER_ID = "id";
    private String getUserId;
    private RecyclerView recyclerViewPosts;
    private AdapterPost adapterPost;
    private OkHttpClient client;
    private Request request;
    private String jsonStringPosts;

    private TextView txtUserName;
    private TextView txtUserNick;
    private TextView txtUserEmail;
    private TextView txtStreet;
    private TextView txtSuite;
    private TextView txtCity;
    private TextView txtZipCode;
    private TextView txtPhone;
    private TextView txtWebsite;
    private TextView txtCompanyName;
    private TextView txtCatchPhrase;
    private TextView txtBS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        txtUserName = findViewById(R.id.user_name);
        txtUserNick = findViewById(R.id.user_nick);
        txtUserEmail = findViewById(R.id.user_email);
        txtStreet = findViewById(R.id.user_street);
        txtSuite = findViewById(R.id.user_suite);
        txtCity = findViewById(R.id.user_city);
        txtZipCode = findViewById(R.id.user_zipcode);
        txtPhone = findViewById(R.id.user_phone);
        txtWebsite = findViewById(R.id.user_website);
        txtCompanyName = findViewById(R.id.company_name);
        txtCatchPhrase = findViewById(R.id.catchPhrase);
        txtBS = findViewById(R.id.user_bs);

        initRecyclerView();
        getExtraFromIntent();
        initPost();
        get();
    }

    private void initRecyclerView() {
        recyclerViewPosts = findViewById(R.id.recycler_view_posts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        AdapterPost.OnPostClickListener onPostClickListener = new AdapterPost.OnPostClickListener() {
            @Override
            public void onPostClick(Post post) {
                Intent intent = new Intent(UserInfoActivity.this, CommentsActivity.class);
                intent.putExtra(CommentsActivity.POST_ID, post.getPostID());
                intent.putExtra(CommentsActivity.POST_TITLE, post.getTitle());
                startActivity(intent);
            }
        };
        adapterPost = new AdapterPost(onPostClickListener);
        recyclerViewPosts.setAdapter(adapterPost);
    }

    private void getExtraFromIntent() {
        getUserId = getIntent().getStringExtra(USER_ID);
    }
    private void initPost() {
        List<User> users = findByUser(getUserId);
    }

    private List<User> findByUser(String getId) {
        List<User> userList = Constans.LIST_RESPONSE;
        for (int i = 0; i < userList.size(); i++){
            User user = userList.get(i);
            String id = user.getId();
            if (id.equalsIgnoreCase(getId)) {
                String name = user.getName();
                txtUserName.setText(name);
                String nickName = user.getNickname();
                txtUserNick.setText(nickName);
                String email = user.getEmail();
                txtUserEmail.setText(email);
                String street = user.getStreet();
                txtStreet.setText(street);
                String suite = user.getSuite();
                txtSuite.setText(suite);
                String city = user.getCity();
                txtCity.setText(city);
                String zipCode = user.getZipcode();
                txtZipCode.setText(zipCode);
                String phone = user.getPhone();
                txtPhone.setText(phone);
                String website = user.getWebsite();
                txtWebsite.setText(website);
                String companyName = user.getCompanyName();
                txtCompanyName.setText(companyName);
                String catchPhrase = user.getCatchPhrase();
                txtCatchPhrase.setText(catchPhrase);
                String bs = user.getBs();
                txtBS.setText(bs);
            }
        }
        return userList;
    }

    private void get() {
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(Constans.URL.GET_USER_POSTS)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Log.i(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                jsonStringPosts = response.body().string();
                //Log.d(TAG, "onResponse: STRING########## " + jsonString);
                runOnUiThread(new Runnable() {
                    public void run() {
                        playForTime();
                    }
                });
            }
        });
    }

    private void playForTime() {
        if (jsonStringPosts != null && !jsonStringPosts.isEmpty()) {
            Constans.LIST_RESPONSE_POSTS = parseJson(jsonStringPosts);
            searchUsers();
        }
    }

    private List<Post> parseJson(String response){
        List<Post> postList = new ArrayList<>();
        try {
            JSONArray arrayPosts = new JSONArray(response);
            for (int i = 0; i < arrayPosts.length(); i++) {
                JSONObject userPost = arrayPosts.getJSONObject(i);
                String userID = userPost.getString(Constans.KEY_USER_ID_POST);
                String postID = userPost.getString(Constans.KEY_ID_POST);
                String postTitle = userPost.getString(Constans.KEY_TITLE_POST);
                String postBody = userPost.getString(Constans.KEY_BODY_POST);

                if (userID.equalsIgnoreCase(getUserId)){
                    Post post = new Post(userID, postID, postTitle, postBody);
                    postList.add(post);
                }
            }
        }catch (JSONException je){
            je.getStackTrace();
        }
        return postList;
    }

    private void searchUsers() {
        List<Post> posts = Constans.LIST_RESPONSE_POSTS;
        if (posts != null && !posts.isEmpty()) {
            adapterPost.setItems(posts);
        }
    }
}
