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
import com.idyll.follow.common.logging.PrintLog;
import com.idyll.follow.common.logging.PrintToast;
import com.idyll.follow.music.adapter.SongsAdapter;
import com.idyll.follow.music.entity.Song;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class MusicListActivity extends Activity {
    //private static final String MEDIA_PATH = "/sdcard/FollowMe/";
    private static final String MEDIA_PATH = "/storage/emulated/0/FollowMe/";
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<Song> musicList = new ArrayList<Song>();
    private ListView musicListView = null;
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
        PrintLog.error("play music");
        playSong(MEDIA_PATH + musicList.get(position).getName());
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
        if (++currentPosition >= musicList.size()) {
            // 마지막 곡이 끝나면 재생할 곡을 초기화합니다.
            currentPosition = 0;
            TextView status = (TextView)findViewById(R.id.playStatus);
            status.setText("ready");
        } else {
            // 다음 곡을 재생
            Toast.makeText(getApplicationContext(), "play next song.", Toast.LENGTH_SHORT).show();
            playSong(MEDIA_PATH + musicList.get(currentPosition).getName());
        }
    }

    private void updateSongList() {
        File file = new File(MEDIA_PATH);

        if (!file.exists()) {
            PrintLog.error("not exists files");
            return;
        }

/*
        for (int i = 0; i < 5; i++) {
            song song = new song();
            song.setname(i + " - iu_goodday.mp3");
            musiclist.add(song);
        }


        song song = new song();
        song.setname("iu_goodday.mp3");
        musiclist.add(song);
*/
        PrintLog.error("1");
        //file[] filelist = file.listfiles(new mp3filter());
        File[] fileList = file.listFiles();
        PrintLog.error("2");
        if (fileList.length < 1) {PrintLog.error("no files");return;}
        PrintLog.error("3");
        for (File bean : fileList) {
            Song song = new Song();
            song.setName(bean.getName());
            musicList.add(song);
        }

        PrintToast.toast(this, "count of songs  : " + musicList.size());

        // connect to arrayadapter
        musicListView = (ListView)findViewById(R.id.music_listview);
        musicListView.setAdapter(new SongsAdapter(this, R.layout.song_item, musicList));


    }
}

class Mp3Filter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3"));
    }
}