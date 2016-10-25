package com.example.pierrickvinot.projetandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.Target;
import com.example.pierrickvinot.projetandroid.R;
import com.example.pierrickvinot.projetandroid.models.Message;

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
        if(messages.get(position).getImage()!=null) {

            ImageView image = (ImageView) convertView.findViewById(R.id.image);

            String basicAuth = "Basic "+ Base64.encodeToString(("t:t").getBytes(),Base64.NO_WRAP);

            Headers HEADERS = new LazyHeaders.Builder().addHeader("Authorization", basicAuth).build();
            GlideUrl glideUrl = new GlideUrl(messages.get(position).getImage(), HEADERS);

            Glide.
                    with(context).
                    load(glideUrl).
                    centerCrop().crossFade().
                    into(image);

            boolean test=true;
            if(test)
                return convertView;
        }
        return convertView;
    }

}