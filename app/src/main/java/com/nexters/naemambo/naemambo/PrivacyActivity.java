package com.nexters.naemambo.naemambo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class PrivacyActivity extends AppCompatActivity {

    TextView privacy_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_content);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        String content = "<p><b><font color='#333333'>제 1조 개인 정보 수집ㆍ이용목적</font></b></p> \n" +
                "                ① 담고풀고는 이용자의 정보수집 시 서비스 제공에 필요한 최소한의 정보를 수집합니다.<br> \n" +
                "                ② 담고풀고 이용자의 개인식별이 가능한 개인정보를 수집하는 때에는 이용자의 동의를 받습니다.<br> \n" +
                "                ③ 제공된 개인정보는 당해 이용자의 동의 없이 제3자에게 제공할 수 없으며, 이에 대한 모든 책임은 담고풀고가 집니다.<br> \n" +
                "                ④ 담고풀고가 제2항과 제3항에 의해 이용자의 동의를 받아야 하는 경우에는 개인정보관리 책임자의 신원 (소속, 성명 및 전화번호 기타 연락처), 정보의 수집목적 및 이용목적, 제3자에 대한 정보제공 관련사항 (제공받는자, 제공목적 및 제공할 정보의 내용) 등 정보통신망이용촉진 등에 관한 법률 제16조 제3항이 규정한 사항을 미리 명시하거나 고지해야 하며 회원은 언제든지 이 동의를 철회할 수 있습니다.<br> \n" +
                "                <p><b><font color='#333333'>제 2조 수집하는 개인정보 항목</font></b><p> \n" +
                "                ① 담고풀고가 서비스 제공을 위해 받는 회원 정보는 이름, Facebook ID, Facebook Profile Image 등입니다. 이 이외에 서비스 상품별 추가적인 정보를 요구할 수도 있습니다.<br> \n" +
                "                ② 고객님께서 담고풀고에서 제공하는 서비스를 받는 동안 회원의 개인정보는 담고풀고에서 계속 보유하며 서비스 제공을 위해 이용하게 됩니다. 단, 담고풀고 이용절차에 따라 회원의 정보 삭제를 요청하거나 표명된 고객 자격 상실 사유에 의해 고객의 이용자격을 제한 및 정지시키는 경우에는 해당 개인의 정보는 즉시 삭제되며, 어떠한 용도로도 열람 또는 이용될 수 없도록 처리됩니다.";

        privacy_content = (TextView) findViewById(R.id.txt_privacy_content);
        privacy_content.setText(Html.fromHtml(content));
    }
}
