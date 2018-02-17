package in.stupidideas.www.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText lgemail;
    private EditText lgpassword;
    private Button lgsubmit;
    private TextView lglogin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private void loginUser(){
        String email = lgemail.getText().toString().trim();
        String password = lgpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter your Email Id.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter your Password.", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Congratulations, you have logged in Sucessfully. :)", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent logedin=new Intent(getApplicationContext(), MyAccount.class);
                    startActivity(logedin);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            Intent login=new Intent(getApplicationContext(),MyAccount.class);
            startActivity(login);
        }

        //ProgressDialog
        progressDialog = new ProgressDialog(this);


        //Email
        lgemail = (EditText) findViewById(R.id.lgemail);

        //Password
        lgpassword = (EditText)findViewById(R.id.lgpasswd);

        //Submit Button
        lgsubmit = (Button)findViewById(R.id.lglogin);
        lgsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }
}
