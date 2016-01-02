package com.example.durvesh.new_act;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Second extends AppCompatActivity implements View.OnClickListener {


     int d;
     String[] str ;
     double[] dou ;
    double[][] arr ;
     double[] ran ;
    boolean done = false;
    int check = 0;
    int h=0,m=0;
    String s00 = "";
    TextView text;
    Button go,range;
    EditText Arange,Brange;
    // int sheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        d = Integer.parseInt(getIntent().getStringExtra("length"));
        str = getIntent().getStringArrayExtra("pickArray");
        dou  = getIntent().getDoubleArrayExtra("time");
         arr  = new double[2*d][2*d];
        ran = new double[2*d];
        Log.d("len",d+" ");

        for(int i = 0 ; i < 2*d ; i++){
            ran[i] = 3;
        }

        for(int i = 0 ; i < 2*d ; i++) {
            if(i == 11)done = true;
            final int s1 = i;
            String address = "Indian%Institute%of%Technology%Bombay,Powai,Mumbai,Maharashtra,India";
            Ion.with(this)
                    .load("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "")
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String s) {
                            //Log.d("Json Obtained", s);
                            try {

                                JSONObject obj = new JSONObject(s);
                                JSONArray data = obj.getJSONArray("results");
                                if (data != null) {
                                    JSONObject data2 = data.getJSONObject(0);
                                    JSONObject data3 = data2.getJSONObject("geometry");
                                    if (data3 != null) {
                                        JSONObject data4 = data3.getJSONObject("location");
                                        String lat = data4.getString("lat");
                                        String lng = data4.getString("lng");
                                        str[s1] = lat+","+lng;
                                        Log.d("s_arr", str[s1]);
                                    }
                                }

                            } catch (Throwable t) {

                                Log.e("My Aaddress", "Could not parse malformed JSON: \"" + s + "\"");

                            }
                        }
                    });

        }

        if(done){
            for(int i = 0;i < 2*d; i++){
                final String p2 = str[i];
                Log.d("strArr",p2);
            }
        }

     /* str[0] = "19.10484,72.82663";str[1]="19.13209,72.83154";
        str[2]="19.13977,72.80922";str[3]="19.19946,72.90691";
        str[4]="19.11629,72.84365";str[5]="19.09678,72.86561";
        str[6]="19.10312,72.87237";str[7]="19.10413,72.88677";
        str[8]="19.10746,72.88791";str[9]="19.11196,72.89746";
        str[10]="19.11454,72.89704";str[11]="19.08878,72.88134";  */
        text = (TextView)findViewById(R.id.textView2);
        range = (Button)findViewById(R.id.getVal);
        Arange = (EditText)findViewById(R.id.editText);
        Brange = (EditText)findViewById(R.id.editText2);
        Arange.setText("0");
        Brange.setText("0");
        go = (Button)findViewById(R.id.go);
        go.setOnClickListener(this);
        range.setOnClickListener(this);
        //go.setEnabled(false);
        if(done) {
            for (int i = 0; i < 2*d; i++) {
                final String point1 = str[i];
                for (int j = 0; j < 2*d; j++) {
                    final String point2 = str[j];
                    final int p1, p2;
                    p1 = i;
                    p2 = j;
                    Ion.with(this)
                            .load("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + point1 + "&destinations=" + point2 + "")
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String s) {
                                    //Log.d("strings", point1);
                                    try {
                                        Log.d("before", point1 + " " + point2);
                                        JSONObject obj = new JSONObject(s);
                                        JSONArray data = obj.getJSONArray("rows");
                                        JSONObject data2 = data.getJSONObject(0);
                                        if (data2 != null) {
                                            JSONArray data3 = data2.getJSONArray("elements");
                                            if (data3 != null) {
                                                JSONObject data4 = data3.getJSONObject(0);
                                                //JSONObject data5 = data4.getJSONObject("distance");
                                                //JSONObject data6 = data3.getJSONObject(1);
                                                JSONObject data7 = data4.getJSONObject("duration");

                       /*     JSONArray data2 = data["elements"].getJSONArray("distance");
                            JSONArray data3 = data["elements"].getJSONArray("duration");
                            if(data2 != null){
                                String[] names = new String[data.length()];
                                String[] birthdays = new String[data.length()];
                                for(int i = 0 ; i < data.length() ; i++) {
                                    birthdays[i] = data.getString("birthday");
                                    names[i] = data.getString("name");
                                }
                            }  */
                                                String[] sre = (data7.getString("text")).split(" ");
                                                if (sre.length > 2) {
                                                    h = Integer.parseInt(sre[0]);
                                                    m = Integer.parseInt(sre[2]);
                                                } else {
                                                    m = Integer.parseInt(sre[0]);
                                                }
                                                double k = (double) h + ((double) m) / 60;
                                                arr[p1][p2] = k;
                                                //String d = Integer.toString(k);
                                                //Log.d("status","yes");
                                                // Log.d("My App", arr[p1][p2] + " ");
                                                //text.setText(s00 + " ");

                                                //if(kite == 12)kite2++;
                                            }
                                        }
                                    } catch (Throwable t) {
                                        Log.e("My App", "Could not parse malformed JSON: \"" + s + "\"");

                                    }


                                }
                            });
                }
            }

        }
//        printArray(arr);


    }
    void solve() {
        int[] visitedA = new int[2*d];
        boolean restore = false,found;
        int watch = 0;
        //int[] visitedB = new int[12];
        double[] initA = new double[d];
        double[] initB = new double[d];
        double temp,temp2;

        for(int i = 0; i < d ; i++){
            initA[i] = dou[i]*60;
        }

        for(int i = d; i < 2*d ; i++ ){
            initB[i-d] = dou[i]*60;
        }

        int count = 0,k1,k2,k3;
        while(watch < d) {
            found = false;
            k1 = min(initA, d);
            temp = initA[k1];
            initA[k1] = 2000;
            k2 = min(initA, d);
            initA[k1] = temp;
            k3 = min(initB, d);
            //if(k1 == 1)Log.d("Missing",(initB[k3] - arr[k1][k3+6] - initA[k1]  < initA[k2] - arr[k1][k3+6] - initA[k1]) + " " + k2 + " "+ k3);
            visitedA[count++] = k1;
            if(initA[k1] < 2000 && initA[k2] < 2000 && k2!=k1){Log.d("CollectFrom", (initA[k1]/60) + " "); s00 += "CollectFrom " + initA[k1]/60 + '\n';}
            else if(initA[k1] == 2000){
                while(initB[k3] != 2000){
                    Log.d("dropAt",(initB[k3]/60) + " ");
                    s00 += "dropAt " + initB[k3]/60 + '\n';
                    initB[k3] = 2000;
                    k3 = min(initB,6);
                }
                break;
            }else if(k2 == k1){
                Log.d("CollectFrom", (initA[k1]/60) + " " + k1);
                s00 += "CollectFrom " + initA[k1]/60 + '\n';
                while(initB[k3] != 2000){
                    Log.d("dropAt",(initB[k3]/60) + " ");
                    s00 += "dropAt " + initB[k3]/60 + '\n';
                    initB[k3] = 2000;
                    k3 = min(initB,d);
                }
                break;
            }
            if ((initA[k2] - arr[k1][k2] - initA[k1] - ran[k2]) < 0) {
                Log.d("PointsCannotBeAre", (initA[k2]/60) + " " + (initB[k2]/60) + " " );
                s00 += "PointsCannotBeAre " + initA[k2]/60 + " " + initB[k2]/60 + '\n';
                initA[k2] = 2000;
                initB[k2] = 2000;
                initA[k1] = 2000;
                watch--;
                restore = true;
            } else if (initB[k3] - arr[k1][k3+6] - initA[k1] <= initA[k2] - arr[k1][k3+6] - initA[k1]) {
                for (int i = 0; i < 12; i++) {
                    if (visitedA[i] == k3) {
                        Log.d("drop at", (initB[k3]/60) + " ");
                        s00 += "drop at " + initB[k3]/60 + '\n';
                        found = true;
                        temp2 = initB[k3];
                        initB[k3] = 2000;
                        int k4 = min(initB,d);
                        initB[k3] = temp2;
                        for(int z = 0 ; z < 6 ; z++){
                            if(visitedA[z] == k4){
                                if((initB[k4]-arr[k3+d][k4]-initB[k3]) < (initA[k2]-arr[k3+d][k2]-initB[k3])){
                                    Log.d("dropAt",(initB[k4]/60) + " ");
                                    s00 += "dropAt " + initB[k4]/60 + '\n';
                                    initB[k4] = 2000;
                                    initA[k1] = 2000;
                                    visitedA[k4] = 0;
                                    watch++;
                                    break;
                                }
                            }
                        }
                        visitedA[k3] = 0;
                        initB[k3] = 2000;
                        initA[k3] = 2000;
                        initA[k1] = 2000;
                        // watch++;
                        break;
                    }
                }
                if (!found) {
                    if (initA[k1] + arr[k1][k3] + arr[k3][k3+d] <= initB[k3] + ran[k3+d]) {
                        //found = false;
                        Log.d("collectFrom", (initA[k3]/60) + " ");
                        Log.d("drop at", (initB[k3]/60) + " ");
                        s00 += "collectFrom " + initA[k3]/60 + '\n' + "drop at " + initB[k3]/60 + '\n';
                        int k4 = min(initB,d);
                        for(int z = 0 ; z < 6 ; z++){
                            if(visitedA[z] == k4){
                                if((initB[k4]+arr[k3+d][k4]-initB[k3]) < (initA[k2]+arr[k3+d][k2]-initB[k3])){
                                    Log.d("dropAt",(initB[k4]/60) + " ");
                                    s00 += "dropAt " + initB[k4]/60 + '\n';
                                    initB[k4] = 2000;
                                    initA[k4] = 2000;
                                    initA[k1] = 2000;
                                    visitedA[k4] = 0;
                                    //watch++;
                                    break;
                                }
                            }
                        }
                        initB[k3] = 2000;
                        initA[k3] = 2000;
                        initA[k1] = 2000;
                        watch++;
                    } else {
                        Log.d("notPossibleToGoTo", (initB[k3]/60) + " ");
                        s00 += "notPossibleToGoTo " + initB[k3]/60 + '\n';
                        initB[k3] = 2000;
                        initA[k3] = 2000;
                        initA[k1] = 2000;
                        watch++;
                    }
                }
            } else {
               /* Log.d("collectFrom", (initA[k2]/60) + " " );
                s00 += "collectFrom " + initA[k2]/60 + '\n';
                initA[k2] = 2000;
                initA[k1] = 2000;
                visitedA[count++] = k2;

                watch++;  */
            }
            if(restore){
                initA[k1] = temp;
                restore = false;
            }else{
                initA[k1] = 2000;
                watch++;
            }
        }

   /*     k1 = min(initA,6);
        k3 = min(initB,6);
        double[] minArr = new double[6];
        for(int i = 0 ; i < 6 ; i++){
            minArr[i] = 1000;
        }
        if(initA[k1] == 1000 && (last >= 0)){
            if(initB[k3] != 1000){
                for(int i = 0; i < 6 ; i++){
                    if(initB[i] < 1000){
                        minArr[i] = arr[last][i] + initB[i];
                    }
                }
            }
            int i = 0;
            while(minArr[i] != 1000){
                i = min(minArr,6);
                if(minArr[i] > 0 && minArr[i] < 1000) {
                    Log.d("dropAt",initB[i] + " ");
                }
            }
        } */

    }

    void getValue(){

        if(check >= 2*d){
            Log.d("IndexOutOfRange", check + " ");
            return;
        }
        double B = Double.parseDouble(Arange.getText().toString());
        double C = Double.parseDouble(Brange.getText().toString());
        Arange.setText("0");
        Brange.setText("0");
        ran[check] = B / 60;
        ran[check + 1] = C / 60;
        //Log.d("ran",B + " " + C);


        check += 2;
        if (check == 2*d) {
            go.setEnabled(true);
            range.setEnabled(false);
        }

    }

    void print(){
        for(int i = 0 ; i < 2*d ; i++){
            Log.d("val", ran[i] + " ");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.go:
                //getValue();
                solve();
                text.setText(s00);
                //Log.d("arrays", arr[1][6] + " " + arr[1][2]);
                s00 = "";
                //print();
                break;
            case R.id.getVal:
                //Toast.makeText(getApplicationContext(),"worked",Toast.LENGTH_LONG).show();
                getValue();
                break;
        }

    }


    public int min(double A[], int n) {
        int min = 0;
        for (int i = 0; i < n; i++) {
            min = (A[i] > A[min]) ? min : i;
        }
        // if (A[min] != 2000) return min;

        return min;
    }
}
