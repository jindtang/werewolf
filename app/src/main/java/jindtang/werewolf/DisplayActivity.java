package jindtang.werewolf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Fang Tang on 4/1/15.
 */
public class DisplayActivity extends ActionBarActivity {

    private static final String TAG = "DisplayActivity";
    public static final int GET_NAME = 100;
    public static final int NIGHT = 200;
    public static final int WEREWOLF = 210;
    public static final int SEER = 220;
    public static final int DAY = 300;
    public static final int DEAD = -1;

    private int request;

    final Context context = this;
    private boolean paused = false;

    private static WerewolfGame game;
    private static boolean day_time = false;
    private static boolean killing = true;
    private static boolean shouldResume;
    private static int character = 0;
    private int turn = 0;
    private SharedPreferences mPrefs;
    private Button next;

    private Button buttons[];
    private static int ids[];
    private String names[];

    private View v;

    private int activity_level;
    private int num_p;
    private int num_w;
    private int num_s;
    private int num_v;
    private boolean done = false;
    public boolean mSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);
        mSound = mPrefs.getBoolean("mSound", true);
        shouldResume = true;
        day_time = false;
        killing = true;
        read();

        loadLayout();

        buttons = new Button[ids.length];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (Button) findViewById(ids[i]);
            buttons[i].setEnabled(true);
            buttons[i].setOnClickListener(new ButtonClickListener(i));
        }
        names = new String[ids.length];

        next = (Button) findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        Log.d(TAG, "The activity has resumed");

        if (shouldResume && request < 200) {
            Log.d(TAG, "Changing button to disable");
            TextView text = (TextView) findViewById(R.id.killtext);
            text.setVisibility(View.INVISIBLE);
            if (turn > 0 || allPlayerSelected()) {
                Button next = (Button) findViewById(R.id.next);
                next.setVisibility(View.VISIBLE);
            }
        }
        else {
            switch (request) {
                case DAY:
                case WEREWOLF:
                    setTextAndButton(getString(R.string.werewolf_text));
                    break;
                case SEER:
                    setTextAndButton(getString(R.string.seer_text));
                    break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent;
        Log.d(TAG, "back button pressed with request " + request );
        switch (request) {
            case WEREWOLF:
                intent = new Intent(this, Night.class);
                startActivity(intent);
//                request = 0;
                break;
            case SEER:
                intent = new Intent(this, Seer.class);
                startActivity(intent);
//                request = 0;
                break;
            case DAY:
                intent = new Intent(this, Daytime.class);
                startActivity(intent);
//                request = 0;
                break;
            default:
                super.onBackPressed();
        }

    }


    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        Log.d(TAG, "The activity DisplayActivity paused");

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

    public boolean allPlayerSelected() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isEnabled())
                return false;
        }
        return true;
    }

    /** Listen to when the user clicks the number of Player button */
    private class ButtonClickListener implements View.OnClickListener { int location;
        public ButtonClickListener(int location) { this.location = location;
        }
        public void onClick(View view) {
            if (turn == 0) {
                Log.d(TAG, "Player select the button: " + location);
                Intent intent = new Intent(context, Createrole.class);
                intent.putExtra(MainActivity.ID, ids[location]);
                intent.putExtra("location", location);
                if (allPlayerSelected())
                    next.setVisibility(View.VISIBLE);
                startActivityForResult(intent, GET_NAME);

            }
            else {
                Log.d(TAG, "Killing the player at: " + location);
                Button b = (Button) view;
                String s = b.getText().toString();
//                if(character == 2){
//                    showdia();
//                }
//                else {
//                AlertDialog dialog;
                switch (request) {
                    case DAY:
                    case WEREWOLF:
                        buttons[location].setText("DEAD");
                        checkrole(s);
                        break;
                    case SEER:
                        showdia(buttons[location]);
                        break;
                }

//                }
                changeButtonsStatus(false);
                v = view;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        startGame(v);
                    }
                }, 900);

            }
        }
    }

    public void showdia(Button b){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String s = b.getText().toString()+"role";
        int r = mPrefs.getInt(s, 0);
        if(r == 2)
            builder.setMessage("This player is a Werewolf");
        else
            builder.setMessage("This player is Innocent");
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();

        dialog.show();

        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable () {

            public void run() {
                if(dialog != null && dialog.isShowing()) dialog.dismiss();
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }

    // Function to read the result from newly created activity
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "back from activity result with code " + requestCode);
        request = requestCode;
        if(resultCode == GET_NAME){
            Log.d(TAG, "Activity result get name");
            // Storing result in names
            int location = data.getExtras().getInt("location");
            names[location] = (String) data.getExtras().get(Createrole.USER_NAME);
            buttons[location].setText(names[location]);
            buttons[location].setEnabled(false);
        }
        if (resultCode == WEREWOLF || requestCode == DAY) {
            Log.d(TAG, "Activity result killing");
            setTextAndButton(getString(R.string.werewolf_text));
            shouldResume = false;
        }
        if (resultCode == SEER ) {
            Log.d(TAG, "Activity result seer");
            setTextAndButton(getString(R.string.seer_text));
            shouldResume = false;
        }

    }

    public void setTextAndButton(String message) {
        TextView text = (TextView) findViewById(R.id.killtext);
        text.setText(message);
        text.setVisibility(View.VISIBLE);
        Button next = (Button) findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i].getText().equals("DEAD"))
                buttons[i].setEnabled(true);
            buttons[i].setOnClickListener(new ButtonClickListener(i));
        }
    }

    public void checkrole(String s){
        read();
        SharedPreferences.Editor ed = mPrefs.edit();
        int r = 0;
        for(int i = 1; i <= num_p; i++){
            String str = "player";
            str += i;
            str += "name";
            String name = mPrefs.getString(str,"");

            if(s.equals(name)) {
                r = i;
            }
        }
        String s2 = "player"+r+"role";
        int role = mPrefs.getInt(s2, 0);
        if(role == 1){
            num_v--;
            ed.putInt("num_v", num_v);
            ed.apply();
        }
        else if(role == 2){
            num_w--;
            ed.putInt("num_w", num_w);
            ed.apply();
        }
        else if(role == 3){
            num_s--;
            ed.putInt("num_s", num_s);
            ed.apply();
        }
    }

    private void changeButtonsStatus(boolean status) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(status);
            buttons[i].setOnClickListener(new ButtonClickListener(i));
        }
    }

    // disable button of player that has been killed
    private void killPlayer(int location) {
        buttons[location].setEnabled(false);
    }

    private void loadLayout() {
        // Get the data from the internet
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        int id = extra.getInt(MainActivity.ID, R.id.activity6);
        activity_level = id;

        switch (id) {
            case R.id.activity6:
                Log.d(TAG, "Going to activity6: " + R.id.activity6);
                ids = new int[]{R.id.player1_6, R.id.player2_6, R.id.player3_6,
                        R.id.player4_6, R.id.player5_6, R.id.player6_6};
                setContentView(R.layout.activity6);
                break;
            case R.id.activity7:
                Log.d(TAG, "Going to activity7: " + R.id.activity7);
                ids = new int[]{R.id.player1_7, R.id.player2_7, R.id.player3_7,
                        R.id.player4_7, R.id.player5_7, R.id.player6_7, R.id.player7_7};
                setContentView(R.layout.activity7);
                break;
            case R.id.activity8:
                Log.d(TAG, "Going to activity8: " + R.id.activity8);
                ids = new int[]{R.id.player1_8, R.id.player2_8, R.id.player3_8,
                        R.id.player4_8, R.id.player5_8, R.id.player6_8, R.id.player7_8,
                        R.id.player8_8};
                setContentView(R.layout.activity8);
                break;
            case R.id.activity9:
                Log.d(TAG, "Going to activity9: " + R.id.activity9);
                ids = new int[]{R.id.player1_9, R.id.player2_9, R.id.player3_9,
                        R.id.player4_9, R.id.player5_9, R.id.player6_9, R.id.player7_9,
                        R.id.player8_9, R.id.player9_9};
                setContentView(R.layout.activity9);
                break;
            case R.id.activity10:
                Log.d(TAG, "Going to activity10: " + R.id.activity10);
                ids = new int[]{R.id.player1_10, R.id.player2_10, R.id.player3_10,
                        R.id.player4_10, R.id.player5_10, R.id.player6_10, R.id.player7_10,
                        R.id.player8_10, R.id.player9_10, R.id.player10_10};
                setContentView(R.layout.activity10);
                break;
        }
    }

    public static void setTime(boolean time) {
        day_time = time;
    }

    public static void setKilling(boolean kill) {
        killing = kill;
    }

    public static void setChar(int c) {
        character = c;
    }

    public void startGame(View view) {
        int requestCode;
        Intent intent;
//        Intent intent = new Intent(this, Transition.class);
//        intent.putExtra(MainActivity.ID, activity_level);
//        intent.putExtra("turn", turn);
        if (!day_time) {
//            day_time = true;
            if (killing) {
                intent = new Intent(this, Night.class);
                requestCode = WEREWOLF;
            }
            else {
                intent = new Intent(this, Seer.class);
                requestCode = SEER;
            }
            intent.putExtra(MainActivity.ID, activity_level);
            intent.putExtra("turn", turn);
            intent.putExtra("killing", killing);
            turn++;
        }
        else {
            intent = new Intent(this, Daytime.class);
            intent.putExtra(MainActivity.ID, activity_level);
            intent.putExtra("turn", turn);
            day_time = false;
            requestCode = DAY;
        }
        startActivityForResult(intent, requestCode);
    }

    public void read(){
        num_w = mPrefs.getInt("num_w", 0);
        num_v = mPrefs.getInt("num_v", 0);
        num_s = mPrefs.getInt("num_s", 0);
        num_p = mPrefs.getInt("num_p", 0);
    }
}
