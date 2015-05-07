package jindtang.werewolf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Night extends ActionBarActivity {

    private int num_w;
    private int num_v;
    private int num_s;
    private int num_p;
    private boolean killing;
    private int turn;
    private Intent intent;
    private SharedPreferences mPrefs;
    final Context context = this;
    public boolean mSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        TextView text = (TextView) findViewById(R.id.nightText);
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);
        mSound = mPrefs.getBoolean("mSound", true);
        // when the turn is even, it's seer's turn
//        if (killing) {
//            text.setText("Seer choose a player to see if he/she is good or bad");
//        }
//        else {
//            text.setText("Werewolves choose someone to kill");
//        }

        setContentView(R.layout.activity_night);
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);

        read();
        checkWinner();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DisplayActivity.setTime(false);
        setResult(DisplayActivity.NIGHT, intent);
        finish();
    }

    public void werewolfKills(View view) {
        DisplayActivity.setTime(false);
        DisplayActivity.setKilling(false);
        setResult(DisplayActivity.WEREWOLF, intent);
        finish();
    }

    public void skipNight(View view) {
        Intent intent;
        DisplayActivity.setKilling(false);
        intent = new Intent(this, Seer.class);
        intent.putExtra("Activity", DisplayActivity.NIGHT);
        startActivity(intent);
        finish();
    }

    public void checkWinner(){
        if(num_w == 0){
            Intent intent = new Intent(context, Winner_v.class);
            startActivity(intent);
        }
        if(num_w >= num_v + num_s){
            Intent intent = new Intent(context, Winner_w.class);
            startActivity(intent);
        }
    }

    public void read(){
        num_w = mPrefs.getInt("num_w", 0);
        num_v = mPrefs.getInt("num_v", 0);
        num_s = mPrefs.getInt("num_s", 0);
        num_p = mPrefs.getInt("num_p", 0);
    }
}
