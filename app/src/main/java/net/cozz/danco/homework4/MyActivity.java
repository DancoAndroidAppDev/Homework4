package net.cozz.danco.homework4;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
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
        CellViewAdapter adapter = (CellViewAdapter) gridView.getAdapter();
        TextView view = new TextView(this);
        view = (TextView) adapter.getView(0, view, gridView);
        animate(view);
        Log.i(TAG, view.getText().toString());
    }

    public void animate(TextView textView){
        final int RED = 0xffFF8080;
        final int BLUE = 0xff8080FF;

        ValueAnimator colorAnim = ObjectAnimator.ofInt(textView, "textColor", RED, BLUE);
        colorAnim.setDuration(1000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

    }

    public void fadeInOut(final TextView textView){
        Animation animFadeIn, animFadeOut;

        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        animFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                continueAnim(textView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        textView.startAnimation(animFadeIn);
    }

    public void continueAnim(final TextView textView){
        Animation animFadeOut;

        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        textView.startAnimation(animFadeOut);
    }
}
