package com.example.quizmeapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.res.ObbInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quizmeapp.Activity.Adapter.CategoryAdapter;
import com.example.quizmeapp.Activity.FragmentsBottoms.HomeFragment;
import com.example.quizmeapp.Activity.FragmentsBottoms.LeaderBoardsFragment;
import com.example.quizmeapp.Activity.FragmentsBottoms.ProfileFragment;
import com.example.quizmeapp.Activity.FragmentsBottoms.WalletFragment;
import com.example.quizmeapp.Activity.model.CategoryModel;
import com.example.quizmeapp.R;
import com.example.quizmeapp.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new HomeFragment());
        transaction.commit();
        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.content,new HomeFragment());
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(R.id.content,new LeaderBoardsFragment());
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(R.id.content,new WalletFragment());
                        transaction.commit();
                        break;
                    case 3:
                        transaction.replace(R.id.content,new ProfileFragment());
                        transaction.commit();
                        break;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.wallet) {
            Toast.makeText(this, "wallet is pressed", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}