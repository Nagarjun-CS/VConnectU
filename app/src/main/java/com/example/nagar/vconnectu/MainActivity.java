package com.example.nagar.vconnectu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech myTTS;
    private SpeechRecognizer mySpeechRecognizer;
    int count = 0;

    //Button button5;
    //Button button6 = (Button)findViewById(R.id.button6);

    /*public  void next(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
        intent.putExtra("num",1);
        startActivity(intent);
    }*/
    public  void login(View view)
    {

        Intent intent = new Intent(getApplicationContext(),login.class);
        intent.putExtra("num",1);
        startActivity(intent);
    }
    public  void signup(View view)
    {
        Intent intent1 = new Intent(getApplicationContext(),Register.class);
        intent1.putExtra("num",1);
        startActivity(intent1);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        count = intent.getIntExtra("num",0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                mySpeechRecognizer.startListening(intent);
            }
        });

        initializeTextToSpeech();
        initializeSpeechRecognizer();

    }

    private void initializeSpeechRecognizer() {
        if(SpeechRecognizer.isRecognitionAvailable(this))
        {
            mySpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mySpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int i) {

                }

                @Override
                public void onResults(Bundle bundle) {
                    List<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                    processResult(results.get(0));

                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });
        }
    }

    private void processResult(String command) {
        command = command.toLowerCase();

        if(command.indexOf("what") != -1)
        {
            if(command.indexOf("your name") != -1)
            {
                speak("My name is V Connect U");
            }
        }


        if(command.indexOf("how") != -1)
            {
                if(command.indexOf("you") != -1)
                {
                    speak("I am fine, how r u");
                }
            }
        if(command.indexOf("who") != -1)
        {
            if(command.indexOf("created") != -1)
            {
                speak("i was created by nagarjun nihal and deshik");
            }
        }
        if(command.indexOf("go") != -1)
        {
            if(command.indexOf("login") != -1)
            {
                Intent intent2 = new Intent(getApplicationContext(),login.class);
                intent2.putExtra("num",1);
                startActivity(intent2);


            }
        }
        if(command.indexOf("go") != -1)
        {
            if(command.indexOf("sign up") != -1)
            {
                Intent intent2 = new Intent(getApplicationContext(),Register.class);
                intent2.putExtra("num",1);
                startActivity(intent2);


            }
        }
        if(command.indexOf("sign up") != -1)
        {
                Intent intent2 = new Intent(getApplicationContext(),Register.class);
                intent2.putExtra("num",1);
                startActivity(intent2);
        }
        if(command.indexOf("login") != -1)
        {
            Intent intent2 = new Intent(getApplicationContext(),login.class);
            intent2.putExtra("num",1);
            startActivity(intent2);
        }
        }

    private void initializeTextToSpeech() {
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(myTTS.getEngines().size() == 0)
                {
                    Toast.makeText(MainActivity.this, "There is no TTS engine on your phone", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if(count == 0)
                {
                    myTTS.setLanguage(Locale.US);
                    speak("Hello welcome to  V Connect U. Feel free to use our voice command");
                    count = 1;
                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >= 21)
        {
            myTTS.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            myTTS.speak(message,TextToSpeech.QUEUE_FLUSH,null);
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
    protected void onPause() {
        super.onPause();
        myTTS.shutdown();
    }
}
