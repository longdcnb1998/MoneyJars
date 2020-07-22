package com.example.moneyjars;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
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
import com.example.moneyjars.adapter.HistoryAdapter;
import com.example.moneyjars.databinding.DialogDetailJarBinding;
import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.entity.NoteHistory;
import com.example.moneyjars.viewmodel.NoteHistoryViewModel;
import com.example.moneyjars.util.DialogUtils;
import com.example.moneyjars.viewmodel.JarViewModel;

import java.util.ArrayList;
import java.util.List;

public class DialogDetailJar extends DialogFragment {

    private Activity activity;
    private Dialog dialog;
    private Jar jar;
    private DialogDetailJarBinding binding;
    private ArrayList<Jar> listSelected;
    private JarViewModel jarViewModel;
    private NoteHistoryViewModel historyViewModel;
    private HistoryAdapter historyAdapter;

    public DialogDetailJar(Jar jar) {
        this.jar = jar;
        listSelected = new ArrayList<>();
        listSelected.add(jar);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = DialogUtils.getNewDialog(activity,false,activity.getResources().getColor(R.color.statusBarColor));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_detail_jar);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.dialog_detail_jar,null,false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI(jar);
        historyViewModel = ViewModelProviders.of(getActivity()).get(NoteHistoryViewModel.class);
        jarViewModel = ViewModelProviders.of(getActivity()).get(JarViewModel.class);
        initView(jar);
        initData();
    }

    private void initView(Jar selectJar) {
        historyViewModel.getHistoryJar(selectJar.getId()).observe(getActivity(), data ->{
            historyAdapter = new HistoryAdapter(getContext(), (ArrayList<NoteHistory>) data);
            binding.lvHistory.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL,false));
            binding.lvHistory.setAdapter(historyAdapter);
        });
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> dataEntries = new ArrayList<>();

        dataEntries.add(new ValueDataEntry("Thu nhập", Integer.parseInt(selectJar.getIncome())));
        dataEntries.add(new ValueDataEntry("Chi tiêu", Integer.parseInt(selectJar.getExpense())));
        Column column = cartesian.column(dataEntries);

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

        cartesian.xAxis(0).title("Hũ tiền");
        cartesian.yAxis(0).title("Số tiền tiền");
        binding.charView.setChart(cartesian);
    }

    private void initUI(Jar jar) {
        binding.tvNameJar.setText(jar.getNameJar());
        binding.totalBalances.setText(Integer.parseInt(jar.getIncome()) - Integer.parseInt(jar.getExpense()) +"");
        binding.tvAmountInCome.setText(jar.getIncome());
        binding.tvAmountOutCome.setText(jar.getExpense());
    }

    private void initData() {
        binding.btnSelectJar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DialogUtils.enableShowDialogFragment(getChildFragmentManager(),DialogSelectJar.class.getSimpleName())){
                    new DialogSelectJar(0, listSelected, new DialogSelectJar.confirmCallback() {
                        @Override
                        public void onSuccess(ArrayList<Jar> listSelected) {
                            initUI(listSelected.get(0));
                            initView(listSelected.get(0));
                        }
                    }).show(getChildFragmentManager(),DialogSelectJar.class.getSimpleName());
                }
            }
        });

        binding.btnShowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(1);
                dismiss();
            }
        });
        binding.btnShowChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(2);
                dismiss();
            }
        });
    }
}
