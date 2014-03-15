package com.example.cloudlibrary.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.cloudlibrary.R;
import com.example.cloudlibrary.adapters.CommentListViewAdapter;
import com.example.cloudlibrary.model.Comment;

import java.util.ArrayList;

/**
 * Created by Jay on 3/15/14.
 */
public class CommentListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        ArrayList<Comment> commentList = (ArrayList<Comment>) getIntent().getSerializableExtra("CommentList");

        ListView lv = (ListView) findViewById(R.id.comment_list);
        CommentListViewAdapter adapter = new CommentListViewAdapter(this, commentList);
        lv.setAdapter(adapter);
    }


}
