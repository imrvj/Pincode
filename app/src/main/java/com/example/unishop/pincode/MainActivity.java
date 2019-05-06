package com.example.unishop.pincode;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchWeatherDetails();
            }
        });
    }

    private void fetchWeatherDetails() {
        //Obtain an instance of Retrofit by calling the static method.
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        /*
        The main purpose of Retrofit is to create HTTP calls from the Java interface based on the annotation associated with each method. This is achieved by just passing the interface class as parameter to the create method
        */
        WeatherAPIs weatherAPIs = retrofit.create(WeatherAPIs.class);
        /*
        Invoke the method corresponding to the HTTP request which will return a Call object. This Call object will used to send the actual network request with the specified parameters
        */
        Call call = weatherAPIs.getPincode(editText.getText().toString());
        /*
        This is the line which actually sends a network request. Calling enqueue() executes a call asynchronously. It has two callback listeners which will invoked on the main thread
        */
        call.enqueue(new Callback() {
            private static final String TAG ="" ;

            @Override
            public void onResponse(Call call, Response response) {
                /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                 */

                    Log.i(TAG, "onResponse: "+response);


                if (response.body() != null) {


                    WResponse wResponse = (WResponse) response.body();

                    if (wResponse.getStatus().contains("Error")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                MainActivity.this);
                        alertDialogBuilder.setTitle("Error");
                    }

                    else {
                        Gson gson = new Gson();

                        String json = gson.toJson(wResponse.getPostOffice());

                        JSONArray list = null;
                        JSONObject indexwala = null;
                        Object State = null;
                        Object Dist = null;
                        Object Reg = null;
                        Object con = null;
                        Object city = "";

                        try {
                            list = new JSONArray(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    MainActivity.this);
                            alertDialogBuilder.setTitle("Error");
                        }

                        if(list==null)
                        {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    MainActivity.this);
                            alertDialogBuilder.setTitle("Error");
                        }

                        try {
                            indexwala = list.getJSONObject(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    MainActivity.this);
                            alertDialogBuilder.setTitle("Error");
                        }


                        try {
                            State = indexwala.get("State");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            Dist = indexwala.get("District");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Reg = indexwala.get("Region");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            con = indexwala.get("Country");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            city = indexwala.get("Name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i(TAG, "onResponse: " + list);


                        textView.setText(" Name :" + city +
                                "\n \n District :" + Dist +
                                "\n \n Region :" + Reg +
                                "\n \n State :" + State +
                                "\n \n Country : " + con
                        );


                    }
                }

                else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            MainActivity.this);
                    alertDialogBuilder.setTitle("Error");
                }

            }


            @Override
            public void onFailure(Call call, Throwable t) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);
                alertDialogBuilder.setTitle("Error");
            }
        });
    }
}