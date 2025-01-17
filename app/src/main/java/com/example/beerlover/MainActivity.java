package com.example.beerlover;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private TextView VtxtWelcome;
    private Button btnList, btnScan, btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VtxtWelcome = findViewById(R.id.VtxtWelcome);
        btnList = findViewById(R.id.btnList);
        btnScan = findViewById(R.id.btnScan);
        btnAdd = findViewById(R.id.btnAdd);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myListActivity = new Intent(getApplicationContext(), MyListActivity.class);
                startActivity(myListActivity);
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);

                intentIntegrator.setPrompt("For flash use volume up key");

                intentIntegrator.setBeepEnabled(true);

                intentIntegrator.setOrientationLocked(true);

                intentIntegrator.setCaptureActivity(Capture.class);

                intentIntegrator.initiateScan();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent add = new Intent(getApplicationContext(), add_beer.class);
                startActivity(add);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult.getContents()!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result");

            MyDBHandler hand = new MyDBHandler(this,null,null,2);

           BeerDB b ;
           b =   hand.findHandler(intentResult.getContents());

           hand.updateHandler(b.getCode(),b.getName(),b.getDet(),1,b.getPic());
            Intent myListActivity = new Intent(getApplicationContext(), MyListActivity.class);

/**            myListActivity.putExtra("name",b.getName());
            myListActivity.putExtra("details",b.getDet());
            myListActivity.putExtra("draught",b.getPic());
            if(!b.getDesc().isEmpty())
                myListActivity.putExtra("desc",b.getDesc());
 **/
            startActivity(myListActivity);

            /**
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            builder.show();
        **/
        }else{
            Toast.makeText(getApplicationContext(),"You did not scan anything",Toast.LENGTH_SHORT).show();

        }
    }

}