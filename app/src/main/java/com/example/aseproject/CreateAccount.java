package com.example.aseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aseproject.spinner.DateClass;
import com.example.aseproject.spinner.Gender;
import com.example.aseproject.spinner.GenericSpinnerAdapter;
import com.example.aseproject.spinner.Month;
import com.example.aseproject.spinner.Year;
import com.example.aseproject.utility.DateAndTimeConversions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    GenericSpinnerAdapter genderAdapter;
    GenericSpinnerAdapter dateAdapter;
    GenericSpinnerAdapter monthAdapter;
    GenericSpinnerAdapter yearAdapter;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore mFirestore;
    private String mCurrentId, mUserId;

    private TextView firstNameField;
    private TextView lastNameField;
    private Spinner dateSpinner;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private TextView emailField;
    private Spinner genderSpinner;
    private TextView contactNumberField;
    private TextView passwordField;
    private TextView confirmPasswordField;

    private TextView firstNameFieldError;
    private TextView lastNameFieldError;
    private TextView dateFieldError;
    private TextView monthFieldError;
    private TextView yearFieldError;
    private TextView emailFieldError;
    private TextView genderFieldError;
    private TextView contactNumberFieldError;
    private TextView passwordFieldError;
    private TextView confirmPasswordFieldError;

    Pattern myPattern;
    CharSequence myEmailCharSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ininUI();
        initObjects();
        bindingData();
        initListeners();
    }
    private void ininUI() {
        firstNameField = (TextView) findViewById(R.id.firstNameField);
        lastNameField = (TextView) findViewById(R.id.lastNameField);
        dateSpinner = (Spinner) findViewById(R.id.dateSpinner);
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        emailField = (TextView) findViewById(R.id.userNameField);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        contactNumberField = (TextView) findViewById(R.id.contactNumberField);
        passwordField = (TextView) findViewById(R.id.passwordField);
        confirmPasswordField = (TextView) findViewById(R.id.confirmPasswordField);

        firstNameFieldError = (TextView) findViewById(R.id.firstNameFieldError);
        lastNameFieldError = (TextView) findViewById(R.id.lastNameFieldError);
        dateFieldError = (TextView) findViewById(R.id.dateFieldError);
        monthFieldError = (TextView) findViewById(R.id.monthFieldError);
        yearFieldError = (TextView) findViewById(R.id.yearFieldError);
        emailFieldError = (TextView) findViewById(R.id.emailFieldError);
        genderFieldError = (TextView) findViewById(R.id.genderFieldError);
        contactNumberFieldError = (TextView) findViewById(R.id.contactNumberFieldError);
        passwordFieldError = (TextView) findViewById(R.id.passwordFieldError);
        confirmPasswordFieldError = (TextView) findViewById(R.id.contactNumberFieldError);

    }

    private void initObjects() {
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myPattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");
    }

    private void bindingData() {
        genderAdapter = new GenericSpinnerAdapter(new Gender().getGenderList(), this);
        genderSpinner.setAdapter(genderAdapter);

        dateAdapter = new GenericSpinnerAdapter(new DateClass().getMyDateList(), this);
        dateSpinner.setAdapter(dateAdapter);

        monthAdapter = new GenericSpinnerAdapter(new Month().getMyMonthList(), this);
        monthSpinner.setAdapter(monthAdapter);

        yearAdapter = new GenericSpinnerAdapter(new Year().getMyYearList(), this);
        yearSpinner.setAdapter(yearAdapter);
    }

    private void initListeners() {
        firstNameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused)
                {
                    if (firstNameField.getText().toString().equals(""))
                    {
                        firstNameFieldError.setText("Please enter first name");
                        firstNameFieldError.setVisibility(View.VISIBLE);
                    }
                }
                if (focused)
                {
                    firstNameFieldError.setVisibility(View.INVISIBLE);
                }
            }
        });

        lastNameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused)
                {
                    if (lastNameField.getText().toString().equals(""))
                    {
                        lastNameFieldError.setText("Please enter last name");
                        lastNameFieldError.setVisibility(View.VISIBLE);
                    }
                }
                if (focused)
                {
                    lastNameFieldError.setVisibility(View.INVISIBLE);
                }
            }
        });

        emailField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused)
                {
                    myEmailCharSequence = emailField.getText().toString();

                    if (emailField.getText().toString().equals("")) {
                        emailFieldError.setText("Please enter email");
                        emailFieldError.setVisibility(View.VISIBLE);
                    }

                    else if (!myPattern.matcher(myEmailCharSequence).matches())
                    {
                        emailFieldError.setText("Email not valid");
                        emailFieldError.setVisibility(View.VISIBLE);
                    }
                }
                if (focused)
                {
                    emailFieldError.setVisibility(View.INVISIBLE);
                }
            }
        });

        contactNumberField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused)
                {
                    if (contactNumberField.getText().toString().equals(""))
                    {
                        contactNumberFieldError.setText("Please enter contact");
                        contactNumberFieldError.setVisibility(View.VISIBLE);
                    }

                    if (contactNumberField.getText().toString().length() < 9 || contactNumberField.getText().toString().length() > 14)
                    {
                        contactNumberFieldError.setText("Invalid Contact");
                        contactNumberFieldError.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        contactNumberFieldError.setText("");
                        contactNumberFieldError.setVisibility(View.INVISIBLE);
                    }
                }
                if (focused)
                {
                    emailFieldError.setVisibility(View.INVISIBLE);
                }
            }
        });

        passwordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused)
                {
                    if (passwordField.getText().toString().equals(""))
                    {
                        passwordFieldError.setText("Please enter password");
                        passwordFieldError.setVisibility(View.VISIBLE);
                    }
                }
                if (focused)
                {
                    passwordFieldError.setVisibility(View.INVISIBLE);
                }
            }
        });

        confirmPasswordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused)
                {
                    if (confirmPasswordField.getText().toString().equals(""))
                    {
                        confirmPasswordFieldError.setText("Please enter confirm password");
                        confirmPasswordFieldError.setVisibility(View.VISIBLE);
                    }
                }
                if (focused)
                {
                    confirmPasswordFieldError.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void createAccount(View view) {
        createUserAccount();
    }

    private boolean createUserAccount()
    {
        clearAllErrorFields();
        try {

            final String firstName = firstNameField.getText().toString();
            final String lastName = lastNameField.getText().toString();

            DateClass dateClassObject = (DateClass) dateAdapter.getItem(dateSpinner.getSelectedItemPosition());
            Month monthObject = (Month) monthAdapter.getItem(monthSpinner.getSelectedItemPosition());
            Year yearObject = (Year) yearAdapter.getItem(yearSpinner.getSelectedItemPosition());

            final String date = dateClassObject.getDate();
            final String month = monthObject.getMonth();
            final String year = yearObject.getYear();

            final String email = emailField.getText().toString();

            Gender genderObject = (Gender) genderAdapter.getItem(genderSpinner.getSelectedItemPosition());
            final String gender = genderObject.getGender();

            final String contactNumber = contactNumberField.getText().toString();
            final String password = passwordField.getText().toString();
            final String confirmPassword = confirmPasswordField.getText().toString();

            boolean dataFlagIncomplete = false;

            if (firstNameField.getText().toString().equals("") || lastNameField.getText().toString().equals("") || genderSpinner.getSelectedItemPosition() == 0 || dateSpinner.getSelectedItemPosition() == 0 || monthSpinner.getSelectedItemPosition() == 0 || yearSpinner.getSelectedItemPosition() == 0 || emailField.getText().toString().equals("") || contactNumberField.getText().toString().equals("") || passwordField.getText().toString().equals("") || confirmPasswordField.getText().toString().equals("")) {
                dataFlagIncomplete = true;
            }

            if (firstNameField.getText().toString().equals("")) {
                firstNameFieldError.setText("Please enter first name");
                firstNameFieldError.setVisibility(View.VISIBLE);
            }

            if (lastNameField.getText().toString().equals("")) {
                lastNameFieldError.setText("Please enter last name");
                lastNameFieldError.setVisibility(View.VISIBLE);
            }

            myEmailCharSequence = emailField.getText().toString();

            if (emailField.getText().toString().equals("")) {
                emailFieldError.setText("Please enter email");
                emailFieldError.setVisibility(View.VISIBLE);
            }

            else if (!myPattern.matcher(myEmailCharSequence).matches())
            {
                emailFieldError.setText("Email not valid");
                emailFieldError.setVisibility(View.VISIBLE);
            }

            if (contactNumberField.getText().toString().equals("")) {
                contactNumberFieldError.setText("Please enter contact");
                contactNumberFieldError.setVisibility(View.VISIBLE);
            }

            if (passwordField.getText().toString().equals("")) {
                passwordFieldError.setText("Please enter last name");
                passwordFieldError.setVisibility(View.VISIBLE);
            }

            if (confirmPasswordField.getText().toString().equals("")) {
                confirmPasswordFieldError.setText("Please enter last name");
                confirmPasswordFieldError.setVisibility(View.VISIBLE);
            }

            if (genderSpinner.getSelectedItemPosition() == 0) {
                genderFieldError.setText("Please Select Gender");
                genderFieldError.setVisibility(View.VISIBLE);
            }

            if (dateSpinner.getSelectedItemPosition() == 0) {
                dateFieldError.setText("Please Select Date");
                dateFieldError.setVisibility(View.VISIBLE);
            }

            if (monthSpinner.getSelectedItemPosition() == 0) {
                monthFieldError.setText("Please select month");
                monthFieldError.setVisibility(View.VISIBLE);
            }

            if (yearSpinner.getSelectedItemPosition() == 0) {
                yearFieldError.setText("Please select year");
                yearFieldError.setVisibility(View.VISIBLE);
            }

            if (!password.equals(confirmPassword))
            {
                showMessage("Confirm password does not match", false);
            }



            String currentDate = DateAndTimeConversions.getCurrentDate();
            String selectedDate = date + "/" + month + "/" + year;

            String dateResult = DateAndTimeConversions.compareDateAndTime(selectedDate, "00:00", currentDate, "00:00");

            if (dateResult.equals("equals") || dateResult.equals("after")) {
                dateFieldError.setText("Birthday should be in past");
                monthFieldError.setText("");
                yearFieldError.setText("");
                return false;
            }

            if (dataFlagIncomplete == true) {
                dataFlagIncomplete = false;
                showMessage("Enter All Details", false);
//                Toast.makeText(CreateAccount.this, "Enter All Details", Toast.LENGTH_LONG).show();
                return false;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(CreateAccount.this, "Account Created", Toast.LENGTH_LONG).show();
                    String user_id = mAuth.getCurrentUser().getUid().toString();

                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("first_name", firstName);
                    userMap.put("last_name", lastName);
                    userMap.put("date", date);
                    userMap.put("month", month);
                    userMap.put("year", year);
                    userMap.put("uid", user_id);
                    userMap.put("gender", gender);
                    userMap.put("contact_number", contactNumber);
                    userMap.put("password", password);

                    mFirestore.collection("Users").document(email).set(userMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Record", "Added");
                                    Log.d("Record", email);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Record", "Not Added");
                                }
                            });

                    //       Toast.makeText(CreateAccount.this, "user was created", Toast.LENGTH_SHORT).show();
                    try {
                        mAuth.signOut();
                    }
                    catch (Exception err){

                    }
                    showMessage("SignUp Successful", true);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateAccount.this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception err)
        {
            return false;
        }

        return false;
    }

    public void showMessage(String message, final boolean flag)
    {
        AlertDialog.Builder adb=new AlertDialog.Builder(CreateAccount.this);
        adb.setTitle("");
        adb.setMessage(message);

        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (flag==true)
                {
                    startActivity(new Intent(CreateAccount.this, MainActivity.class));
                    finish();
                }
            }});
        adb.show();
    }

    public void clearAllErrorFields()
    {
        firstNameFieldError.setText("");
        lastNameFieldError.setText("");
        dateFieldError.setText("");
        monthFieldError.setText("");
        yearFieldError.setText("");
        emailFieldError.setText("");
        genderFieldError.setText("");
        contactNumberFieldError.setText("");
        passwordFieldError.setText("");
        confirmPasswordFieldError.setText("");
    }
}
