package com.example.httptraining;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httptraining.Adapters.AdapterAlbums;
import com.example.httptraining.Pojo.Album;

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

public class AlbumsActivity extends AppCompatActivity {

    public static final String USER_ID_FOR_ALBUMS = "id";

    private String getUserId;
    private RecyclerView recyclerViewAlbums;
    private AdapterAlbums adapterAlbums;
    private OkHttpClient client;
    private Request request;
    private String jsonStringAlbums;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        initRecyclerView();
        getExtraFromIntent();
        get();
    }

    private void initRecyclerView() {
        recyclerViewAlbums = findViewById(R.id.recycler_view_albums);
        recyclerViewAlbums.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        AdapterAlbums.OnAlbumClickListener onAlbumClickListener = new AdapterAlbums.OnAlbumClickListener() {
            @Override
            public void onAlbumClick(Album album) {
                Intent intent = new Intent(AlbumsActivity.this, PhotoActivity.class);
                intent.putExtra(PhotoActivity.ALBUM_ID, album.getAlbumsId());
                startActivity(intent);
            }
        };
        adapterAlbums = new AdapterAlbums(onAlbumClickListener);
        recyclerViewAlbums.setAdapter(adapterAlbums);
    }

    private void getExtraFromIntent() {
        getUserId = getIntent().getStringExtra(USER_ID_FOR_ALBUMS);
    }

    private void get() {
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(Constans.URL.GET_USERS_ALBUMS)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                jsonStringAlbums = response.body().string();
                runOnUiThread(new Runnable() {
                    public void run() {
                        playForTime();
                    }
                });
            }
        });
    }

    private void playForTime() {
        if (jsonStringAlbums != null && !jsonStringAlbums.isEmpty()) {
            Constans.LIST_RESPONSE_ALBUMS = parseJson(jsonStringAlbums);
            searchUsers();
        }
    }

    private List<Album> parseJson(String response) {
        List<Album> albums = new ArrayList<>();
        try {
            JSONArray arrayAlbums = new JSONArray(response);
            for (int i = 0; i < arrayAlbums.length(); i++) {
                JSONObject joAlbums = arrayAlbums.getJSONObject(i);
                String userId = joAlbums.getString(Constans.KEY_USER_ID_ALBUMS);
                String albumId =  joAlbums.getString(Constans.KEY_ALBUMS_ID);
                String albumTitle = joAlbums.getString(Constans.KEY_ALBUMS_TITLE);

                if (userId.equalsIgnoreCase(getUserId)) {
                    Album album = new Album(userId, albumId, albumTitle);
                    albums.add(album);
                }
            }
        }catch (JSONException je) {
            je.getStackTrace();
        }
       return albums;
    }
    private void searchUsers() {
        List<Album> albums = Constans.LIST_RESPONSE_ALBUMS;
        if (albums != null && !albums.isEmpty()) {
            adapterAlbums.setItems(albums);
        }
    }

}
