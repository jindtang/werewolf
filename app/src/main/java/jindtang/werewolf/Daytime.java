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


public class Daytime extends Activity {

    private int num_w;
    private int num_v;
    private int num_s;
    final Context context = this;
    private SharedPreferences mPrefs;
    private Player players[];
    private int num_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daytime);
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);

        read();
        checkWinner();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daytime, menu);
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

    public void townVote(View view) {
        DisplayActivity.setTime(false);
        DisplayActivity.setKilling(true);
        finish();
    }

    public void checkWinner(){
        read();
        if(num_w == 0){
            Intent intent = new Intent(context, Winner_v.class);
            startActivity(intent);
        }
        if(num_w >= num_v + num_s){
            Intent intent = new Intent(context, Winner_w.class);
            startActivity(intent);
        }
    }

//    public void read(){
//        num_w = mPrefs.getInt("num_w", 0);
//        num_v = mPrefs.getInt("num_v", 0);
//        num_s = mPrefs.getInt("num_s", 0);
//        num_p = mPrefs.getInt("num_p", 0);
//        players = new Player[num_p];
//
//        for(int i = 1; i <= num_p; i++){
//            String s = "player";
//            s += i;
//            String s2 = s + "name";
//            players[i-1].setName( mPrefs.getString(s2, "player"));
////            String s3 = s + "dead";
////            players[i-1].setDead(mPrefs.getBoolean(s3, false));
////            String s4 = s + "id";
////            players[i-1].setid(mPrefs.getString(s4, ""));
//            String s5 = s + "role";
//            players[i-1].setRole(roleint(mPrefs.getInt(s5, 0)));
//        }
//    }

    public Player.roles roleint(int x){
        switch (x){
            case 1:
                return Player.roles.Villager;
            case 2:
                return Player.roles.Werewolf;
            case 3:
                return Player.roles.Seer;
        }
        return null;
    }

    public void read(){
        num_w = mPrefs.getInt("num_w", 0);
        num_v = mPrefs.getInt("num_v", 0);
        num_s = mPrefs.getInt("num_s", 0);
        num_p = mPrefs.getInt("num_p", 0);
    }
}
