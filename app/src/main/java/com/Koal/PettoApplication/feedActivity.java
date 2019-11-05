package com.Koal.PettoApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

public class feedActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    ArrayList<String> usertitlefromFB;
    ArrayList<String> userimagefromFB;
    ArrayList<String> userdescfromFB;
    ArrayList<String> useremailfromFB;
    feedRecyclerAdapter feedRecyclerAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_post,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_post) {
            Intent intent = new Intent(getApplicationContext(), uploadActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.sign_out) {
            firebaseAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        usertitlefromFB = new ArrayList<>();
        useremailfromFB = new ArrayList<>();
        userimagefromFB = new ArrayList<>();
        userdescfromFB = new ArrayList<>();
        getDataFromFirestore();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecyclerAdapter = new feedRecyclerAdapter(userdescfromFB,userimagefromFB,usertitlefromFB);
        recyclerView.setAdapter(feedRecyclerAdapter);
    }
    public void getDataFromFirestore(){
        CollectionReference collectionReference =  firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null) {
                    Toast.makeText(feedActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
                if(queryDocumentSnapshots!=null){
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                        Map<String,Object> data = snapshot.getData();
                        String title = (String) data.get("title");
                        String useremail = (String) data.get("useremail");
                        String description = (String) data.get("description");
                        String downloadurl = (String) data.get("downloadurl");
                        usertitlefromFB.add(title);
                        useremailfromFB.add(useremail);
                        userdescfromFB.add(description);
                        userimagefromFB.add(downloadurl);
                        feedRecyclerAdapter.notifyDataSetChanged();
                    }
                }


            }
        });
    }
}
