package com.pucpr.agenda.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class DataModel {
    private static DataModel instance = new DataModel();
    public UserDetails userDetails = new UserDetails("luke","1234");
    private DataModel(){

    }
    public static DataModel getInstance(){
        return instance;
    }
    private ArrayList<Contact> contact;
    private ContactDatabase database;

    public void createDatabase(Context context){
        database = new ContactDatabase(context);

    }

    public ArrayList<Contact> getContact(){
        return contact;
    }
    public Contact getContact(int pos){
        return contact.get(pos);
    }

    public int getContactSize(){
        if (contact != null) {
            return contact.size();
        } else {
            return 0;
        }
    }

    public boolean addContact(Contact c){
        long id = database.createContactInDB(c);
        if (id > 0){
            c.setId(id);
            contact.add(c);
            return true;
        }
        return false;
    }

    public boolean insertContact(Contact c, int pos){
        long id = database.createContactInDB(c);
        if (id > 0){
            contact.add(pos,c);
            return true;
        }
        return false;
    }
    public boolean updateContact(Contact c, int pos){
        int count = database.updateContactInDB(c);
        if (count == 1){
            contact.set(pos,c);
            return true;
        }
        return false;
    }

    public boolean removeContact(int pos){
        int count = database.removeContactInDB(
                getContact(pos)
        );
        if (count == 1){
            contact.remove(pos);
            return true;
        }
        return false;
    }
}
