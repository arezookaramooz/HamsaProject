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
        startIndexOfPosts = indexOfNumberOfPosts + 1;
        endIndexOfPosts = startIndexOfPosts + numberOfPosts-1;

//        Log.d("ProfileActivity", "lines[o] is: " + lines[0]);
//        Log.d("ProfileActivity", "number of online users is: " + numberOfOnlineUsers);
//        Log.d("PhotosActivity", "index of username is" + indexOfUserName);
        userName = lines[indexOfUserName];

//        Log.d("PhotosActivity", "user name is: " + userName);
        String[] followers = findFollowers(lines, userName);
//        Log.d("PhotosActivity", "number of followers is: " + followers.length );
//        Log.d("PhotosActivity", "follower is: " + followers[0] );
        String[] sortedPosts = sortPostsByTimestamp(getPosts(lines));
//        Log.d("PhotosActivity", "sorted post0 is: " + sortedPosts[0] );
//        Log.d("PhotosActivity", "sorted post1 is: " + sortedPosts[1] );
//        Log.d("PhotosActivity", "sorted post2 is: " + sortedPosts[2] );
//        Log.d("PhotosActivity", "sorted post3 is: " + sortedPosts[3] );
//        Log.d("PhotosActivity", "sorted post4 is: " + sortedPosts[4] );

        LinearLayout linearLayout = findViewById(R.id.ll);

        for (int i = 0; i < sortedPosts.length; i++) {
            String[] splitedPost = sortedPosts[i].split("\\r?\\s");
            if (splitedPost[2].equals("true")){
//                Log.d("PhotosActivity", "yes: " + splitedPost[0]);
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
//        Log.d("PhotosActivity", "number of posts is " + numberOfPosts);
        String[] posts = new String[numberOfPosts];
        int j = 0;
//        Log.d("PhotosActivity", " start index is " + startIndexOfPosts);
//        Log.d("PhotosActivity", " end index is " + endIndexOfPosts);
        for (int i = startIndexOfPosts; i <= endIndexOfPosts; i++) {
            posts[j] = lines[i];
            j++;
        }
        return posts;
    }

    private String[] sortPostsByTimestamp(String[] posts) {
        String temp;
//        Log.d("PhotosActivity", "posts0 are: " + posts[0]);
//        Log.d("PhotosActivity", "posts1 are: " + posts[1]);
//        Log.d("PhotosActivity", "posts2 are: " + posts[2]);
//        Log.d("PhotosActivity", "posts3 are: " + posts[3]);
//        Log.d("PhotosActivity", "posts4 are: " + posts[4]);
//        Log.d("PhotosActivity", "number of posts is: " + posts.length);
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
