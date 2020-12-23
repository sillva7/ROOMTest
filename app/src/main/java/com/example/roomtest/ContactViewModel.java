package com.example.roomtest;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomtest.DB.Contact;
import com.example.roomtest.DB.ContactDB;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ContactViewModel extends AndroidViewModel {

    private static ContactDB contactDB;
    private static Contact tempContact;
    private LiveData<List<Contact>> contactsLiveData;


    public ContactViewModel(@NonNull Application application) {
        super(application);

        contactDB = ContactDB.getInstance(getApplication());
        contactsLiveData = contactDB.contactDAO().getAllContacts();
    }


    private static class insertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        //делаем для каждого метода из ДАО асинхронный класс, чтобы все его задачи(тяжелые для основного треда) выполнялись в бэкграунде
        @Override
        protected Void doInBackground(Contact... contacts) {

            if (contacts.length != 0 && contacts != null) {
                contactDB.contactDAO().insertContact(contacts[0]);
            }

            return null;
        }
    }

    public void insertContact(Contact contact) {
//делаем отдельный метод для этого же метода из ДАО и уже целового класса, чтобы удобнее было его использовать. мб пойму более глубже позже
        new insertContactAsyncTask().execute(contact);
    }

    private static class updateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(Contact... contacts) {
            if (contacts != null && contacts.length > 0) {
                contactDB.contactDAO().updateContact(contacts[0]);
            }
            return null;
        }
    }

    public void updateContact(Contact contact) {
        new updateContactAsyncTask().execute(contact);
    }

    private static class deleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(Contact... contacts) {
            if (contacts != null && contacts.length > 0) {
                contactDB.contactDAO().deleteContact(contacts[0]);
            }
            return null;
        }
    }

    public void deleteContact(Contact contact) {
        new deleteContactAsyncTask().execute(contact);
    }

    public LiveData<List<Contact>> getContactsLiveData() {
        return contactsLiveData;
    }

    private static class getContactByIdAsyncTask extends AsyncTask<Long, Void, Contact> {

        @Override
        protected Contact doInBackground(Long... integers) {
            if (integers != null && integers.length > 0) {
                return contactDB.contactDAO().getContactById(integers[0]);
            }
            return null;
        }


    }

    public Contact getContactById(long i) {


        try {
            return new getContactByIdAsyncTask().execute(i).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }


}
