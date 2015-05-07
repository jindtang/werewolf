package jindtang.werewolf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;


public class Winner_v extends ActionBarActivity {
    public boolean mSound;
    private SharedPreferences mPrefs;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_v);
//        this.setTitle("");

        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);
        mSound = mPrefs.getBoolean("mSound", true);
        //createSoundPool();

        if(mSound) {
            MediaPlayer sound = MediaPlayer.create(this, R.raw.villager);
            sound.start();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem mi = (MenuItem) menu.getItem(2);

        if (mSound == false)
            mi.setIcon(R.drawable.mute);
        else
            mi.setIcon(R.drawable.sound);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.info){
            Intent intent = new Intent(context, Information.class);
            startActivity(intent);
        }
        else if (id == R.id.new_game) {
            Intent intent = new Intent(this, Startgame.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.sound){
            mSound = !mSound;
            if(mSound == false)
                ((ActionMenuItemView) this.findViewById(R.id.sound)).setIcon(getResources().getDrawable(R.drawable.mute));
            else
                ((ActionMenuItemView) this.findViewById(R.id.sound)).setIcon(getResources().getDrawable(R.drawable.sound));
            SharedPreferences.Editor ed = mPrefs.edit();
            ed.putBoolean("mSound", mSound);
            ed.apply();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
    }

    public void changeSound(){
        mSound = !mSound;
    }

}
