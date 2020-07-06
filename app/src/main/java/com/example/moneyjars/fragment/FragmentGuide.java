package com.example.moneyjars.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moneyjars.R;

public class FragmentGuide extends Fragment {

    private ImageView img_guide;
    private TextView tv_title, tv_des;


    public static FragmentGuide newInstance(int position) {
        FragmentGuide fragmentGuide = new FragmentGuide();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragmentGuide.setArguments(bundle);
        return fragmentGuide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        img_guide = view.findViewById(R.id.img_guide);
        tv_title = view.findViewById(R.id.tv_guide_title);
        tv_des = view.findViewById(R.id.tv_description);
        int position = getArguments().getInt("position");
        switch (position) {
            case 0:
                int source1 = R.mipmap.money_jar;
                String title1 = getActivity().getResources().getString(R.string.guide1);
                String des1 = getActivity().getResources().getString(R.string.guide1_des);
                updateView(source1, title1, des1);
                break;

            case 1:
                int source2 = R.mipmap.pig;
                String title2 = getActivity().getResources().getString(R.string.guide2);
                String des2 = getActivity().getResources().getString(R.string.guide2_des);
                updateView(source2, title2, des2);
                break;
            case 2:
                int source3 = R.mipmap.wallet;
                String title3 = getActivity().getResources().getString(R.string.guide3);
                String des3 = getActivity().getResources().getString(R.string.guide3_des);
                updateView(source3, title3, des3);
                break;
        }

        super.onViewCreated(view, savedInstanceState);
    }

    private void updateView(int imgSource, String title, String description) {
        img_guide.setImageResource(imgSource);
        tv_title.setText(title);
        tv_des.setText(description);
    }
}
