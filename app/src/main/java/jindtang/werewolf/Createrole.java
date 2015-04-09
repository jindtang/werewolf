package jindtang.werewolf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Random;
import android.app.AlertDialog.Builder;


public class Createrole extends Activity {

    private static final String TAG = "CreateRole";

    public final static String USER_NAME = "username";
    public final static String PLAYERID = "playerid";
    private Button oknext;
//    private int activity_level;
//    private int location;

    private SharedPreferences mPrefs;
    private Player players[];
    private int num_p;
    private int num;
    private int num_w;
    private int num_s;
    private int num_v;
    private int w;
    private int v;
    private int s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createrole);
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);

        read();
        num = 0;
        players = new Player[num_p];
        oknext = (Button) findViewById(R.id.oknext);
        oknext.setEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_createrole, menu);
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

    public void backToDisplay(View view) {
        //save();
        read();
        Intent intent = getIntent();
        EditText editText = (EditText) findViewById(R.id.name);
        String name = editText.getText().toString();
        if(name.equals("")) {
            name = "player";
            name += num;
        }
        //players[num].setName(name);

        SharedPreferences.Editor ed = mPrefs.edit();
        String s = "player";
        s += num;
        s += "name";
        ed.putString(s, name);
        ed.apply();

        intent.putExtra(USER_NAME, name);

        Log.d(TAG, "Going back the display: " + name);

        // Setting resultCode to 100 to identify on old activity
        setResult(DisplayActivity.GET_NAME, intent);
        finish();
    }

    public void randomRole(View v){
        Button b = (Button) v;
        Player.roles r = setRoles();
        num++;
        SharedPreferences.Editor ed = mPrefs.edit();
        int x = roleint(r);
        String s = "player";
        s += num;
        s += "role";
        ed.putInt(s,x);
        ed.putInt("num",num);
        ed.apply();

        String str = rolestr(r);
        Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your role is "+ str);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
        v.setEnabled(false);
        oknext.setEnabled(true);
    }

    public Player.roles setRoles(){
        read();
        int i = w + v + s;
        Random r = new Random();
        int x = r.nextInt(i)+1;
        SharedPreferences.Editor ed = mPrefs.edit();

        if(x <= v) {
            v--;
            ed.putInt("v", v);
            ed.apply();
            return Player.roles.Villager;
        }
        if(x > v&& x <= v+w) {
            w--;
            ed.putInt("w", w);
            ed.apply();
            return Player.roles.Werewolf;
        }
        else {
            s--;
            ed.putInt("s", s);
            ed.apply();
            return Player.roles.Seer;
        }
    }

    public int roleint( Player.roles r){
        switch(r){
            case Werewolf:
                return 2;
            case Villager:
                return 1;
            case Seer:
                return 3;
        }
        return 0;
    }

    public String rolestr(Player.roles r){
        switch(r){
            case Werewolf:
                return "Werewolf";
            case Villager:
                return "Villager";
            case Seer:
                return "Seer";
        }
        return "";
    }

    public void read(){
        num_w = mPrefs.getInt("num_w", 0);
        num_v = mPrefs.getInt("num_v", 0);
        num_s = mPrefs.getInt("num_s", 0);
        num_p = mPrefs.getInt("num_p", 0);
        num = mPrefs.getInt("num", 0);
        w = mPrefs.getInt("w", 2);
        s = mPrefs.getInt("s", 1);
        v = mPrefs.getInt("v", 0);
    }

}
