package com.example.proyecto_android.model;

import android.widget.AdapterView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lista fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}