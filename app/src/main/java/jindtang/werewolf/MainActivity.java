package jindtang.werewolf;

import android.app.Activity;
import android.content.Context;
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


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    public static final String ID = "IDTag";

    public static final int NUM_BUTTONS = 5;
    public static final int DEFAULT_PLAYERS = 6;

    final Context context = this;

    private SharedPreferences mPrefs;
    private int num_p;
    private int num_w = 2;
    private int num_s = 1;
    private int num_v;

    //Buttons making up the number of Player
    private Button playerButtons[];
    private static final int ids[] = {R.id.activity6, R.id.activity7, R.id.activity8,
        R.id.activity9, R.id.activity10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);


        playerButtons = new Button[NUM_BUTTONS];
        for (int i = 0; i < playerButtons.length; i++) {
            playerButtons[i] = (Button) findViewById(ids[i]);
            playerButtons[i].setEnabled(true);
            playerButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /** Listen to when the user clicks the number of Player button */
    private class ButtonClickListener implements View.OnClickListener { int location;
        public ButtonClickListener(int location) { this.location = location;
        }
        public void onClick(View view) {
            Log.d(TAG, "Button clicked at activity: " + location);
            Intent intent = new Intent(context, DisplayActivity.class);
            intent.putExtra(ID, ids[location]);
            num_p = location + DEFAULT_PLAYERS;
            save();
            startActivity(intent);
        }
    }

    public void info(View view){
        Intent intent = new Intent(context, Information.class);
        startActivity(intent);
    }

//    public void setNum(int i){
//        Button b = (Button) view;
//        String s = b.getText().toString();
//        num_p = Integer.parseInt(s);
//    }

    public void save(){
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("num_p", num_p);
        ed.putInt("w", 2);
        ed.putInt("num_w", 2);
        ed.putInt("s", 1);
        ed.putInt("num_s", 1);
        num_v = num_p - 3;
        ed.putInt("v", num_v);
        ed.putInt("num_v", num_v);
        ed.putInt("num", 0);
        ed.apply();
    }

}
