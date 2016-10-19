package com.example.pierrickvinot.projetandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pierrick.vinot on 19/10/16.
 */

public class ItemAdapter extends BaseAdapter {
    List<Message> messages;

    Context context;
    LayoutInflater layoutInflater;

    public ItemAdapter(List<Message> messages, Context context) {
        super();
        this.messages = messages;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override public int getCount() {
        return messages.size();
    }

    @Override public Object getItem(int position) {
        return messages.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.element_message, null);
        TextView message = (TextView) convertView.findViewById(R.id.message);
        message.setText(messages.get(position).getMessage());
        TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setText(messages.get(position).getLogin());
        return convertView;
    }
}