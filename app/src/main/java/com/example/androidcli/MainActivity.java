package com.example.androidcli;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    TextView androidConsole;
    EditText consoleInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAndroidConsole();
}

    void setupSendButton()
    {
        Button sendbutton = findViewById(R.id.sendbutton);
        sendbutton.setOnClickListener(v -> {
            sendCommand(androidConsole, consoleInputText);
            consoleInputText.setText("");
        });
    }

    void setAndroidConsole()
    {
        androidConsole = findViewById(R.id.AndroidConsole);
        androidConsole.setMovementMethod(new ScrollingMovementMethod());
        consoleInputText = findViewById(R.id.androidConsoleInput);
        setupSendButton();
    }

    //this method should print the logcat output to the TextView
    void sendCommand(TextView t, EditText editText)
    {
        try
        {
            String ConsoleCommand = editText.getText().toString();

            if (ConsoleCommand.equals(""))
            {
                Toast.makeText(this, "Please enter a command", Toast.LENGTH_SHORT).show();
                return;
            }

                Process process = Runtime.getRuntime().exec(ConsoleCommand);
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                StringBuilder log=new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    log.append(line+"\n");
                }
                androidConsole.append(log.toString());

        }
        catch (IOException e)
        {
            e.printStackTrace();
            t.append(e.toString());
        }
    }
}