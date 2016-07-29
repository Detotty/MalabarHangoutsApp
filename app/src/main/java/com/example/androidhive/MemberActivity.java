package com.example.androidhive;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidhive.library.DatabaseHandler;
import com.example.androidhive.library.UserFunctions;
import com.example.androidhive.utils.ImageLoader;

public class MemberActivity extends Activity {
	
	Button btnViewMember;
	Button btnLinkToRegister;
	Button btnLinkviewMember;
	
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;
	
	TextView memberInfoMsg;
	TextView memberName;
	TextView memberNextPaymentDate;
	ImageView memberImage;
	

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "firstName";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	private static String KEY_NEXT_PAYMENT_DUE_DATE = "nextPaymentDueDate";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member);

		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginMemberid);
		//inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnViewMember = (Button) findViewById(R.id.btnViewMember);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		
		
		memberInfoMsg=(TextView) findViewById(R.id.member_information);
		memberName=(TextView) findViewById(R.id.member_name);
		memberNextPaymentDate=(TextView) findViewById(R.id.member_nextpayment_date);
		memberImage=(ImageView)findViewById(R.id.memberImage);
		//btnLinkviewMember =(Button)findViewById(R.id.btnLinkToMemberScreen);
		//loginErrorMsg = (TextView) findViewById(R.id.login_error);

		// Login button Click Event
		btnViewMember.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String memberId = inputEmail.getText().toString();
				//String password = inputPassword.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				//Log.d("Button", "Login");
				JSONObject json = userFunction.viewMember(memberId,"");
				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						//loginErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS); 
						JSONObject json_user2 = json.getJSONObject("user");
						
						String member_email2=json_user2.getString(KEY_EMAIL);
						String member_name=json_user2.getString(KEY_NAME);
						String member_NextPayment=json_user2.getString(KEY_NEXT_PAYMENT_DUE_DATE);
						
						
						memberInfoMsg.setText(member_email2);
						memberName.setText(member_name);
						memberNextPaymentDate.setText(member_NextPayment);
						
						int loader = R.drawable.ic_launcher;
						//String image_url = "http://api.androidhive.info/images/sample.jpg";
						String image_url = "http://malabarhangouts.com/cannoneers/fetchMemberPhoto?memberId="+memberId;
						
						// ImageLoader class instance
				        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
				        //whenever you want to load an image from url
				        // call DisplayImage function
				        // url - image url to load
				        // loader - loader image, will be displayed before getting image
				        // image - ImageView 
				        imgLoader.DisplayImage(image_url, loader, memberImage);
						
						if(Integer.parseInt(res) == 2){
							// user successfully logged in
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");
							String member_email=json_user.getString(KEY_EMAIL);
							memberInfoMsg.setText(member_email);
							// Clear all previous data in database
							//userFunction.logoutUser(getApplicationContext());
							//db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
							
							// Launch Dashboard Screen
							Intent dashboard = new Intent(getApplicationContext(), MemberActivity.class);
							
							// Close all views before launching Dashboard
							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(dashboard);
							
							// Close Login Screen
							finish();
						}else{
							// Error in login
							//loginErrorMsg.setText("Invalid MemberId");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

}
