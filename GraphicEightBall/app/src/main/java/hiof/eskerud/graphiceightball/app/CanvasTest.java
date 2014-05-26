package hiof.eskerud.graphiceightball.app;

/**
 * Created by Martin on 19.05.2014.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class CanvasTest extends View implements Runnable {

    private AccelerometerReader accelerometerReader;
    private long time = 1;
    private float cx;
    private float cy;
    private float radius;
    public ArrayList<String> fortunes = new ArrayList<String>();
    private Paint paintBlack;
    private Paint paintWhite;
    private int offsetX = 0, offsetY = 0;


    public CanvasTest(Context context) {
        super(context);
        setupFortuneList();
        setupCanvasProperties();
    }

    //Constructor necessary for the view.
    public CanvasTest(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupFortuneList();
        setupCanvasProperties();
    }

    //Constructor necessary for the view.
    public CanvasTest(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet);
        setupFortuneList();
        setupCanvasProperties();
    }

    private void setupCanvasProperties() {
        radius = 470;
        paintWhite = new Paint();
        paintWhite.setColor(Color.WHITE);
        paintBlack = new Paint();
        paintBlack.setColor(Color.BLACK);

        setFocusableInTouchMode(true);
        setClickable(true);
        setLongClickable(true);
    }


    public void setAccelerometerReaderAndStartDrawThread(AccelerometerReader accelerometerReader) {
        this.accelerometerReader = accelerometerReader;
        Thread thread = new Thread(this);
        thread.start();
    }


    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        cx = getWidth() / 2;
        cy = getHeight() / 2;
    }

    public void draw(Canvas canvas) {

        super.draw(canvas);
        canvas.drawCircle(cx + offsetX, cy + offsetY, radius, paintBlack);
        canvas.drawCircle(cx + offsetX, cy + offsetY, (int) (radius * 0.45), paintWhite);
        paintBlack.setTextSize(350);
        canvas.drawText("8", (cx - 100)+offsetX, (cy + 120)+offsetY, paintBlack);
        paintBlack.setTextSize(65);
        canvas.drawText(fortunes.get(accelerometerReader.getDestiny()), 1, 1550, paintBlack);
    }

    public void run() {

        while (true) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                Log.e(MainActivity.TAG, "shit broke, yo");
            }
            update();
            postInvalidate();
        }
    }

    private void update() {
        //multplying by 15 to get a nice animation going, just as the default small float numbers are harder to work with for this purpose.
        offsetX = (int) accelerometerReader.getLastX()*15;
        offsetY = (int) accelerometerReader.getLastY()*15;
    }


    private void setupFortuneList() {
        //TODO: restructure to allow centered text.
        fortunes.add("The Eightball Knows All");
        fortunes.add("Svært dårlig");
        fortunes.add("Det kommer til å gå bra");
        fortunes.add("Mitt svar er nei");
        fortunes.add("Sånn jeg ser det, ja");
        fortunes.add("Vurder andre planer");
        fortunes.add("Spør igjen senere");
        fortunes.add("Best å ikke si det akkuratt nå");
        fortunes.add("Kildene mine er skeptiske");
        fortunes.add("Det finnes ting som peker mot ja");
        fortunes.add("Garantert");
        fortunes.add("Ja");
    }


}
