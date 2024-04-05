package com.shilight.cm.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shilight.cm.Login;
import com.shilight.cm.R;
import com.shilight.cm.about;
import com.shilight.cm.databinding.FragmentMyBinding;
import com.shilight.cm.sever.UserInformation;
import com.shilight.cm.sever.UserLogin;
import com.shilight.cm.web;


public class myFragment extends Fragment {

    private FragmentMyBinding binding;
    LinearLayout grxx,qhzh,yszc,about;
    TextView userText;TextView nameText;
    ImageView loginImg;TextView loginText;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
         userText = root.findViewById(R.id.userid);
         nameText = root.findViewById(R.id.name);
         loginImg = root.findViewById(R.id.img_login);
         loginText = root.findViewById(R.id.lgin);


        grxx = root.findViewById(R.id.grxx);
        qhzh = root.findViewById(R.id.qhzh);
        yszc = root.findViewById(R.id.yszc);
        about = root.findViewById(R.id.about);

        grxx.setOnClickListener(view -> {

            if(UserLogin.isLogin(getActivity())){
                Intent go = new Intent();
                go.setClass(getActivity(),com.shilight.cm.grxx.class);
                startActivity(go);

            }
            Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
        });
        qhzh.setOnClickListener(view -> {
            Intent go1 = new Intent();
            go1.setClass(getActivity(), Login.class);
            startActivity(go1);
        });
        yszc.setOnClickListener(view -> {
            Intent go1 = new Intent();
            go1.setClass(getActivity(), web.class);
            go1.putExtra("data","https://api.shilight.cn/PARK/privacy.html");
            go1.putExtra("tittle","隐私政策");
            startActivity(go1);
        });
        about.setOnClickListener(view -> {
            Intent go1 = new Intent();
            go1.setClass(getActivity(), com.shilight.cm.about.class);
            startActivity(go1);
        });

        if(UserLogin.isLogin(getActivity())){
            loginText.setVisibility(View.GONE);
            UserInformation userInformation = UserInformation.getInformation(getActivity());
            userText.setVisibility(View.VISIBLE);
            nameText.setVisibility(View.VISIBLE);
            userText.setText(userInformation.getUserid());
            nameText.setText(userInformation.getName());


        }else{
            Log.i("no","");
        }


        loginImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent();
                go.setClass(getActivity(), Login.class);
                startActivity(go);
            }
        });





        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(UserLogin.isLogin(getActivity())){
            loginText.setVisibility(View.GONE);
            UserInformation userInformation = UserInformation.getInformation(getActivity());
            userText.setVisibility(View.VISIBLE);
            nameText.setVisibility(View.VISIBLE);
            userText.setText(userInformation.getUserid());
            nameText.setText(userInformation.getName());


        }else{
            Log.i("no","");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}