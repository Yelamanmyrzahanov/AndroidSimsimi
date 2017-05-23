package com.hfad.androidsimsimi;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hfad.androidsimsimi.Adapter.CustomAdapter;
import com.hfad.androidsimsimi.Helper.HttpDataHandler;
import com.hfad.androidsimsimi.Models.ChatModel;
import com.hfad.androidsimsimi.Models.SimsimiModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<ChatModel> list_chat = new ArrayList<>();
    FloatingActionButton btn_send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView  = (ListView)findViewById(R.id.list_of_message);
        editText = (EditText)findViewById(R.id.user_message);
        btn_send_message = (FloatingActionButton)findViewById(R.id.fab);

        btn_send_message.setOnClickListener(new View.OnClickListener(){
            //get text from user input and create chat model
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                ChatModel  model = new ChatModel(text,true); // user send message
                list_chat.add(model);
                new SimsimiAPI().execute(list_chat); // create async task class to handle data

                //remove user message
                editText.setText("");
            }
        });
    }

    private  class SimsimiAPI extends AsyncTask<List<ChatModel>,Void,String>{

        String stream = null;
        List<ChatModel> models;
        String text = editText.getText().toString();

        @Override
        protected String doInBackground(List<ChatModel>... params) {
            //we will pass parameters api key and text from user to api ednpoint
            String url  = String.format("http://sandbox.api.simsimi.com/request.p?key=%s&lc=en&ft=1.0&text=%s",getString(R.string.simsimi_api),text);
            models = params[0] ; //set list_chat_model to local variable
            HttpDataHandler httpDataHandler = new HttpDataHandler(); //get result from api, using httpdatahandler to request response
            stream = httpDataHandler.GetHTTPData(url);
            return  stream;

        }
        //OVerride onPostExecute method and send data to listView
        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson(); // using gson to parse into gson object and create custom chatmodel
            SimsimiModel response = gson.fromJson(s,SimsimiModel.class);

            ChatModel chatModel = new ChatModel(response.getResponse(),false); // get response from simsimi
            models.add(chatModel);
            CustomAdapter adapter = new CustomAdapter(models,getApplicationContext());
            listView.setAdapter(adapter);
        }
    }
}
