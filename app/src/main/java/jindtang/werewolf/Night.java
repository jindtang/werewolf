package jindtang.werewolf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Night extends Activity {

    private int num_w;
    private int num_v;
    private int num_s;
    private int num_p;
    private SharedPreferences mPrefs;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);

        read();
        checkWinner();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_night, menu);
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

    @Override
    public void onBackPressed() {
        DisplayActivity.setTime(false);
        finish();
    }

    public void werewolfKills(View view) {
        DisplayActivity.setTime(true);
        DisplayActivity.setKilling(true);
        finish();
    }

    public void skipNight(View view) {
        DisplayActivity.setKilling(false);
        Intent intent = new Intent(this, Daytime.class);
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
