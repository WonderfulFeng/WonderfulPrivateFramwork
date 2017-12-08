package com.example.administrator.myprivateframework.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.myprivateframework.R;
import com.example.administrator.myprivateframework.utils.WonderfulLogUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class Test2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        findViewById(R.id.btClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
        findViewById(R.id.btClick2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test2();
            }

        });
    }

    ObservableEmitter emitter;

    private void test() {
        if (i++ == 0)
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                    emitter = e;
                    e.onNext(1);
                    e.onNext(2);
                    e.onNext(3);
                }
            }).subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(Integer value) {
                    WonderfulLogUtils.logi("TAG", value + "");
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onComplete() {
                }
            });
        else {
            emitter.onNext(4);
            emitter.onNext(5);
            emitter.onNext(6);
            emitter.onNext(7);
        }
    }

    Disposable disposable;
    int i = 0;

    private void test2() {
    }

}

