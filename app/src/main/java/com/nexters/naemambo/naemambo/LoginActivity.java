package com.nexters.naemambo.naemambo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private CallbackManager callbackManager;
    String id = "";
    String name = "";
    String email = "";
    Context mContext;
    private LoginButton loginButton;
    private RelativeLayout layout_root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();


        //facebook tracking
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);


        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.btn_login);
        ImageView btn_facebook = (ImageView) findViewById(R.id.btn_facebook);
        layout_root = (RelativeLayout) findViewById(R.id.layout_root);

        btn_facebook.setOnClickListener(this);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {//로그인이 성공되었을때 호출
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // 로그아웃버튼으로 변경!

                                        try {
                                            //String id1=object.getString("id");
                                            //String email1=object.getString("email");
                                            Log.e(TAG, "facebook res tostring" + response.toString());
                                            id = (String) response.getJSONObject().get("id");//페이스북 아이디값
                                            name = (String) response.getJSONObject().get("name");//페이스북 이름
                                            //email = (String) response.getJSONObject().get("email");//이메일

                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        //서버에 id ,name,email등 정보를 보내고 조회후에 승인되면
                                        //고유 키를 받아서 sharedPreference에 저장
                                        //로그아웃하기 전까지 담아둠

                                        // new joinTask().execute(); //자신의 서버에서 로그인 처리를 해줍니다

                                        //로그인성공하면 메인으로
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "로그인을 취소 하였습니다!", Toast.LENGTH_SHORT).show();
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        exception.printStackTrace();
                        Toast.makeText(LoginActivity.this, "에러가 발생하였습니다", Toast.LENGTH_SHORT).show();
                        // App code
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        Log.d("myLog", "requestCode  " + requestCode);
        Log.d("myLog", "resultCode" + resultCode);
        Log.d("myLog", "data  " + data.getDataString());

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_facebook:
                loginButton.performClick();
                break;
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        Glide.with(this).load(R.drawable.login_background).asBitmap().into(new SimpleTarget<Bitmap>(layout_root.getWidth(), layout_root.getHeight()) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                layout_root.setBackground(drawable);
            }
        });
    }
}
