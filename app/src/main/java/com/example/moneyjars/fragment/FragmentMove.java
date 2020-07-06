package com.example.moneyjars.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.moneyjars.DialogSelectJar;
import com.example.moneyjars.R;
import com.example.moneyjars.databinding.FragmentMoveBinding;
import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.util.DialogUtils;
import com.example.moneyjars.viewmodel.JarViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragmentMove extends Fragment {

    private FragmentMoveBinding binding;
    private ArrayList<Jar> listSelected;
    private JarViewModel viewModel;
    private Jar jarStart,jarEnd;

    public static FragmentMove getInstance() {
        FragmentMove fragment = new FragmentMove();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_move,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(JarViewModel.class);


        binding.jarStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DialogUtils.enableShowDialogFragment(getChildFragmentManager(), DialogSelectJar.class.getSimpleName())){
                    new DialogSelectJar(3, listSelected, new DialogSelectJar.confirmCallback() {
                        @Override
                        public void onSuccess(ArrayList<Jar> list) {
                            jarStart = list.get(0);
                            Picasso.with(getContext())
                                    .load(list.get(0).getAvatar())
                                    .into(binding.ivJarStart);
                        }
                    }).show(getChildFragmentManager(),DialogSelectJar.class.getSimpleName());
                }
            }
        });

        binding.jarEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DialogUtils.enableShowDialogFragment(getChildFragmentManager(), DialogSelectJar.class.getSimpleName())){
                    new DialogSelectJar(3, listSelected, new DialogSelectJar.confirmCallback() {
                        @Override
                        public void onSuccess(ArrayList<Jar> list) {
                            jarEnd = list.get(0);
                            Picasso.with(getContext())
                                    .load(list.get(0).getAvatar())
                                    .into(binding.ivJarEnd);
                        }
                    }).show(getChildFragmentManager(),DialogSelectJar.class.getSimpleName());
                }
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jarEnd!= null && jarStart!= null){
                    String amount = binding.edtAmount.getText().toString().trim();
                    if (amount.length() >0){
                        int money = Integer.parseInt(amount);
                        if (money == 0){
                            Toast.makeText(getActivity(), "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            int startIncome = Integer.parseInt(jarStart.getIncome()) - money;
                            jarStart.setIncome(startIncome +"");
                            int endIncome = Integer.parseInt(jarEnd.getIncome()) + money;
                            jarEnd.setIncome(endIncome+"");
                            viewModel.insertJar(jarStart);
                            viewModel.insertJar(jarEnd);
                            Toast.makeText(getActivity(), "Bạn đã chuyển thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getActivity(), "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
