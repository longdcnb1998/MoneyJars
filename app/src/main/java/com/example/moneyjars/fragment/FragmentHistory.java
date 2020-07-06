package com.example.moneyjars.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyjars.DialogSelectJar;
import com.example.moneyjars.R;
import com.example.moneyjars.adapter.HistoryAdapter;
import com.example.moneyjars.adapter.HistoryDetailAdapter;
import com.example.moneyjars.databinding.FragmentHistoryBinding;
import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.entity.NoteHistory;
import com.example.moneyjars.util.DialogUtils;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentHistory extends Fragment {

    private FragmentHistoryBinding binding;
    private MonthYearPickerDialogFragment dialogFragment;
    private NoteHistoryViewModel viewModel;
    private HistoryDetailAdapter adapter;
    private int yearSelected;
    private int monthSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(NoteHistoryViewModel.class);
        Calendar calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);
        binding.tvDate.setText(monthSelected+1 +"/"+yearSelected);
        viewModel.getHistoryUser(1).observe(getActivity(),data ->{
            adapter = new HistoryDetailAdapter(getContext(), (ArrayList<NoteHistory>) data, 1, new HistoryAdapter.Callback() {
                @Override
                public void onClick(int position) {
                    Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                }
            });
            binding.lvHistory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
            binding.lvHistory.setAdapter(adapter);
        });
        binding.btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment = MonthYearPickerDialogFragment.getInstance(monthSelected, yearSelected);
                dialogFragment.show(getChildFragmentManager(), null);
                dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int year, int monthOfYear) {
                        binding.tvDate.setText(monthOfYear+1 +"/"+year);
                    }
                });
            }
        });

        binding.btnSelectJar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DialogUtils.enableShowDialogFragment(getChildFragmentManager(), DialogSelectJar.class.getSimpleName())){
                    new DialogSelectJar(3, null, new DialogSelectJar.confirmCallback() {
                        @Override
                        public void onSuccess(ArrayList<Jar> list) {
                            if (list.get(0) != null){
                                binding.tvNameJars.setText(list.get(0).getNameJar());
                                viewModel.getHistoryJar(list.get(0).getId()).observe(getActivity(),data ->{
                                    adapter.setListHistory((ArrayList<NoteHistory>) data);
                                });
                            }
                            else {
                                viewModel.getHistoryUser(1).observe(getActivity(),data ->{
                                    binding.tvNameJars.setText("Tất cả các hũ");
                                   adapter.notifyDataSetChanged();
                                });
                            }
                        }
                    }).show(getChildFragmentManager(),DialogSelectJar.class.getSimpleName());
                }
            }
        });
    }

}
