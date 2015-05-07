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


public class Settingup extends Activity {
    final Context context = this;
    public boolean mSound;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingup);
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);
        mSound = mPrefs.getBoolean("mSound", true);
        ActionMenuItemView mi = (ActionMenuItemView) this.findViewById(R.id.sound);
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
}
