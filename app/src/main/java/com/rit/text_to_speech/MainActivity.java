package com.rit.text_to_speech;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextToSpeech tts;
    private EditText editText;
    private Button btnSumit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        editText=findViewById( R.id.editText );
        btnSumit=findViewById( R.id.button);
        btnSumit.setOnClickListener( this );

        tts=new TextToSpeech( this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    int result=tts.setLanguage( Locale.getDefault());
                    if(result==TextToSpeech.LANG_MISSING_DATA){
                        Toast.makeText( MainActivity.this,"Language Not Supported",Toast.LENGTH_SHORT ).show();

                    }else {
                        btnSumit.setEnabled( true );
                        voiceOutput();
                    }
                }else{
                    Toast.makeText( MainActivity.this,"Failed",Toast.LENGTH_SHORT ).show();

                }

            }
        } );

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.button){
            voiceOutput();

        }

    }

    private void voiceOutput() {
        CharSequence text=editText.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText( MainActivity.this,text,Toast.LENGTH_SHORT).show();
            tts.speak( text,TextToSpeech.QUEUE_FLUSH,null,"id" );
        }

    }

    @Override
    protected void onDestroy() {
       if(tts !=null){
           tts.stop();
           tts.shutdown();
       }
        super.onDestroy();
    }
}
