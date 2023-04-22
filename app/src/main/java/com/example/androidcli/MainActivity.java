package com.example.androidcli;

import androidx.annotation.NonNull;
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
    void sendCommand(TextView console, EditText consoleInput)
    {
        try
        {
            String consoleCommand = consoleInput.getText().toString();

            if (consoleCommand.equals(""))
            {
                Toast.makeText(this, "Please enter a command", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder commandOutput = runAndGetOuput(consoleCommand);
            androidConsole.append(commandOutput.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            console.append(e.toString());
        }
    }

    @NonNull
    private StringBuilder runAndGetOuput(String consoleCommand) throws IOException
    {
        Process process = runCommandAndGetTheProcess(consoleCommand);
        BufferedReader bufferedReader = getProcessOutputStream(process);
        StringBuilder resultToDisplayOnConsole=new StringBuilder();
        readLinesToStringBuilder(bufferedReader, resultToDisplayOnConsole);
        return resultToDisplayOnConsole;
    }

    @NonNull
    private BufferedReader getProcessOutputStream(Process process) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return bufferedReader;
    }

    private Process runCommandAndGetTheProcess(String consoleCommand) throws IOException {
        Process process = Runtime.getRuntime().exec(consoleCommand);
        return process;
    }

    private void readLinesToStringBuilder(BufferedReader bufferedReader, StringBuilder log) throws IOException
    {
        String line = "";
        while ((line = bufferedReader.readLine()) != null)
            log.append(line+"\n");
    }
}