package com.example.bsk69.spystreamgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsk69.spystreamgui.Adapter.MsgAdapter;
import com.firebase.ui.auth.AuthUI;
import com.bumptech.glide.Glide;
import com.example.bsk69.spystreamgui.Model.User;
import com.example.bsk69.spystreamgui.Model.Chat;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


    /*
        CircleImageView profile_image;
        TextView username;

        List<Chat> mchat;
        FirebaseUser fuser;
        DatabaseReference reference;
        RecyclerView recyclerView;
        MsgAdapter msgAdapter;
        FloatingActionButton fab;
        EditText text_send;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            profile_image = findViewById(R.id.profile_image);

            text_send = (EditText) findViewById(R.id.input);
            fab = (FloatingActionButton) findViewById(R.id.fab);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final String userid = getIntent().getStringExtra("userid");

            fuser = FirebaseAuth.getInstance().getCurrentUser();


            readMsg(fuser.getUid(),userid);
            reference = FirebaseDatabase.getInstance().getReference("UsersPlatform").child(userid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);


                    readMsg(fuser.getUid(),userid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = text_send.getText().toString();
                    if (!msg.equals("")){
                        sendMsg(FirebaseAuth.getInstance().getCurrentUser().getEmail(),msg);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Nie możesz wysłać pustej wiadomości",Toast.LENGTH_SHORT).show();

                    }
                    text_send.setText("");
                }
            });
        }

        private void sendMsg(String sender, String message){

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("sender", sender);
            hashMap.put("message", message);

            reference.child("Chats").push().setValue(hashMap);
        }

        private void readMsg(final String myid, final String userid){
            mchat = new ArrayList<>();

            reference = FirebaseDatabase.getInstance().getReference("Chats");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mchat.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Chat chat = snapshot.getValue(Chat.class);
                        mchat.add(chat);
                        msgAdapter = new MsgAdapter(ActivityChat.this,mchat);
                        recyclerView.setAdapter(msgAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    */

public class ActivityChat extends AppCompatActivity {
    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMsg> adapter;
    Query query = FirebaseDatabase.getInstance().getReference().child("Chats").limitToLast(50);

    FirebaseListOptions<ChatMsg> options = new FirebaseListOptions.Builder<ChatMsg>()
            .setQuery(query, ChatMsg.class)
            .setLayout(R.layout.list_item)
            .build();
    EditText input;
    RelativeLayout chat;
    FloatingActionButton fab;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Pomyślnie zalogowany!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Nie można zalogować się. Spróbuj później.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        input = (EditText) findViewById(R.id.input);
        chat = (RelativeLayout) findViewById(R.id.activity_chat);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Chats").push().setValue(new ChatMsg(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");
                input.requestFocus();
                displayChatMessage();
            }

        });


        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        } else {
            Toast.makeText(getApplicationContext(), "Witaj: " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        }


    }

    private void displayChatMessage() {
        ListView listMessage = (ListView) findViewById(R.id.list_of_message);
        adapter = new FirebaseListAdapter<ChatMsg>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull ChatMsg model, int position) {
                TextView messageText, messageUser, messageTime;
                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
            }
        };

        listMessage.setAdapter(adapter);
        adapter.startListening();
    }

   /* @Override
    protected void onStart() {
        super.onStart();
    }*/


    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}

        /*
            FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<ChatMsg, ChatHolder>(options)
            {
                @NonNull
                @Override
                public ChatHolder onCreateViewHolder(ViewGroup viewGroup, int parent) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.activity_chat, parent, false);

                    return new ChatHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull ChatHolder holder, int position, @NonNull ChatMsg model) {

                }

                protected void populateView(View v,ChatMsg model,int position){
                    TextView messageText,messageUser,messageTime;
                    messageText = (TextView)v.findViewById(R.id.message_text);
                    messageUser = (TextView)v.findViewById(R.id.message_user);
                    messageTime = (TextView)v.findViewById(R.id.message_time);

                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));

                }


            };



        }
        public class ChatHolder {

            private String messageText, messageUser;
            private long messageTime;

            public ChatHolder(@NonNull View v) {
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
            }
        }

        @Override
        protected void onStart() {
            super.onStart();
            adapter.startListening();
        }

        @Override
        protected void onStop() {
            super.onStop();
            adapter.stopListening();
        }*/
        /* public ChatHolder onCreateViewHolder (ViewGroup parent, int viewType){
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
                    return new ChatHolder(view);
                }

                protected void  onBindViewHolder(ChatHolder holder,int position,ChatMsg model){

                }*/



