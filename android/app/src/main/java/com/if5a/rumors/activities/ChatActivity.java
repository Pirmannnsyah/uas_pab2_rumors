package com.if5a.rumors.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.if5a.rumors.services.APIService;
import com.if5a.rumors.chats.Data;
import com.if5a.rumors.R;
import com.if5a.rumors.chats.Sender;
import com.if5a.rumors.utilities.Utility;
import com.if5a.rumors.chats.ViewData;
import com.if5a.rumors.chats.ChatMessage;
import com.if5a.rumors.chats.ChatViewHolder;
import com.if5a.rumors.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private ImageView ivBack;
    private static final String TAG = ChatActivity.class.getSimpleName();

    private String mUsername;
    private static final int REQUEST_IMAGE = 2;
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";

    private RecyclerView rvChat;
    private LinearLayoutManager mLinearLayoutManager;
    private Button btnSend;
    private EditText etMessege;
    private ImageView ivAddMessege;

    private FirebaseAuth mAuth;
    private DatabaseReference mRoot, mRef;
    private FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder> mFirebaseAdapter;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseMessaging.getInstance().subscribeToTopic("messages");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        Log.d(TAG, "onCreate: "+userId);
        mRoot = FirebaseDatabase.getInstance().getReference();
        mRef = mRoot.child("users").child(userId);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                mUsername = user.getFullName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mUsername = "Anonymus";
            }
        });

        rvChat = findViewById(R.id.rv_chat);
        etMessege = findViewById(R.id.et_message);
        ivAddMessege = findViewById(R.id.iv_addMessege);
        btnSend = findViewById(R.id.btn_send);

        ivBack = findViewById(R.id.iv_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mLinearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        mLinearLayoutManager.setStackFromEnd(true);
        rvChat.setLayoutManager(mLinearLayoutManager);

        SnapshotParser<ChatMessage> parser = new SnapshotParser<ChatMessage>() {
            @NonNull
            @Override
            public ChatMessage parseSnapshot(@NonNull DataSnapshot snapshot) {
                ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                if (chatMessage != null){
                    chatMessage.setId(snapshot.getKey());
                }
                return chatMessage;
            }
        };

        mRef = mRoot.child("messages");
        FirebaseRecyclerOptions<ChatMessage> options = new FirebaseRecyclerOptions.Builder<ChatMessage>()
                .setQuery(mRef, parser)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull ChatMessage model) {
                mRoot.child("users").child(model.getSender()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        User user = datasnapshot.getValue(User.class);

                        if(model.getSender().equals(userId)){
                            holder.llReceiver.setVisibility(View.GONE);
                            holder.llSender.setVisibility(View.VISIBLE);

                            if (model.getText()!= null){
                                holder.tvMessageSender.setText(model.getText());
                                holder.tvMessageSender.setVisibility(View.VISIBLE);
                                holder.ivMessageSender.setVisibility(View.GONE);
                            } else if (model.getImageUrl()!= null){
                                String imageUrl = model.getImageUrl();
                                if(imageUrl.startsWith("gs://")){
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                                    storageReference.getDownloadUrl().addOnCompleteListener((task) ->  {
                                            if (task.isSuccessful()) {
                                                String downloadUrl = task.getResult().toString();
                                                Glide.with(holder.ivMessageSender.getContext())
                                                        .load(downloadUrl)
                                                        .into(holder.ivMessageSender);
                                            } else {
                                                Log.w(TAG, "Getting download url failed!", task.getException());
                                            }
                                    });
                                }else {
                                    Glide.with(holder.ivMessageSender.getContext())
                                            .load(model.getImageUrl())
                                            .into(holder.ivMessageSender);
                                }
                                holder.ivMessageSender.setVisibility(View.VISIBLE);
                                holder.tvMessageSender.setVisibility(View.GONE);
                            }
                            holder.tvSender.setText(user.getFullName());

                            ColorGenerator generator = ColorGenerator.Companion.getMATERIAL();
                            TextDrawable textDrawable = TextDrawable.builder()
                                    .beginConfig()
                                    .width(36)
                                    .height(36)
                                    .endConfig()
                                    .buildRound(getInitialName(user.getFullName().toUpperCase()), generator.getColor(model.getSender()));

                            holder.ivSender.setImageDrawable(textDrawable);
                        }else{
                            holder.llReceiver.setVisibility(View.VISIBLE);
                            holder.llSender.setVisibility(View.VISIBLE);

                            if (model.getText()!= null){
                                holder.tvMessageReceiver.setText(model.getText());
                                holder.tvMessageReceiver.setVisibility(View.VISIBLE);
                                holder.ivMessageReceiver.setVisibility(View.GONE);
                            } else if (model.getImageUrl()!= null){
                                String imageUrl = model.getImageUrl();
                                if(imageUrl.startsWith("gs://")){
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()) {
                                                String downloadUrl = task.getResult().toString();
                                                Glide.with(holder.ivMessageReceiver.getContext())
                                                        .load(downloadUrl)
                                                        .into(holder.ivMessageReceiver);
                                            } else {
                                                Log.w(TAG, "Getting download url failed!", task.getException());
                                            }
                                        }
                                    });
                                }else {
                                    Glide.with(holder.ivMessageReceiver.getContext())
                                            .load(model.getImageUrl())
                                            .into(holder.ivMessageReceiver);
                                }
                                holder.ivMessageReceiver.setVisibility(View.VISIBLE);
                                holder.tvMessageReceiver.setVisibility(View.GONE);
                            }
                            holder.tvReceiver.setText(user.getFullName());

                            ColorGenerator generator = ColorGenerator.Companion.getMATERIAL();
                            TextDrawable textDrawable = TextDrawable.builder()
                                    .beginConfig()
                                    .width(36)
                                    .height(36)
                                    .endConfig()
                                    .buildRound(getInitialName(user.getFullName().toUpperCase()), generator.getColor(model.getSender()));

                            holder.ivReceiver.setImageDrawable(textDrawable);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new ChatViewHolder(inflater.inflate(R.layout.item_chat, parent, false));
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int chatMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1 ||
                        (positionStart >= (chatMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    rvChat.scrollToPosition(positionStart);
                }
            }
        });

        rvChat.setAdapter(mFirebaseAdapter);
        etMessege.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    btnSend.setEnabled(true);
                } else {
                    btnSend.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMessage chatMessage = new ChatMessage(etMessege.getText().toString(),
                        userId,
                        null);
                mRoot.child("messages").push().setValue(chatMessage);
                etMessege.setText("");

                Data data = new Data(mUsername, chatMessage.getText(), userId);
                Sender sender = new Sender(data, "/topics/messages");
                sendNotification(sender);
            }
        });

        ivAddMessege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_IMAGE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onPause() {
        mFirebaseAdapter.stopListening();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE){
            if (resultCode == RESULT_OK){
                if(data != null){
                    final Uri uri = data.getData();
                    Log.d(TAG, "Uri : " + uri.toString());

                    ChatMessage tempMessage = new ChatMessage(null, userId, LOADING_IMAGE_URL);
                    mRoot.child("messages").push()
                            .setValue(tempMessage, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if (databaseError == null){
                                        String key = databaseReference.getKey();
                                        StorageReference storageReference = FirebaseStorage.getInstance()
                                                .getReference(mAuth.getCurrentUser().getUid())
                                                .child(key)
                                                .child(uri.getLastPathSegment());

                                        putImageInStorage(storageReference, uri, key);
                                    }else {
                                        Log.w(TAG, "Unable to write database", databaseError.toException());
                                    }
                                }
                            });
                }
            }
        }

    }

    private void putImageInStorage(StorageReference storageReference, Uri uri, String key) {
        storageReference.putFile(uri).addOnCompleteListener(ChatActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().getMetadata().getReference().getDownloadUrl()
                            .addOnCompleteListener(ChatActivity.this, new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        ChatMessage chatMessage = new ChatMessage(null, userId, task.getResult().toString());
                                        mRoot.child("messages").child(key).setValue(chatMessage);

                                        Data data = new Data(mUsername, "Image Message", userId, task.getResult().toString());
                                        Sender sender = new Sender(data, "/topics/messages");
                                        sendNotification(sender);
                                    }
                                }
                            });
                } else {
                    Log.w(TAG, "Image upload task failed!", task.getException());
                }
            }
        });

    }


    private String getInitialName(String fullName) {
        String splitName[] = fullName.split("\\s+");
        int splitCount = splitName.length;

        if (splitCount == 1) {
            return "" + fullName.charAt(0) + fullName.charAt(0);
        } else {
            int firstSpace = fullName.indexOf(" ");
            String firstName = fullName.substring(0, firstSpace);

            int lastSpace = fullName.lastIndexOf(" ");
            String lastName = fullName.substring(lastSpace + 1);

            return "" + firstName.charAt(0) + lastName.charAt(0);
        }
    }

    private void sendNotification(Sender sender) {
        APIService api = Utility.getmRetrofit().create(APIService.class);
        Call<ViewData> call = api.sendNotification(sender);
        call.enqueue(new Callback<ViewData>() {
            @Override
            public void onResponse(Call<ViewData> call, Response<ViewData> response) {
                if (response.code() == 200) {
                    System.out.println("Response : " + response.body().getMessage_id());
                    if (response.body().getMessage_id() != null) {
//                        Toast.makeText(MainActivity.this, "Pesan berhasil dikirim!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChatActivity.this, "Pesan gagal dikirim!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChatActivity.this, "Response " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewData> call, Throwable t) {
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(ChatActivity.this, "Retrofit Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}