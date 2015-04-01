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
        //This abstract class is the superclass of all classes representing an input stream of bytes.
        //Applications that need to define a subclass of InputStream must always provide a method that returns the next byte of input.
        InputStream sourceStream = getResources().openRawResource(R.raw.source_element);
        //An InputStreamReader is a bridge from byte streams to character streams: It reads bytes and decodes them into characters using a specified charset. The charset that it uses may be specified by name or may be given explicitly, or the platform's default charset may be accepted.
        //Each invocation of one of an InputStreamReader's read() methods may cause one or more bytes to be read from the underlying byte-input stream. To enable the efficient conversion of bytes to characters, more bytes may be read ahead from the underlying stream than are necessary to satisfy the current read operation.
        //For top efficiency, consider wrapping an InputStreamReader within a BufferedReader.

        //Reads text from a character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines.
        //The buffer size may be specified, or the default size may be used. The default is large enough for most purposes.
        //In general, each read request made of a Reader causes a corresponding read request to be made of the underlying character or byte stream. It is therefore advisable to wrap a BufferedReader around any Reader whose read() operations may be costly, such as FileReaders and InputStreamReaders.
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
