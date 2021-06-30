package com.example.quizmeapp.Activity.FragmentsBottoms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizmeapp.Activity.Adapter.LeaderBoardsAdapter;
import com.example.quizmeapp.Activity.FireBaseModel.User;
import com.example.quizmeapp.R;
import com.example.quizmeapp.databinding.FragmentLeaderBoardsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class LeaderBoardsFragment extends Fragment {

    public LeaderBoardsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentLeaderBoardsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLeaderBoardsBinding.inflate(inflater, container, false);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        ArrayList<User> users = new ArrayList<>();
        LeaderBoardsAdapter adapter = new LeaderBoardsAdapter(getContext(), users);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        database.collection("users")
                .orderBy("coins", Query.Direction.DESCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    User user = snapshot.toObject(User.class);
                    users.add(user);
                }
                adapter.notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }
}