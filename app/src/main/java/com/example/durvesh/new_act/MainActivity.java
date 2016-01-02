package com.example.durvesh.new_act;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String[] str;
    String su0 = "Latitude/Longitude(from)\n",su1 = "Time\n";
    double[] ti;
    Button go,sub,time;
    EditText num,adt,ll,ll2,adt2;
    TextView coun,test,test2;
    int d,i ;
    boolean yes = false,to = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go = (Button)findViewById(R.id.button);
        sub = (Button)findViewById(R.id.button2);
        time = (Button)findViewById(R.id.button3);

        num = (EditText)findViewById(R.id.editText);
        adt = (EditText)findViewById(R.id.editText3);
        ll =  (EditText)findViewById(R.id.editText2);
        ll2 = (EditText)findViewById(R.id.editText4);
        adt2 = (EditText)findViewById(R.id.editText5);
        coun = (TextView)findViewById(R.id.textView3);
        test = (TextView)findViewById(R.id.testing);
        test2 = (TextView)findViewById(R.id.testing2);

        //print1 = (TextView)findViewById(R.id.textView8);
        //print2 = (TextView)findViewById(R.id.textView9);

        sub.setEnabled(false);
        time.setEnabled(false);
        adt.setEnabled(false);
        ll.setEnabled(false);

        go.setOnClickListener(this);
        time.setOnClickListener(this);
        sub.setOnClickListener(this);

    }

    public void setVal(){
        final int p0 = i,p1 = i+d;
        String temp = ll.getText().toString();
        String pemp = ll2.getText().toString();
        if(temp != null){
            str[p0] = temp;
            str[p1] = pemp;
            Log.d("here",temp);
        }
        temp = adt.getText().toString();
        pemp = adt2.getText().toString();
        if(temp != null){
            try{
                double temp2 = Double.parseDouble(temp);
                double pemp2 = Double.parseDouble(pemp);
                ti[i] = temp2;
                ti[i+d] = pemp2;
                i++;
                su0 += str[p0] + "  ";
                su0 += str[p1] + "" + '\n';
                su1 += temp2 + "  " ;
                su1 += pemp2 + "" + '\n';
                String soo = (p0 + 1) + " ";
                coun.setText(soo);
                test.setText(su0);
                test2.setText(su1);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"decimal values required",Toast.LENGTH_SHORT).show();
            }
        }

        ll.setText("");
        adt.setText("");
        ll2.setText("");
        adt2.setText("");
        if(i >= d)sub.setEnabled(false);
    }

    public void getVal(){
        if(i >= d){
            //time.setEnabled(false);
            Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
            //i++;
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
          case R.id.button:
            try {
                String s = num.getText().toString();
                d = Integer.parseInt(s);
                str = new String[2*d];
                ti = new double[2*d];
                sub.setEnabled(true);
                time.setEnabled(true);
                adt.setEnabled(true);
                ll.setEnabled(true);
                go.setEnabled(false);
            } catch (NumberFormatException nfe) {
                Toast.makeText(getApplicationContext(), "number required", Toast.LENGTH_SHORT).show();
            }
                break;

          case R.id.button3:
              getVal();
              Intent intent = new Intent(MainActivity.this,Second.class);
              Log.d("tarun",d+"");
              intent.putExtra("length",d+"");
              intent.putExtra("pickArray",str);
              intent.putExtra("time",ti);
              startActivity(intent);
              break;
          case R.id.button2:
                setVal();
                break;
        }
    }
}
