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

public class MainActivity extends AppCompatActivity {

    private EditText rgemail;
    private EditText rgpassword;
    private Button rgsubmit;
    private TextView rglogin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    //For Registration
    private void registerUser(){
        String email = rgemail.getText().toString().trim();
        String password = rgpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter your Email Id.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter your Password.", Toast.LENGTH_SHORT).show();
        return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Congratulations, you have registered Sucessfully. :)", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent login=new Intent(getApplicationContext(), Login.class);
                        startActivity(login);
                }
                else{
                    Toast.makeText(MainActivity.this, "Registration Error!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null){
            finish();
            Intent login=new Intent(getApplicationContext(),Login.class);
            startActivity(login);
        }

        //ProgressDialog
        progressDialog = new ProgressDialog(this);


        //Email
        rgemail = (EditText) findViewById(R.id.regemail);

        //Password
        rgpassword = (EditText)findViewById(R.id.regpasswd);

        //Submit Button
        rgsubmit = (Button)findViewById(R.id.regsubmit);
        rgsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        //Login Path
        rglogin = (TextView) findViewById(R.id.reglogin);
        rglogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(MainActivity.this, Login.class);
                startActivity(reg);
            }
        });
    }
}
