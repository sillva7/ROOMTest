package com.example.roomtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.roomtest.DB.Contact;
import com.example.roomtest.DB.ContactDAO;
import com.example.roomtest.DB.ContactDB;
import com.example.roomtest.recyclerView.ContactAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactViewModel viewModel;
    private ContactAdapter adapter;
    RecyclerView recyclerView;
    private FloatingActionButton fab;
    private final int ADD = 0;
    private final int EDIT = 1;
    private final static String TAG = "454545";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.addEditContactButton);
        adapter = new ContactAdapter();
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getContactsLiveData().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setContactList(contacts);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditContact(0, null);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);


        adapter.setOnContactClickListener(new ContactAdapter.OnContactClickListener() {
            @Override
            public void OnContactClick(int position) {
                addEditContact(1, adapter.getContactList().get(position));

            }
        });
        Log.d(TAG, "onCreate: " + viewModel.getContactById(1));//test

        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Contact contact = adapter.getContactList().get(viewHolder.getAdapterPosition());
                adapter.getContactList().remove(viewHolder.getAdapterPosition());
                viewModel.deleteContact(contact);

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    private void addEditContact(int fun, Contact contact) {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.add_edit_contact, null);

        EditText fName = view.findViewById(R.id.addEditFName);
        EditText lName = view.findViewById(R.id.addEditLName);
        EditText email = view.findViewById(R.id.addEditEmail);
        EditText number = view.findViewById(R.id.addEditNumber);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (fun == 0) {
            builder.setTitle("Add contact to base")
                    .setView(view)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Contact contact = new Contact(fName.getText().toString(), lName.getText().toString(), email.getText().toString(), number.getText().toString());//0 - это значит будто мы не сетнули просто id, н autogenerat'ится
                            viewModel.insertContact(contact);
                        }
                    });
        } else if (fun == 1) {
            fName.setText(contact.getName());
            lName.setText(contact.getLastName());
            email.setText(contact.getEmail());
            number.setText(contact.getNumber());
            builder.setTitle("Edit selected contact")
                    .setView(view)
                    .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            contact.setName(fName.getText().toString());
                            contact.setLastName(lName.getText().toString());
                            contact.setEmail(email.getText().toString());
                            contact.setNumber(number.getText().toString());

                            viewModel.updateContact(contact);
                        }
                    });
        }

        Dialog dialog = builder.create();
        dialog.show();

    }


}