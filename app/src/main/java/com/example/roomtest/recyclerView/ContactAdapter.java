package com.example.roomtest.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomtest.DB.Contact;
import com.example.roomtest.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    List<Contact> contactList;

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public ContactAdapter() {
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        TextView fName = holder.fName;
        TextView lName = holder.lName;
        TextView email = holder.email;
        TextView number = holder.number;

        fName.setText(contactList.get(position).getName());
        lName.setText(contactList.get(position).getLastName());
        email.setText(contactList.get(position).getEmail());
        number.setText(contactList.get(position).getNumber());


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView fName,lName, email, number;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            fName = itemView.findViewById(R.id.firstName);
            lName = itemView.findViewById(R.id.lastName);
            email = itemView.findViewById(R.id.email);
            number = itemView.findViewById(R.id.number);
        }
    }

}
