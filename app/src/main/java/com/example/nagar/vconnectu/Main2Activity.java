package com.example.nagar.vconnectu;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;


import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TextToSpeech myTTS;
    private SpeechRecognizer mySpeechRecognizer;
    int number;
    int check =0;
    Timer timer;
    //Button button;
    //Button button2;
    //Button button3;
    //Button button4;

    public void drop(View view)
    {
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();


    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
       switch (menuItem.getItemId()) {
           case R.id.logout: {
               FirebaseAuth.getInstance().signOut();
               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               intent.putExtra("num", 1);
               startActivity(intent);
               finish();
           }
               return true;
           case R.id.about:
               //Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(getApplicationContext(),about.class);
               startActivity(intent);
               return true;
           case R.id.help:
               Intent intent1 = new Intent(getApplicationContext(),help.class);
               startActivity(intent1);
               return true;
           case R.id.feedback:
               Intent intent2 = new Intent(getApplicationContext(),feedback.class);
               startActivity(intent2);
               return true;

           default:
               return false;
       }
    }

    public void next (View view)
    {
        Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
        startActivity(intent);
    }

    public void waste (View view)
    {
        Intent intent = new Intent(getApplicationContext(),waste.class);
        startActivity(intent);
    }

    /*public void back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("num",1);
        startActivity(intent);
    }*/
    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(),login.class);
        intent.putExtra("num",1);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        number  = intent.getIntExtra("num",1);

        timer = new Timer();

        //button = (Button)findViewById(R.id.button);
       // button2 = (Button)findViewById(R.id.button2);
       // button3 = (Button)findViewById(R.id.button3);
        //button4 = (Button)findViewById(R.id.button4);


        //button.animate().alpha(1).setDuration(6500);
        //button2.animate().alpha(1).setDuration(7500);
        //button3.animate().alpha(1).setDuration(8500);

        CircleMenu circleMenu = findViewById(R.id.circleMenu);
        final String[]menu = {
                "lost and found",
                "food",
                "waste report"

        };
        circleMenu.setMainMenu(Color.parseColor("#ff4500"),R.drawable.ic_add,R.drawable.ic_clear)
                .addSubMenu(Color.parseColor("#adff2f"),R.drawable.ic_action_lost)
                .addSubMenu(Color.parseColor("#ffff00"),R.drawable.ic_action_waste)
                .addSubMenu(Color.parseColor("#00bfff"),R.drawable.ic_action_food)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        if(index == 0)
                        {
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(),lost.class);
                                    startActivity(intent);
                                }
                            },950);

                        }
                        if(index == 1)
                        {
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(),waste.class);
                                    startActivity(intent);
                                }
                            },950);

                        }
                        if(index == 2)
                        {
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                                    startActivity(intent);
                                }
                            },950);

                        }



                    }
                });






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
        /*if(command.indexOf("go") != -1 || command.indexOf("next page") != -1)
        {
            if(command.indexOf("previous page") != -1)
            {
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                intent2.putExtra("num",1);
                startActivity(intent2);

            }
        }*/
        if(command.indexOf("food") != -1  || command.indexOf("select food") != -1)
        {
                Intent intent2 = new Intent(getApplicationContext(),Main3Activity.class);
                //intent2.putExtra("num",1);
                startActivity(intent2);
        }
        if(command.indexOf("lost") != -1  || command.indexOf("found") != -1)
        {
            Intent intent2 = new Intent(getApplicationContext(),Main3Activity.class);
            //intent2.putExtra("num",1);
            startActivity(intent2);
        }
        if(command.indexOf("waste report") != -1  || command.indexOf("report waste") != -1)
        {
            Intent intent2 = new Intent(getApplicationContext(),Main3Activity.class);
            //intent2.putExtra("num",1);
            startActivity(intent2);
        }
        if(command.indexOf("logout") != -1  || command.indexOf("exit") != -1)
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),login.class);
            intent.putExtra("num",1);
            startActivity(intent);
            finish();
        }

    }

    private void initializeTextToSpeech() {
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(myTTS.getEngines().size() == 0)
                {
                    Toast.makeText(Main2Activity.this, "There is no TTS engine on your phone", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if(check == 0)
                {
                    myTTS.setLanguage(Locale.US);
                    speak("To explore more please click on the plus button");

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
