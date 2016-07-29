package com.example.androidhive;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PaymentActivity extends Activity {
	
	Button btnMakePayment;
	
	EditText inputMemberId;
	EditText inputPaymentAmt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment);
		/*btnMakePayment 		= (Button) findViewById(R.id.btnMakePayment);
		
		btnMakePayment.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//String memberId 		= inputMemberId.getText().toString();
				//String paymentAmount 	= inputPaymentAmt.getText().toString();
			}
		});*/
	}

}
