package com.example.nagar.vconnectu;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class waste extends AppCompatActivity {
    private TextToSpeech myTTS;
    private SpeechRecognizer mySpeechRecognizer;
    private static final int PICK = 1;

    private ImageView imageView;
    private Button button;
    private Button button2;
    private Button button3;
    private EditText editText;
    private ProgressBar progressBar2;
    private Uri imageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste);

        imageView = (ImageView)findViewById(R.id.imageView);
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        editText = (EditText)findViewById(R.id.editText);
        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);


        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFile();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(waste.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }


        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImagesActivity();


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
        if(command.indexOf("go") != -1)
        {
            if(command.indexOf("back") != -1  || command.indexOf("previous page") != -1) {
                Intent intent = new Intent(getApplication(), Main2Activity.class);
                startActivity(intent);
            }
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
                    Toast.makeText(waste.this, "There is no TTS engine on your phone", Toast.LENGTH_SHORT).show();
                    finish();
                }
                /*if(check == 0)
                {
                    myTTS.setLanguage(Locale.US);
                    speak("what would u like to choose. order food: report waste. or lost and found");

                }*/

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

    public void openFile()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(imageView);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            mUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar2.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(waste.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(editText.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(waste.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar2.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void openImagesActivity()
    {
        Intent intent = new Intent(this,imagesActivity.class);
        startActivity(intent);
    }



}
