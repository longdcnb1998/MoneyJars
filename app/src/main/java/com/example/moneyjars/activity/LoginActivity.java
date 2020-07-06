package com.example.moneyjars.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneyjars.DialogGuide;
import com.example.moneyjars.R;
import com.example.moneyjars.entity.User;
import com.example.moneyjars.util.DialogUtils;
import com.example.moneyjars.viewmodel.UserViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "LongDinh";
    private GoogleSignInClient mGoogleSignInClient;
    private TextView tv_wellCome;
    private ImageView Img_wellCome;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private UserViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.getListUser().observe(this, data -> {
            if (data != null) {
            }
        });

        preferences = getSharedPreferences("UserAccount", MODE_PRIVATE);
        editor = preferences.edit();

        String userName = preferences.getString("UserName", "");
        String userEmail = preferences.getString("Email", "");
        if (userName.length() > 0 && userEmail.length() > 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "Không có dữ liệu");
            initUI();
            initData();
        }
    }

    private void initUI() {
        tv_wellCome = findViewById(R.id.textView);
        Img_wellCome = findViewById(R.id.img_wellcome);


        SignInButton btn_signIn = findViewById(R.id.btn_signIn);
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.my_transition);
        tv_wellCome.startAnimation(animation);
        Img_wellCome.startAnimation(animation);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initData() {

    }

    private void updateUI(GoogleSignInAccount account) {
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                User user = new User(personName, personEmail, personPhoto + "");
                viewModel.insertUser(user);
                editor.putString("UserName", personName);
                editor.putString("Email", personEmail);
                editor.putString("firstLogin", "first");
                editor.commit();
                if (DialogUtils.enableShowDialogFragment(getSupportFragmentManager(), DialogGuide.class.getSimpleName())) {
                    new DialogGuide().show(getSupportFragmentManager(), DialogGuide.class.getSimpleName());
                }

                Log.d(TAG, personName + " " + personGivenName + " " + personFamilyName + " " + personEmail + " " +
                        personId + " " + personPhoto);
            }
            updateUI(acct);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
}