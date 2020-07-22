package com.example.moneyjars.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.moneyjars.R;
import com.example.moneyjars.databinding.FragmentChartBinding;
import com.example.moneyjars.viewmodel.JarViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentChart extends Fragment {

    private FragmentChartBinding binding;
    private JarViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chart, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(JarViewModel.class);


        Cartesian cartesian = AnyChart.column();
        List<DataEntry> data = new ArrayList<>();

        viewModel.getListJar(1).observe(getActivity(), listJar ->{
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
            cartesian.yAxis(0).title("Số tiền ");
        });
        binding.anyChart.setChart(cartesian);

    }
}
