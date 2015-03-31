package com.example.TestResource;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity implements View.OnClickListener {
    private static final String LOG_TAG = "myLogs";
    private Spinner spinnerResult;
    private Button btnDbIn, btnDbOut;
    private final List<SpinnerElement> spinnerList = new ArrayList<SpinnerElement>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initUiComponents();
    }

    @Override
    public void onClick(View view){
        switch (view.getId())
        {
            case R.id.btnDbIn:
                SpinnerElement elem = (SpinnerElement)spinnerResult.getSelectedItem();
                Toast.makeText(this, String.format("%s - %s", elem.text, elem.val), Toast.LENGTH_SHORT).show();
            break;
        }
    }

    private void initUiComponents(){
        spinnerResult = (Spinner)findViewById(R.id.spinnerResult);
        btnDbIn = (Button)findViewById(R.id.btnDbIn);
        btnDbOut = (Button)findViewById(R.id.btnDbOut);
        btnDbIn.setOnClickListener(this);
        btnDbOut.setOnClickListener(this);

        readSource();
        setSpinnerDefault();
    }

    private void setSpinnerDefault(){
        ArrayAdapter<SpinnerElement> dataAdapter = new ArrayAdapter<SpinnerElement>(this,
                android.R.layout.simple_spinner_item, spinnerList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerResult.setAdapter(dataAdapter);
    }

    private void readSource(){
        InputStream sourceStream = getResources().openRawResource(R.raw.source_element);
        BufferedReader r = new BufferedReader(new InputStreamReader(sourceStream));
        String line;
        try {
            while ((line = r.readLine()) != null) {
                String[] splittedArray = line.split(";");
                spinnerList.add(new SpinnerElement(splittedArray[0], splittedArray[1]));
            }
        }
        catch (IOException ex){
           ex.getMessage();
        }
    }

    class SpinnerElement{
        SpinnerElement(String val, String text){
            this.val = val;
            this.text = text;
        }

        final String val;
        final String text;

        @Override
        public String toString(){
            return text;
        }
    }
}
