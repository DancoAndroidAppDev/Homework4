package net.cozz.danco.homework4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;


public class MyActivity extends Activity {
    public static final String TAG = MyActivity.class.getCanonicalName();

    private int fontSize = 4;

    private BaseAdapter adapter = null;

    public int getFontSize() {
        return fontSize;
    }

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        adapter = new CellViewAdapter(this);
        loadContent();
    }


    private void loadContent() {
        final List<String> capitals =
                Arrays.asList(getResources().getStringArray(R.array.capitals));

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Capital is " + capitals.get(position), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), ViewFlagActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.increase_font) {
            fontSize += 1;
            loadContent();
            return true;
        } else if (id == R.id.decrease_font) {
            fontSize -= 1;
            loadContent();
            return true;
        } else if (id == R.id.animate) {
            doAnimation();
        }
        return super.onOptionsItemSelected(item);
    }


    public void doAnimation() {

        String str =(String) gridView.getAdapter().getItem(0);
        Log.i(TAG, str);
    }
}
