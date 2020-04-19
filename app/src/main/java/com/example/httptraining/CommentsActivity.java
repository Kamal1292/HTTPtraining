package com.example.httptraining;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httptraining.Adapters.AdapterComment;
import com.example.httptraining.Pojo.Comment;
import com.example.httptraining.Pojo.Post;

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

public class CommentsActivity extends AppCompatActivity {

    public static final String POST_ID = "id";
    public static final String POST_TITLE = "title";

    private RecyclerView recyclerViewComm;
    private String getPostId;
    private String getPostTitle;
    private AdapterComment adapterComment;
    private OkHttpClient client;
    private Request request;
    private String jsonStringComments;

    private TextView txtTitlePost;
    private TextView txtNameComment;
    private TextView txtEmailCom;
    private TextView txtBodyCom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_post);

        recyclerViewComm = findViewById(R.id.recycler_view_comments);

        txtTitlePost = findViewById(R.id.txt_title_post2);
        txtNameComment = findViewById(R.id.txt_name_comment);
        txtEmailCom = findViewById(R.id.txt_commentator_email);
        txtBodyCom =findViewById(R.id.txt_comment_body);

        initRecyclerView();
        getExtraFromIntent();
        initComment();
        get();
    }

    private void initRecyclerView() {
        recyclerViewComm = findViewById(R.id.recycler_view_comments);
        recyclerViewComm.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        AdapterComment.OnCommentClickListener onCommentClickListener = new AdapterComment.OnCommentClickListener() {
            @Override
            public void onCommentClick(Comment comment) {

            }
        };
        adapterComment = new AdapterComment(onCommentClickListener);
        recyclerViewComm.setAdapter(adapterComment);
    }

    private void getExtraFromIntent() {
        getPostId = getIntent().getStringExtra(POST_ID);
        getPostTitle = getIntent().getStringExtra(POST_TITLE);
        txtTitlePost.setText(getPostTitle);
    }

    private void initComment() {
        List<Post> posts = findByPost(getPostId);
    }

    private List<Post> findByPost(String getPostId) {
        List<Post> posts = Constans.LIST_RESPONSE_POSTS;
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            String postId = post.getPostID();
            if (postId.equalsIgnoreCase(getPostId)){
                String titlePost = post.getTitle();
                txtTitlePost.setText(titlePost);
            }
        }
        return posts;
    }

    private void get() {
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(Constans.URL.GET_POSTS_COMMENTS)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Log.i(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                jsonStringComments = response.body().string();
                runOnUiThread(new Runnable() {
                    public void run() {
                        playForTime();
                    }
                });
            }
        });
    }

    private void playForTime() {
        if (jsonStringComments != null && !jsonStringComments.isEmpty()) {
            Constans.LIST_RESPONSE_COMMENTS = parseJson(jsonStringComments);
            searchUsers();
        }
    }

    private List<Comment> parseJson(String response) {
        List<Comment> commentList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(response);
            for (int i = 0; i < array.length(); i++) {
                JSONObject joComment = array.getJSONObject(i);
                String postId = joComment.getString(Constans.KEY_POST_ID);
                String commentId = joComment.getString(Constans.KEY_COMMENT_ID);
                String commentName = joComment.getString(Constans.KEY_COMMENT_NAME);
                String commentatorEmail = joComment.getString(Constans.KEY_COMMENTATOR_EMAIL);
                String commentBody = joComment.getString(Constans.KEY_COMMENT_BODY);

                if (postId.equalsIgnoreCase(getPostId)){
                    Comment comment = new Comment(postId, commentId, commentName, commentatorEmail, commentBody);
                    commentList.add(comment);
                }
            }

        }catch (JSONException je) {
            je.getStackTrace();
        }
        return commentList;
    }

    private void searchUsers() {
        List<Comment> comments = Constans.LIST_RESPONSE_COMMENTS;
        if (comments != null && !comments.isEmpty()) {
            adapterComment.setItems(comments);
        }
    }
}
