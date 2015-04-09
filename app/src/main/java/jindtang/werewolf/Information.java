package jindtang.werewolf;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Information extends ActionBarActivity {

    //private Button b[] = new Button[3];
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

//        b[0] = (Button) findViewById(R.id.info_1);
//        b[1] = (Button) findViewById(R.id.info_2);
//        b[2] = (Button) findViewById(R.id.info_3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_information, menu);
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

    public void set(View view){
        Intent intent = new Intent(context, Settingup.class);
        startActivity(intent);
    }

    public void night(View view){
        Intent intent = new Intent(context, Night_info.class);
        startActivity(intent);
    }

    public void daytime(View view){
        Intent intent = new Intent(context, Day_info.class);
        startActivity(intent);
    }

    public void victory(View view){
        Intent intent = new Intent(context, Victory.class);
        startActivity(intent);
    }
}
