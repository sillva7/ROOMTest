package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.roomtest.DB.Contact;
import com.example.roomtest.DB.ContactDB;
import com.example.roomtest.recyclerView.ContactAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ContactViewModel viewModel;
    ContactAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ContactAdapter();
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getContactsLiveData().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setContactList(contacts);
            }
        });


    }
}