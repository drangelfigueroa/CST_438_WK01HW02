package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.AppDAO;
import com.example.myapplication.db.AppDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingActivity extends AppCompatActivity {
    AppDAO appDAO;
    private static final String USER_ID_KEY = "com.example.myapplication.userIdKey";
    private TextView textViewPosts;
    private TextView textViewWelcome;
    private User user;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        getDatabase();
        wireUpDisplay();
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
    public void getDatabase(){
        appDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();
    }
    public void wireUpDisplay(){

        textViewPosts = findViewById(R.id.textViewPosts);
        textViewPosts.setMovementMethod(new ScrollingMovementMethod());
        textViewWelcome = findViewById(R.id.textViewWelcome);
        Intent intent = getIntent();
        userId = intent.getIntExtra(USER_ID_KEY, -1);
        user = appDAO.getUserByUserId(userId);
        textViewWelcome.setText("Welcome "+user.getUsername()+", userid="+userId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewPosts.setText("Code: "+response.code());
                    return;
                }

                List<Post> posts = response.body();

                for(Post post: posts){
                    if(post.getUserId()== userId){
                        textViewPosts.append(post.getTitle() + "\n" + post.getBody() + "\n\n");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewPosts.setText(t.getMessage());
            }
        });
    }
}