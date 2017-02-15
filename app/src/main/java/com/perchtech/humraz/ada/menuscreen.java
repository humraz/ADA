package com.perchtech.humraz.ada;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;


public class menuscreen extends ActionBarActivity {
    static int[] imageResources = new int[]{
            R.drawable.aaa,
            R.drawable.aaa,
            R.drawable.aaa,
            R.drawable.aaa,
            R.drawable.aaa


    };
    static int[] Strings = new int[]{
            R.string.hi,
            R.string.hi,
            R.string.hi,
            R.string.hi,
            R.string.hi



    };
    static int imageResourceIndex = 0;
    static int str = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuscreen);
        bmb();
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://adaa-45b17.firebaseio.com/PreviousLocation/");

        //Getting values to store

        //Creating Person object
        location person = new location();

        //Adding values
        person.setLat("10.00112");
        person.setLng("74.2324");
        //Storing values to firebase
        ref.push().setValue(person);
    }





    public void bmb()
    {
        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View actionBar = mInflater.inflate(R.layout.custombar, null);
        TextView mTitleTextView = (TextView) actionBar.findViewById(R.id.title_text);
        mTitleTextView.setText(R.string.app_name);
        mActionBar.setCustomView(actionBar);
        mActionBar.setDisplayShowCustomEnabled(true);
        ((Toolbar) actionBar.getParent()).setContentInsetsAbsolute(0,0);

        BoomMenuButton bmb = (BoomMenuButton) actionBar.findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalTextRes(getString())
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {


                            if (index == 0) {
                                start(index);

                            }
                            if (index == 1) {
                                start(index);
                            }
                            if (index == 2) {
                                start(index);

                            }
                            if (index == 3) {
                                start(index);
                            }
                            if (index == 4) {
                                start(index);

                            }
                        }
                    })

                    .normalImageRes(getImageResource());
            bmb.addBuilder(builder);
        }
    }
    public static int getString() {
        if (str >= Strings.length) str = 0;
        return Strings[str++];
    }
    public static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }
    public void start(int pos)
    {
        Toast.makeText(this, Integer.toString(pos), Toast.LENGTH_LONG).show();
    }
}
