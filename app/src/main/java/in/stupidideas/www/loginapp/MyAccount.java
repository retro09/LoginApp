package in.stupidideas.www.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyAccount extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            Intent myacc=new Intent(MyAccount.this,MainActivity.class);
            startActivity(myacc);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        textView=(TextView) findViewById(R.id.acctext);
        String result = user.getEmail();

        String username = result.split("\\@")[0];
        textView.setText("Hello " + username);
        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                Intent myacc=new Intent(MyAccount.this,MainActivity.class);
                startActivity(myacc);
            }
        });
    }
}
