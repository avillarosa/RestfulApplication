package com.csfu.cpsc41102.remoteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Student> studentList = new ArrayList<Student>();
    LinearLayout lv;
    Context ctx;
    boolean completed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_name_list);
        lv = findViewById(R.id.summary_id);
        ctx = this;

        WsAsyncTask wsAsyncTask = new WsAsyncTask(this);
        wsAsyncTask.execute();

        //tv.setText(new Integer(studentList.size()).toString());
        //
        /*
        Thread wsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                testWebService();
                //completed = true;
                //
                Thread inThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < studentList.size(); i++) {
                                    TextView tv = new TextView(ctx);
                                    tv.setText(studentList.get(i).getFirstName());
                                    lv.addView(tv);
                                }
                            }
                        });
                    }
                });
                inThread.start();
            }
        });
        wsThread.start();
        */
        //
        //tv.setText(new Integer(studentList.size()).toString());
        //while (!completed) {}
        //for (int i=0; i<studentList.size(); i++) {
        //    TextView tv = new TextView(ctx);
        //    tv.setText(studentList.get(i).getFirstName());
        //    lv.addView(tv);
        //}
    }

    public void updateScreen() {
        for (int i=0; i<studentList.size(); i++) {
            TextView tv = new TextView(ctx);
            tv.setText(studentList.get(i).getFirstName());
            lv.addView(tv);
        }
    }

    public void testWebService_add() {
        String request = "http://cs101i.fullerton.edu:400/students";
        try {
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            //
            Student sObj = new Student("Alex", "Chang", "3637774849");
            JSONObject jObj = sObj.toJsonObject();
            // attach the JSON string to the request message body
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            writer.write(jObj.toString());
            writer.flush();
            writer.close();
            //
            conn.getInputStream();
        } catch (Exception e) {

        }

    }

    public void testWebService() {
        String request = "http://cs101i.fullerton.edu:400/students";
        try {
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //
            InputStream in = conn.getInputStream();
            int respCode = conn.getResponseCode();
            Log.d("Remote Application ", "Response Code: " + respCode);
            String contentType = conn.getHeaderField("Content-Type");
            Log.d("Remote Application ", contentType);
            String respString;
            if (respCode == HttpURLConnection.HTTP_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }
                out.close();
                respString = new String(out.toByteArray());

                Log.d("Remote Application", respString);

                studentList = convertFromJson2Java(respString);

            }
        } catch(Exception e) {
            Log.d("Remote Application ", e.getMessage());
        }
    }

    ArrayList<Student> convertFromJson2Java(String str) throws Exception {
        JSONObject respObj = new JSONObject(str);
        JSONArray objList = respObj.getJSONArray("Result Set");
        ArrayList<Student> sList = new ArrayList<Student>();
        for (int i=0; i<objList.length(); i++) {
            Student sObj = new Student();
            sObj.fromJSON(objList.getJSONObject(i));
            sList.add(sObj);
        }
        return sList;
    }
}
