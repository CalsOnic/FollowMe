package com.idyll.follow.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.idyll.follow.R;
import com.idyll.follow.common.logging.PrintLog;
import com.idyll.follow.music.entity.Song;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends ArrayAdapter<Song> {
    private ViewHolder viewHolder = null;
    private LayoutInflater layoutInflater = null;
    private ArrayList<Song> songs = null;
    private Context mContext = null;

    public SongsAdapter(Context context, int resource, List<Song> objects) {
        super(context, resource, objects);
        this.layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    protected void finalize() throws Throwable {
        free();
        super.finalize();
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Song getItem(int position) {
        return super.getItem(position);
    }

    /**
     * Set one row data
     * It is printed on ListView
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = getItem(position);
        View view = convertView;

        if (null == view) {
            view = layoutInflater.inflate(R.layout.song_item, null);

            viewHolder = new ViewHolder();
            viewHolder.songTitle = (TextView)view.findViewById(R.id.song_title);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.songTitle.setText(song.getName());

        return view;
    }

    private void free() {
        viewHolder = null;
        layoutInflater = null;
        songs = null;
        mContext = null;
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                default :
                    break;
            }
        }
    };

    /**
     * ViewHolder
     * speed up for "getView"
     * used "viewHolder" in order to re-use findViewByID
     */
    class ViewHolder {
        public TextView songTitle = null;
    }
}
