package com.example.moneyjars;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyjars.adapter.AddSelectJarAdapter;
import com.example.moneyjars.adapter.SubSelectJarAdapter;
import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.util.DialogUtils;
import com.example.moneyjars.viewmodel.JarViewModel;

import java.util.ArrayList;

public class DialogSelectJar extends DialogFragment {

    private JarViewModel viewModel;
    private Activity activity;
    private Dialog dialog;
    private AddSelectJarAdapter adapter;
    private Button btnConfirm;
    private RecyclerView lvSelectJar;
    private TextView tv100Percent;
    private View viewEmpty;
    private ArrayList<Jar> listSelected;
    private confirmCallback callback;
    private int type;
    private SubSelectJarAdapter subAdapter;
    private ArrayList<Jar> listPicked = new ArrayList<>();
    private SubSelectJarAdapter moveAdapter;

    public DialogSelectJar(int type, ArrayList<Jar> listSelected, confirmCallback callback) {
        this.type = type;
        this.callback = callback;
        this.listSelected = listSelected;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = DialogUtils.getNewDialogTransparent(activity, R.anim.slide_up);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_select_jar);
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        initData();
        viewModel = ViewModelProviders.of(getActivity()).get(JarViewModel.class);
        viewModel.getListJar(1).observe(getActivity(), data -> {
            if (type == 1) {
                adapter = new AddSelectJarAdapter(activity, (ArrayList<Jar>) data, new AddSelectJarAdapter.Callback() {
                    @Override
                    public void onClick(int position) {
                        if (data.get(position).isChecked()) {
                            listSelected.add(data.get(position));
                        } else {
                            listSelected.remove(data.get(position));
                        }
                    }
                });
                lvSelectJar.setAdapter(adapter);
            } else if (type == 0 || type == 3) {
                for (Jar jar : data) {
                    jar.setChecked(false);
                }
                data.get(0).setChecked(true);
                subAdapter = new SubSelectJarAdapter(activity, (ArrayList<Jar>) data, new SubSelectJarAdapter.Callback() {
                    @Override
                    public void onClick(int position, boolean checked) {
                        listSelected = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setChecked(false);
                        }
                        if (!checked) {
                            data.get(position).setChecked(true);
                        }
                        subAdapter.notifyDataSetChanged();
                        if (data.get(position).isChecked()) {
                            listSelected.add(data.get(position));
                        }
                    }
                });
                lvSelectJar.setAdapter(subAdapter);
            }
        });
    }

    private void initUI() {
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        tv100Percent = dialog.findViewById(R.id.tvPercent);
        lvSelectJar = dialog.findViewById(R.id.lvJars);
        viewEmpty = dialog.findViewById(R.id.viewEmpty);
        lvSelectJar.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        listSelected = new ArrayList<>();
    }

    private void initData() {
        viewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onSuccess(listSelected);
                }
                dismiss();
            }
        });
    }

    public interface confirmCallback {
        void onSuccess(ArrayList<Jar> listSelected);
    }
}
