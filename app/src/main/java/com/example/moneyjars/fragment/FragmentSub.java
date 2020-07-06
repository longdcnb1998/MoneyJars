package com.example.moneyjars.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.moneyjars.DialogSelectJar;
import com.example.moneyjars.KeyboardController;
import com.example.moneyjars.R;
import com.example.moneyjars.databinding.FragmentSubBinding;
import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.entity.NoteHistory;
import com.example.moneyjars.util.DialogUtils;
import com.example.moneyjars.viewmodel.JarViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class FragmentSub extends Fragment {

    private FragmentSubBinding binding;
    private JarViewModel viewModel;
    private NoteHistoryViewModel historyViewModel;
    private ArrayList<Jar> listSelected = new ArrayList<>();

    public static FragmentSub getInstance() {
        FragmentSub fragment = new FragmentSub();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(JarViewModel.class);
        viewModel.getListJar(1).observe(getActivity(), new Observer<List<Jar>>() {
            @Override
            public void onChanged(List<Jar> jars) {
                binding.tvNameJar.setText(jars.get(0).getNameJar());
            }
        });
        historyViewModel = ViewModelProviders.of(getActivity()).get(NoteHistoryViewModel.class);
        KeyboardController.show(getActivity(), binding.edtAmount);
        binding.edtAmount.requestFocus();
        binding.btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        binding.tvSelectDate.setText("Ngày " + dayOfMonth + ", Tháng " + month + ", Năm " + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        binding.btnSelectJar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DialogUtils.enableShowDialogFragment(getChildFragmentManager(), DialogSelectJar.class.getSimpleName())) {
                    new DialogSelectJar(0, listSelected, new DialogSelectJar.confirmCallback() {
                        @Override
                        public void onSuccess(ArrayList<Jar> list) {
                            listSelected = list;
                            binding.tvNameJar.setText(listSelected.get(0).getNameJar());
                        }
                    }).show(getChildFragmentManager(), DialogSelectJar.class.getSimpleName());
                }
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listSelected.size() > 0) {
                    for (int i = 0; i < listSelected.size(); i++) {
                        int amount = Integer.parseInt(binding.edtAmount.getText().toString().trim());
                        if (amount > 0) {
                            Jar jar = listSelected.get(i);
                            jar.setExpense(Integer.parseInt(listSelected.get(i).getExpense()) + amount + "");
                            viewModel.insertJar(jar);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    historyViewModel.insertHistory(new NoteHistory(jar.getId(), jar.getNameJar(),jar.getAvatar(),1
                                    ,0,amount+""
                                            ,binding.tvSelectDate.getText().toString().trim(),binding.edtDes.getText().toString().trim()));
                                }
                            }).start();
                        } else {
                            Toast.makeText(getActivity(), "Số tiền cần lớn hơn 0", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getActivity(), "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                        KeyboardController.hide(getContext(), binding.edtAmount);
                    }
                } else {
                    Toast.makeText(getActivity(), "Bạn chưa chon hũ tiền", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
