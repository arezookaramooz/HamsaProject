package com.example.arezookaramooz.hamsa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private int indexOfUserName;
    private int indexOfNumberOfPosts;
    private int numberOfOnlineUsers;
    private int numberOfPosts;
    private String userName;
    private int startIndexOfPosts;
    private int endIndexOfPosts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent myIntent = getIntent();
        String input = myIntent.getStringExtra("inputString");
        Log.d("ProfileActivity", "input is: "  + input);

        String[] lines = input.split("\\r?\\n");

        numberOfOnlineUsers = Integer.parseInt(lines[0]);
        indexOfNumberOfPosts = numberOfOnlineUsers * 3 + 1;
        numberOfPosts = Integer.parseInt(lines[indexOfNumberOfPosts]);
        indexOfUserName = numberOfOnlineUsers * 3 + 1 + numberOfPosts + 1;
        userName = lines[indexOfUserName];
        startIndexOfPosts = indexOfNumberOfPosts + 1;
        endIndexOfPosts = startIndexOfPosts + numberOfPosts-1;

        String[] followers = findFollowers(lines, userName);
        String[] sortedPosts = sortPostsByTimestamp(getPosts(lines));

        LinearLayout linearLayout = findViewById(R.id.ll);

        for (int i = 0; i < sortedPosts.length; i++) {
            String[] splitedPost = sortedPosts[i].split("\\r?\\s");
            if (splitedPost[2].equals("true")){
                TextView t = new TextView(ProfileActivity.this);
                t.setText(sortedPosts[i].replace("true", ""));
                linearLayout.addView(t);
            } else {
                Log.d("PhotosActivity", "no " + splitedPost[0] );
                for (int j = 0 ; j < followers.length ; j++){
                    if (followers[j].equals(splitedPost[0]) || splitedPost[0].equals(userName)){
                        TextView t = new TextView(ProfileActivity.this);
                        t.setText(sortedPosts[i].replace("false", ""));
                        linearLayout.addView(t);
                    }
                }
            }

        }
    }

    private String[] findFollowers(String[] lines, String userName) {
        String[] followers = new String[0];
        for (int i = 1; i < indexOfNumberOfPosts; i = i + 3) {
            if (lines[i].equals(userName)) {
                followers = lines[i + 2].split("\\s");
            }
        }
        return followers;
    }

    private String[] getPosts(String[] lines) {
        String[] posts = new String[numberOfPosts];
        int j = 0;
        for (int i = startIndexOfPosts; i <= endIndexOfPosts; i++) {
            posts[j] = lines[i];
            j++;
        }
        return posts;
    }

    private String[] sortPostsByTimestamp(String[] posts) {
        String temp;
        for (int i = 1; i < posts.length; i++) {
            for (int j = i; j > 0; j--) {
                String[] splitedPostj = posts[j].split("\\s");
                String[] splitedPostjm = posts[j - 1].split("\\s");
                if (Integer.parseInt(splitedPostj[1]) < Integer.parseInt(splitedPostjm[1])) {
                    temp = posts[j];
                    posts[j] = posts[j - 1];
                    posts[j - 1] = temp;
                }
            }
        }
        int x = 0;
        String[] result = new String[posts.length];
        for (int i = posts.length - 1; i >= 0; i--) {
            result[x] = posts[i];
            x++;
        }
        return result;
    }
}