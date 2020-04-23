package com.example.httptraining;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httptraining.Adapters.AdapterPhoto;
import com.example.httptraining.Pojo.Photo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PhotoActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    public static final String ALBUM_ID = "id";

    private String getAlbumId;
    private RecyclerView recyclerViewPhoto;
    private AdapterPhoto adapterPhoto;
    private OkHttpClient client;
    private Request request;
    private String jsonStringPhotos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        initRecyclerView();
        getExtraFromIntent();
        get();
    }

    private void initRecyclerView() {
        recyclerViewPhoto = findViewById(R.id.recycler_view_photos);
        recyclerViewPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerViewPhoto.setAdapter(adapterPhoto);
    }

    private void getExtraFromIntent() {
        getAlbumId = getIntent().getStringExtra(ALBUM_ID);
    }

    private void get() {
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(Constans.URL.GET_ALBUMS_PHOTOS)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                jsonStringPhotos = response.body().string();
                runOnUiThread(new Runnable() {
                    public void run() {
                        playForTime();
                    }
                });
            }
        });
    }

    private void playForTime() {
        if (jsonStringPhotos != null && !jsonStringPhotos.isEmpty()) {
            Constans.LIST_RESPONSE_PHOTOS = parseJson(jsonStringPhotos);
            setPhoto();
        }
    }

    private List<Photo> parseJson(String response) {
        List<Photo> photos = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(response);
            for (int i = 0; i < 5; i++) {
                JSONObject joPhoto = array.getJSONObject(i);

                String albumId = joPhoto.getString(Constans.KEY_ALBUMS_ID_PHOTO);
                String photoId = joPhoto.getString(Constans.KEY_ID_PHOTO);
                String titlePhoto = joPhoto.getString(Constans.KEY_TITLE_PHOTO);
                Log.d(TAG, "parseJson: ##################################### + " + titlePhoto);
                String url = joPhoto.getString(Constans.KEY_URL_PHOTO);
                Log.d(TAG, "parseJson: ############################# " + url);

                if (albumId.equalsIgnoreCase(getAlbumId)) {
                    Photo photo = new Photo(albumId, photoId, titlePhoto, url);
                    photos.add(photo);
                }
            }
        }catch (JSONException je) {
            je.getStackTrace();
        }
        return photos;
    }

    private void setPhoto() {
        Collection<Photo> photos = Constans.LIST_RESPONSE_PHOTOS;
        Log.d(TAG, "setPhoto: ##############################################" + photos.size());
        if (!photos.isEmpty()) {
            adapterPhoto.setItems(photos);
        }
    }

}
