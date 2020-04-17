package com.example.httptraining;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.httptraining.Pojo.User;

import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    public static final String USER_ID = "id";
    private String getUserId;

    private TextView txtUserName;
    private TextView txtUserNick;
    private TextView txtUserEmail;
    private TextView txtStreet;
    private TextView txtSuite;
    private TextView txtCity;
    private TextView txtZipCode;
    private TextView txtPhone;
    private TextView txtWebsite;
    private TextView txtCompanyName;
    private TextView txtCatchPhrase;
    private TextView txtBS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        txtUserName = findViewById(R.id.user_name);
        txtUserNick = findViewById(R.id.user_nick);
        txtUserEmail = findViewById(R.id.user_email);
        txtStreet = findViewById(R.id.user_street);
        txtSuite = findViewById(R.id.user_suite);
        txtCity = findViewById(R.id.user_city);
        txtZipCode = findViewById(R.id.user_zipcode);
        txtPhone = findViewById(R.id.user_phone);
        txtWebsite = findViewById(R.id.user_website);
        txtCompanyName = findViewById(R.id.company_name);
        txtCatchPhrase = findViewById(R.id.catchPhrase);
        txtBS = findViewById(R.id.user_bs);

        getExtraFromIntent();
        initUser();
    }

    private void getExtraFromIntent() {
        getUserId = getIntent().getStringExtra(USER_ID);
    }
    private void initUser() {
        List<User> users = findByUser(getUserId);
    }

    private List<User> findByUser(String getId) {
        List<User> userList = Constans.LIST_RESPONSE;
        for (int i = 0; i < userList.size(); i++){
            User user = userList.get(i);
            String id = user.getId();
            if (id.equalsIgnoreCase(getId)) {
                String name = user.getName();
                txtUserName.setText(name);
                String nickName = user.getNickname();
                txtUserNick.setText(nickName);
                String email = user.getEmail();
                txtUserEmail.setText(email);
                String street = user.getStreet();
                txtStreet.setText(street);
                String suite = user.getSuite();
                txtSuite.setText(suite);
                String city = user.getCity();
                txtCity.setText(city);
                String zipCode = user.getZipcode();
                txtZipCode.setText(zipCode);
                String phone = user.getPhone();
                txtPhone.setText(phone);
                String website = user.getWebsite();
                txtWebsite.setText(website);
                String companyName = user.getCompanyName();
                txtCompanyName.setText(companyName);
                String catchPhrase = user.getCatchPhrase();
                txtCatchPhrase.setText(catchPhrase);
                String bs = user.getBs();
                txtBS.setText(bs);
            }
        }
        return userList;
    }





}
