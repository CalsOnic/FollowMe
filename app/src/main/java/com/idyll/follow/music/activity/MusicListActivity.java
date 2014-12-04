package com.idyll.follow.music.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.idyll.follow.R;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class MusicListActivity extends ListActivity {
    private static final String MEDIA_PATH = "sdcard/FollowMe/";
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<String> songs = new ArrayList<String>();
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_music_list);

        updateSongList();
    }

    protected void onListItemClick(ListView listView, View view, int position, long id) {
        currentPosition = position;
        playSong(MEDIA_PATH + songs.get(position));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void playSong(String path) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.start();

            Toast.makeText(this, "play : " + path, Toast.LENGTH_SHORT).show();
            TextView status = (TextView)findViewById(R.id.playStatus);
            status.setText("playing : " + path);

            // 한 곡의 재생이 끝나면 다음 곡을 재생
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextSong();
                }
            });
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void nextSong() {
        if (++currentPosition >= songs.size()) {
            // 마지막 곡이 끝나면 재생할 곡을 초기화합니다.
            currentPosition = 0;
            TextView status = (TextView)findViewById(R.id.playStatus);
            status.setText("ready");
        } else {
            // 다음 곡을 재생
            Toast.makeText(getApplicationContext(), "play next song.", Toast.LENGTH_SHORT).show();
            playSong(MEDIA_PATH + songs.get(currentPosition));
        }
    }

    private void updateSongList() {
        File file = new File(MEDIA_PATH);

        if (!file.exists()) {
            Log.e("tlog", "not exist files");
            return;
        }

        Toast.makeText(getApplicationContext(), "?", Toast.LENGTH_SHORT).show();

        songs.add("iu_goodday.mp3");
        ArrayAdapter<String> songList = new ArrayAdapter<String>(this, R.layout.song_item, songs);
        setListAdapter(songList);

        /*
        File[] fileList = file.listFiles(new Mp3Filter());

        if (fileList.length < 1) {return;}

        for (File bean : fileList) {
            songs.add(bean.getName());
        }

        ArrayAdapter<String> songList = new ArrayAdapter<String>(this, R.layout.song_item, songs);
        setListAdapter(songList);
        */
    }
}

class Mp3Filter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3"));
    }
}