package com.example.cloudlibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cloudlibrary.activities.R;
import com.example.cloudlibrary.model.Comment;

import java.util.ArrayList;

/**
 * Created by Jay on 3/15/14.
 */
public class CommentListViewAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<Comment> commentList;

    public CommentListViewAdapter(Context ctx, ArrayList<Comment> commentList){
        this.ctx = ctx;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.comment_list_item, viewGroup, false);

        Comment item = commentList.get(position);

        TextView nameView = (TextView) rowView.findViewById(R.id.name);
        nameView.setText(item.getName());

        TextView dateView = (TextView) rowView.findViewById(R.id.date);
        dateView.setText(item.getDate());

        TextView commentView = (TextView) rowView.findViewById(R.id.comment);
        commentView.setText(item.getComment());

        return rowView;
    }
}
