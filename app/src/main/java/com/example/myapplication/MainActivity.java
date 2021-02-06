package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.db.AppDAO;
import com.example.myapplication.db.AppDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button buttonLogin;
    private AppDAO appDAO;
    private User user;
    EditText usernameEditText;
    EditText passwordEditText;
    String usernameField;
    String passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDatabase();
        checkForUsers();
        wireUpDisplay();
    }


    public void getDatabase(){
        appDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();
    }
    public void checkForUsers(){
        List<User> users = appDAO.getAllUsers();

        if(users.size() == 0){
            String pass = "password123";
            appDAO.insert(new User("testuser1","testuser1"), new User("coolGuy", "coolPass"), new User("liberator", "password123"), new User("anonymous", "securePass"),
                    new User("boomer", pass), new User("knowItAll", pass), new User("userName",pass), new User("hello_world", pass), new User("gratefulDead", pass), new User("lastOne", pass) );
        }
    }
    public void wireUpDisplay(){
        buttonLogin = findViewById(R.id.buttonLogin);
        usernameEditText = findViewById(R.id.editTextTextUsername);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameField = usernameEditText.getText().toString();
                passwordField = passwordEditText.getText().toString();

                if(verify(usernameField, passwordField)){
                    Toast.makeText(getApplicationContext(), "id" + user.getUserId(), Toast.LENGTH_SHORT).show();
                    Intent intent = LandingActivity.intentFactory(getApplicationContext(), user.getUserId());
                    startActivity(intent);
                }
            }
        });
    }
    public boolean verify(String username, String password){
        user = appDAO.getUserByUsername(username);
        if(user == null) {
            usernameEditText.requestFocus();
            Toast.makeText(getApplicationContext(), "Username not recognized", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!user.getPassword().equals(password)) {
            passwordEditText.requestFocus();
            Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}