package com.example.moneyjars.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.moneyjars.DialogDetailJar;
import com.example.moneyjars.R;
import com.example.moneyjars.activity.DealActivity;
import com.example.moneyjars.adapter.HistoryAdapter;
import com.example.moneyjars.adapter.JarsAdapter;
import com.example.moneyjars.databinding.FragmentHomeBinding;
import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.entity.NoteHistory;
import com.example.moneyjars.util.DialogUtils;
import com.example.moneyjars.viewmodel.JarViewModel;
import com.example.moneyjars.viewmodel.NoteHistoryViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.moneyjars.activity.MainActivity.mLastClickTime;

public class FragmentHome extends Fragment {

    private FragmentHomeBinding binding;
    private Activity activity;
    private JarsAdapter adapter;
    private JarViewModel jarViewModel;
    private SharedPreferences preferences;
    private NoteHistoryViewModel historyViewModel;
    private HistoryAdapter historyAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        activity = getActivity();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferences = getActivity().getSharedPreferences("UserAccount", MODE_PRIVATE);

        String userName = preferences.getString("UserName", "");
        jarViewModel = ViewModelProviders.of(this).get(JarViewModel.class);
        historyViewModel = ViewModelProviders.of(this).get(NoteHistoryViewModel.class);
        jarViewModel.getListJar(1).observe(this, data -> {
            long amount = 0;
            long income = 0;
            long expense = 0;
            for (int i=0;i<data.size();i++){
                income +=Integer.parseInt(data.get(i).getIncome());
                expense += Integer.parseInt(data.get(i).getExpense());
            }
            amount = income - expense;
            if (amount <0) amount= 0;
            binding.tvAmountInCome.setText(income+" "+getString(R.string.VND));
            binding.tvAmountOutCome.setText(expense +" "+getString(R.string.VND));
            binding.totalBalances.setText(amount +"");
            adapter = new JarsAdapter(getActivity(), (ArrayList<Jar>) data, new JarsAdapter.Callback() {
                @Override
                public void onClick(int position) {
                    if (DialogUtils.enableShowDialogFragment(getFragmentManager(), DialogDetailJar.class.getSimpleName())){
                        new DialogDetailJar(data.get(position)).show(getFragmentManager(),DialogDetailJar.class.getSimpleName());
                    }
                }
            });

            binding.recyclerViewJars.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            binding.recyclerViewJars.setAdapter(adapter);
        });
        binding.tvName.setText(userName);


        historyViewModel.getHistoryUser(1).observe(getActivity(),data ->{
           if (data.size() > 0){
               historyAdapter = new HistoryAdapter(getActivity(),(ArrayList<NoteHistory>) data);
               binding.lvHistory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
               binding.lvHistory.setAdapter(historyAdapter);
           }
        });

        binding.layoutInCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                DealActivity.start(getContext(),0);
            }
        });
        binding.layoutOutCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                DealActivity.start(getContext(),1);
            }
        });
        binding.btnShowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(1);
            }
        });
        binding.btnShowChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(2);
            }
        });
        Cartesian cartesian = AnyChart.column();
        List<DataEntry> data = new ArrayList<>();

        jarViewModel.getListJar(1).observe(getActivity(), listJar ->{
            data.add(new ValueDataEntry(listJar.get(0).getNameJar(), Integer.parseInt(listJar.get(0).getIncome())));
            data.add(new ValueDataEntry(listJar.get(1).getNameJar(), Integer.parseInt(listJar.get(1).getIncome())));
            data.add(new ValueDataEntry(listJar.get(2).getNameJar(), Integer.parseInt(listJar.get(2).getIncome())));
            data.add(new ValueDataEntry(listJar.get(3).getNameJar(), Integer.parseInt(listJar.get(3).getIncome())));
            data.add(new ValueDataEntry(listJar.get(4).getNameJar(), Integer.parseInt(listJar.get(4).getIncome())));
            data.add(new ValueDataEntry(listJar.get(5).getNameJar(), Integer.parseInt(listJar.get(5).getIncome())));

            Column column = cartesian.column(data);

            column.tooltip()
                    .titleFormat("{%X}")
                    .position(Position.CENTER_BOTTOM)
                    .anchor(Anchor.CENTER_BOTTOM)
                    .offsetX(0d)
                    .offsetY(5d)
                    .format("đ{%Value}{groupsSeparator: }");

            cartesian.animation(true);
            cartesian.title("Báo cáo thu chi");

            cartesian.yScale().minimum(0d);

            cartesian.yAxis(0).labels().format("đ{%Value}{groupsSeparator: }");

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
            cartesian.interactivity().hoverMode(HoverMode.BY_X);

            cartesian.xAxis(0).title("Các hũ tiền");
            cartesian.yAxis(0).title("Số tiền tiền");
        });
        binding.charView.setChart(cartesian);
    }
}
