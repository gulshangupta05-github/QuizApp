package com.example.quizmeapp.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmeapp.Activity.FireBaseModel.User;
import com.example.quizmeapp.R;
import com.example.quizmeapp.databinding.RowLeaderboarsBinding;

import java.util.ArrayList;

public class LeaderBoardsAdapter  extends  RecyclerView.Adapter<LeaderBoardsAdapter.LeaderBoardViewHolder>{

    Context context;
    ArrayList<User> users;

    public LeaderBoardsAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_leaderboars,parent,false);

        return new LeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {
                User user = users.get(position);
                holder.binding.name.setText(user.getName());
                holder.binding.coins.setText(String.valueOf(user.getCoins()));
                holder.binding.index.setText(String.format("#%d",position+1));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder {
        RowLeaderboarsBinding binding;

        public LeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowLeaderboarsBinding.bind(itemView);


        }
    }
}
