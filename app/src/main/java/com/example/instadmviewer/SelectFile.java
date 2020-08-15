package com.example.instadmviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectFile extends AppCompatActivity {

    // Request code for selecting the messages file
    private static final int PICK_MESSAGE_FILE = 2;

    private ArrayList<Conversation> conversations;

    // Strings used in the Instagram JSON file
    private static final String PARTICIPANTS = "participants";
    private static final String CONVERSATION = "conversation";
    private static final String SENDER = "sender";
    private static final String CREATED_AT = "created_at";
    private static final String TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);
    }

    public void openFile(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
       // intent.setType("application/json"); // TODO: couldn't make this line work :(
        intent.setType("*/*");

        startActivityForResult(intent, PICK_MESSAGE_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == PICK_MESSAGE_FILE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                String messageFile = null;
                try {
                    messageFile = readMessageFile(uri);
                } catch (IOException e) {
                    // TODO: display error
                    e.printStackTrace();
                }
                parseJsonFile(messageFile);
            }
        }
    }

    private String readMessageFile(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        String string = stringBuilder.toString();
        if (string.isEmpty()) {
            throw new IOException("Could not retrieve any messages");
        }
        return string;
    }

    private void parseJsonFile(String messageFile) {
        try {
            List<String> participantList = new ArrayList<>();
            JSONArray allConversations = new JSONArray(messageFile); // conversations array
            for (int i = 0; i < allConversations.length(); i++) {
                JSONObject singleConversation = allConversations.getJSONObject(i);
                JSONArray participants = singleConversation.getJSONArray(PARTICIPANTS);
                for (int j = 0; j < participants.length(); j++) {
                    participantList.add((String) participants.get(j));
                }
                // TODO: from participant list, get the most frequent participant to infer which
                // is the user's username
                // TODO: *****************************
            }
        } catch (JSONException e) {
            // TODO: display error
        }
    }
}