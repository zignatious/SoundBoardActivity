package com.example.myapplication;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundBoardActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = SoundBoardActivity.class.getSimpleName();
    private Button aNoteButton;
    private Button bFlatNoteButton;
    private Button bNoteButton;
    private Button scaleButton;
    private SoundPool soundPool;
    private int aNoteSound;
    private int bNoteSound;
    private int bbNoteSound;
    boolean loaded = false;
    private Map<Integer, Integer> noteMap;
    private List<Note> scaleTrack = new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_board);
        Log.d(TAG, "onCreate: ");
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        createScale();
        wireWidgets();
        loadSounds();
        setListeners();
    }

    private void createScale() {
            Note note1 = new Note(soundPool.load(this, R.raw.scalea, 1), 600);
        Note note2 = new Note(soundPool.load(this, R.raw.scalebb, 1), 600);
        Note note3 = new Note(soundPool.load(this, R.raw.scaleb, 1), 400);
        Note note4 = new Note(soundPool.load(this, R.raw.scalea, 1), 400);
        Note note5 = new Note(soundPool.load(this, R.raw.scalebb, 1), 400);
        Note note6 = new Note(soundPool.load(this, R.raw.scaleb, 1), 200);
        Note note7 = new Note(soundPool.load(this, R.raw.scalea, 1), 200);
        Note note8 = new Note(soundPool.load(this, R.raw.scalebb, 1), 200);
        Note note9 = new Note(soundPool.load(this, R.raw.scaleb, 1), 0);
        scaleTrack.add(note1);
        scaleTrack.add(note2);
        scaleTrack.add(note3);
        scaleTrack.add(note4);
        scaleTrack.add(note5);
        scaleTrack.add(note6);
        scaleTrack.add(note7);
        scaleTrack.add(note8);
        scaleTrack.add(note9);
    }

    private void loadSounds() {
            aNoteSound = soundPool.load(this, R.raw.scalea, 1);
        bNoteSound = soundPool.load(this, R.raw.scaleb, 1);
        bbNoteSound = soundPool.load(this, R.raw.scalebb, 1);

        noteMap = new HashMap<>();
        noteMap.put(aNoteButton.getId(), aNoteSound);
        noteMap.put(bNoteButton.getId(), bNoteSound);
        noteMap.put(bFlatNoteButton.getId(), bbNoteSound);
    }

    private void wireWidgets() {
            aNoteButton = findViewById(R.id.button_note_a);
        bFlatNoteButton = findViewById(R.id.button_not_bFlat);
        bNoteButton = findViewById(R.id.button_not_b);
        scaleButton = findViewById(R.id.button_main_scale);
    }

    private void setListeners() {
    KeyboardListener keyboardListener = new KeyboardListener();
        aNoteButton.setOnClickListener(keyboardListener);
        bNoteButton.setOnClickListener(keyboardListener);
        bFlatNoteButton.setOnClickListener(keyboardListener);

        scaleButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private void delay(int millisDelay) {
        try {
            Thread.sleep(millisDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_main_scale: {
                for (int i = 0; i < scaleTrack.size(); i++) {
                    soundPool.play(scaleTrack.get(i).getSoundId(), 1, 1, 1, 0, 1);
                    delay(scaleTrack.get(i).getMillisDelay());
                }
            }
        }
    }


    private class KeyboardListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int songId = noteMap.get(view.getId());
            if (songId != 0) {
                soundPool.play(songId, 1, 1, 1, 0, 1);
            }
        }
    }

    private class Note {

        private int soundId;
        private int millisDelay;

        public Note(int soundId, int millisDelay) {
            this.soundId = soundId;
            this.millisDelay = millisDelay;
        }

        public int getSoundId() {
            return soundId;
        }

        public void setSoundId(int soundId) {
            this.soundId = soundId;
        }

        public int getMillisDelay() {
            return millisDelay;
        }

        public void setMillisDelay(int millisDelay) {
            this.millisDelay = millisDelay;
        }
    }

    private class Song {
        private List<Note> song;

        public Song() {
          song = new ArrayList<Note>();
       }

        public void addNote(Note note) {
            song.add(note);
        }


       public void playSong() {
            for (int i = 0; i < song.size(); i++) {
                soundPool.play(song.get(i).getSoundId(), 1, 1, 1, 0, 1);
                delay(song.get(i).getMillisDelay());
           }
        }
    }
}