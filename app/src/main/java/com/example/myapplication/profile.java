package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.main);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            } else if (itemId == R.id.content) {
                startActivity(new Intent(getApplicationContext(),notesActivity.class));
                finish();
            } else if (itemId == R.id.note) {
                startActivity(new Intent(getApplicationContext(), profile.class));
                finish();
            } else if (itemId == R.id.chatbot) {
                startActivity(new Intent(getApplicationContext(), chat_bot.class));
                finish();
            } else if (itemId == R.id.user) {
                return true;
            }
            else {
                // Handle other menu items if needed
                return false;
            }
            return true; // Return true to indicate that the item selection has been handled
        });
    }
}