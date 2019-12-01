package com.example.sqlapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurName, editMarks, editId;
    Button btnAddData , btnGetData , btnUpdateData, btnDeleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editId = (EditText) findViewById(R.id.editText_Id);
        editName = (EditText) findViewById(R.id.editText_Name);
        editSurName = (EditText) findViewById(R.id.editText_SurName);
        editMarks = (EditText) findViewById(R.id.editText_Mark);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnGetData = (Button) findViewById(R.id.button_getAll);
        btnUpdateData = (Button) findViewById(R.id.button_update);
        btnDeleteData = (Button) findViewById(R.id.button_delete);
        AddData();
        ViewAll();
        UpdateData();
        DeleteData();

    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      boolean isInserted =   myDb.insertData(editName.getText().toString(),editSurName.getText().toString(),editMarks.getText().toString());
                      if (isInserted)
                          Toast.makeText(MainActivity.this,"Data Inserted", Toast.LENGTH_LONG).show();
                      else
                          Toast.makeText(MainActivity.this,"Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void ViewAll(){
        btnGetData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            //show message
                            showMessage("Error","No Data Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :" +res.getString(0)+ "\n");
                            buffer.append("Name :" +res.getString(1)+ "\n");
                            buffer.append("Surname :" +res.getString(2)+ "\n");
                            buffer.append("Marks :" +res.getString(3)+ "\n");
                        }
                        //Show all data

                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void UpdateData(){
        btnUpdateData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateData(editId.getText().toString(),editName.getText().toString(),editSurName.getText().toString(),editMarks.getText().toString());
                        if (isUpdated)
                            Toast.makeText(MainActivity.this,"Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void DeleteData(){
        btnDeleteData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isDeleted = myDb.deleteData(editId.getText().toString());
                        if (isDeleted)
                            Toast.makeText(MainActivity.this,"Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
