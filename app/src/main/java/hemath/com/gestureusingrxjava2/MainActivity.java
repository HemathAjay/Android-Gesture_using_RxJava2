package hemath.com.gestureusingrxjava2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import hemath.com.gesture.Swipe;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Swipe swipe;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        swipe = new Swipe();
        disposable = swipe.observe()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(swipeEvent -> setText(swipeEvent.toString()));
    }


    private void setText(String text)
    {
        textView.setText(text);
    }


    @Override public boolean dispatchTouchEvent(MotionEvent event) {
        return swipe.dispatchTouchEvent(event) || super.dispatchTouchEvent(event);
    }

    @Override protected void onPause() {
        super.onPause();
        safelyUnsubscribe(disposable);
    }

    private void safelyUnsubscribe(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
