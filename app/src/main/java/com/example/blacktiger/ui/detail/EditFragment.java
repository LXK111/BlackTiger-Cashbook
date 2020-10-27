package com.example.blacktiger.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.blacktiger.data.Entity.Blacktiger;
import com.google.gson.Gson;
import com.example.blacktiger.R;
import com.example.blacktiger.adapters.BlacktigerAdapter;
import com.example.blacktiger.ui.add.AddActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditFragment extends Fragment {
    public static final String BLACKTIGER_EDIT = "blacktiger_edit";
    private DetailViewModel detailViewModel;
    private Blacktiger blacktiger;
    private TextView type, amount, info, date, category,account,members;
    private ImageView icon;
    private Button bt_edit, bt_delete;
    private DecimalFormat mAmountFormat = new DecimalFormat("0.00");
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm E");

    public EditFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);
        getActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        type = root.findViewById(R.id.textView_edit_type);
        amount = root.findViewById(R.id.textView_edit_amount);
        date = root.findViewById(R.id.textView_edit_date);
        info = root.findViewById(R.id.textView_edit_info);
        icon = root.findViewById(R.id.imageView_edit_icon);
        category = root.findViewById(R.id.textView_edit_category);
        bt_edit = root.findViewById(R.id.button_edit_edit);
        bt_delete = root.findViewById(R.id.button_edit_delete);
        account = root.findViewById(R.id.textView_edit_account);
        members = root.findViewById(R.id.textView_edit_members);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        Gson gson = new Gson();
        String blacktigerJson = getArguments().getString(BLACKTIGER_EDIT);
        if (blacktigerJson != null) {
            blacktiger = gson.fromJson(blacktigerJson, Blacktiger.class);
            if (blacktiger.isType()) {
                type.setText("支出");
                amount.setText("-" + mAmountFormat.format(blacktiger.getAmount()));
            } else {
                type.setText("收入");
                amount.setText("+" + mAmountFormat.format(blacktiger.getAmount()));
            }
            date.setText(sdf.format(new Date(blacktiger.getTime())));
            category.setText(blacktiger.getCategory());
            info.setText(blacktiger.getNote());
            icon.setImageDrawable(getContext().getDrawable(BlacktigerAdapter.getDrawableId(blacktiger.getIcon())));
            account.setText(blacktiger.getAccount());
            members.setText(blacktiger.getMembers());
        }

        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blacktiger != null) {
                    Intent intent = new Intent(getActivity(), AddActivity.class);
                    intent.putExtra(BLACKTIGER_EDIT, blacktigerJson);
                    startActivity(intent);
                }
            }
        });
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blacktiger != null)
                    detailViewModel.deleteWasteBook(blacktiger);
                Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigateUp();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
    }
}
