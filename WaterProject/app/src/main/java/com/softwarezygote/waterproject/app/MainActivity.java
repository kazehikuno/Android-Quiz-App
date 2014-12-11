package com.softwarezygote.waterproject.app;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    // has the same methods as ActionBarActivity, they can be overridden if needed

    // Variables
    ScrollView s;                       // ScrollView to control the scroll
    ScrollView s2;
    TextView resultString;              // correct or incorrect
    TextView ques;                      // the question field
    TextView a;                         // answer a field
    TextView b;                         // answer b field
    TextView c;                         // answer c field
    TextView d;                         // answer d field
    RadioGroup radioGrp;                // radio button field
    boolean result = false;             // was the guess correct?
    int answer = 0;                     // the position of the correct answer (0-3)
    Button mainButton;                  // submit answer button handler
    Button nextButton;                  // next question button handler
    int dbId = 0;                       // current database row
    long dbLength;                      // length of the database
    DatabaseHelper db;                  // database object
    Question currentQuestion;           // question object

    // onCreate
    @Override  // override the extended onCreate
    protected void onCreate(Bundle savedInstanceState) {        // first thing the app does
        super.onCreate(savedInstanceState);                     // calls the parent onCreate as well as the new one

        setContentView(R.layout.activity_main);                 // set the layout according to the xml

        dbId = (int )(Math.random() * (dbLength-1));

        currentQuestion = new Question();                       // finish creating the question object
        db = new DatabaseHelper(this);                          // finish creating the database object, send this context

        // set the radio button group
        radioGrp = (RadioGroup)findViewById(R.id.radios);       // find by xml id

        // database create and open
        db.createDatabase();                                    // call create method on database object
        db.openDatabase();                                      // call open method

        // find the length of the database
        dbLength = db.count();                                  // call count method

        // set the current question
        currentQuestion = update(dbId, db);                     // call update method to populate currentQuestion

        // set onClick listeners
        mainButton = (Button) findViewById(R.id.main_button);   // find id from xml
        mainButton.setOnClickListener(this);                    // listen for a button press in this context
        nextButton = (Button) findViewById(R.id.next_button);   // find id from xml
        nextButton.setOnClickListener(this);                    // listen for a button press in this context

        // set the textViews
        ques = (TextView) findViewById(R.id.questions);         // reference xml
        a = (TextView) findViewById(R.id.radio_A);
        b = (TextView) findViewById(R.id.radio_B);
        c = (TextView) findViewById(R.id.radio_C);
        d = (TextView) findViewById(R.id.radio_D);
        s = (ScrollView) findViewById(R.id.scroll);
        s2 = (ScrollView) findViewById(R.id.scroll2);

        // display the text
        display();                  // call display method

    // end of onCreate
    }



    // updates the current question
    public Question update(int id, DatabaseHelper d){  // two parameters
        Question q;  // temporary Question variable
        q = d.getNext(id);  // call getNext from DatabaseHelper class
        return q;  // return is in the form of a Question variable
    }

    // displays the current question
    public void display(){  // no parameters
        ques.setText(Html.fromHtml(currentQuestion.getQues()));  // populate the textFields, display automatically
        answer = currentQuestion.getAns();
        a.setText(Html.fromHtml(currentQuestion.getA()));
        b.setText(Html.fromHtml(currentQuestion.getB()));
        c.setText(Html.fromHtml(currentQuestion.getC()));
        d.setText(Html.fromHtml(currentQuestion.getD()));
        radioGrp.clearCheck();  // clear the radio button selection from any previous question
    }

    // onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // onRadioButtonClicked
    public void onRadioButtonClicked(View view) {  // send a view as a parameter, the view is the group of radio buttons
        // are any of them checked?
        boolean checked = ((RadioButton) view).isChecked();  // call the isChecked method, the view means this specific group of them

        // check which radio button was clicked
        switch(view.getId()) {  // switch statement
            case R.id.radio_A:  // first option was selected
                if (answer == 0)  // if the currentQuestion has the answer located in position 0
                    result = true; // set result flag for output
                else
                    result = false;
                    break;             // etc...
            case R.id.radio_B:
                if (answer == 1)
                    result = true;
                else
                    result = false;
                    break;
            case R.id.radio_C:
                if (answer == 2)
                    result = true;
                else
                    result = false;
                    break;
            case R.id.radio_D:
                if (answer == 3)
                    result = true;
                else
                    result = false;
                    break;
            default:  // used when none of these options are true
                result = false;
                break;
        }
    }

    // onClick
    @Override
    public void onClick(View view) {  // uses a view as a parameter
            resultString = (TextView) findViewById(R.id.display_result);  // reference xml
        switch(view.getId()) {  // switch to see which button it was
            case R.id.main_button:  // main case
                if (result) {  // if boolean result is true
                    resultString.setText("CORRECT!");  // set the text
                    resultString.setBackgroundColor(0xFF66FF99);
                }
                else {
                    resultString.setText("INCORRECT.");
                    resultString.setBackgroundColor(0xFFFF8484);
                }
                break;
            case R.id.next_button:  // next question case, also resets results

                dbId = (int )(Math.random() * (dbLength-1));
                //dbId += 1;  // increment the database row counter

                if(dbId >= dbLength){  // if the row counter exceeds the size of the database
                    dbId = 0;  // reset the row counter
                }
                currentQuestion = update(dbId, db);  // grab a new question from the database
                display();  // display the new question
                s.smoothScrollTo(0,0);  // scroll back to the top
                s2.smoothScrollTo(0,0);  // scroll back to the top
                resultString.setText("");  // clear the result field
                resultString.setBackgroundColor(0xFFF3F3F3);
                result = false;  // clear previous answer
                break;
        }
    }

}
