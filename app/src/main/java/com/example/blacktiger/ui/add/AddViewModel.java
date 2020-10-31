package com.example.blacktiger.ui.add;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.util.Function;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.blacktiger.HomeActivity;
import com.example.blacktiger.R;
import com.example.blacktiger.data.AccountRepository;
import com.example.blacktiger.data.BlacktigerRepository;
import com.example.blacktiger.data.CategoryRepository;
import com.example.blacktiger.data.Entity.Account;
import com.example.blacktiger.data.Entity.Blacktiger;
import com.example.blacktiger.data.Entity.Category;
import com.example.blacktiger.utils.CommonUtils;
import com.example.blacktiger.utils.DatePickUtils;
import com.example.blacktiger.utils.DateToLongUtils;
import com.example.blacktiger.utils.ResUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private SimpleDateFormat mDateFormat;
    private DecimalFormat mAmountFormat = new DecimalFormat("0.00");
    private ObservableField<String> mDateText = new ObservableField<>();
    private ObservableField<String> mAmountText = new ObservableField<>();
    private ObservableField<String> mDesc = new ObservableField<>();
    private ObservableField<String> mCate2 = new ObservableField<>();
    private ObservableField<String> mType = new ObservableField<>();
    private ObservableField<String> mAccount = new ObservableField<>();
    private ObservableField<String> mMembers = new ObservableField<>();
    private String iconId;
    private long mDate;
    private double mAmount;
    private BlacktigerRepository blacktigerRepository;
    private CategoryRepository categoryRepository;
    private Blacktiger blacktigerEdit;

    public SharedPreferences Setting = getApplication().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);

    private AlertDialog alertDialog1;
    private AccountRepository accountRepository;
    private List<Account> AccountList;

    public AddViewModel(@NonNull Application application) {
        super(application);
        mDateFormat = new SimpleDateFormat(ResUtils.getString(
                application, R.string.date_format_y_m_d) + " HH:mm", Locale.getDefault());
        mDate = System.currentTimeMillis();
        mDateText.set(mDateFormat.format(new Date(mDate)));
        blacktigerRepository = new BlacktigerRepository(application);
        categoryRepository = new CategoryRepository(application);
        accountRepository = new AccountRepository(application);
    }


    /**
     * 消费说明点击
     */
    public void onDescClick(Activity activity) {

        EditText input = new EditText(activity);
        input.setText(getDesc().get());
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("添加备注").setView(input)
                .setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String tmp;
                tmp = input.getText().toString().trim();
                if (!tmp.isEmpty()) {
                    setmDesc(tmp);
                }
            }
        }).show();
    }


    /**
     * 账户点击
     */

    public void onAccountClick(Activity activity) {
    /**
        ArrayList<String> result=new ArrayList<String>();
        result = accountRepository.getAllAccountsName();
    **/

        ///final String items[] = result.toArray(new String[0]);
        //final String items[] = {"校园卡","平安银行","工商银行","蚂蚁花呗","信用卡","微信","支付宝"};
        //final  String items[] = accountRepository.getAllAccountsName().toArray(new String[0]);

        final String items[] = Setting.getString("account", String.valueOf(0)).split("\\s+");
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
        alertBuilder.setTitle("选择账户");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(activity, items[i], Toast.LENGTH_SHORT).show();
                String acc;
                acc = items[i];
                alertDialog1.dismiss();
                setmAccount(acc);
            }
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }

    /**
     * 二级类别点击
     */

    public void onCategory2Click(Activity activity) {

        EditText input = new EditText(activity);
        input.setText(getCate2().get());
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("添加二级类别").setView(input)
                .setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String tmp;
                tmp = input.getText().toString().trim();
                if (!tmp.isEmpty()) {
                    setmCate2(tmp);
                }
            }
        }).show();
    }

    /**
     * 成员点击
     */

    public void onMembersClick(Activity activity) {
        //final String items[] = {"我","孩子","妻子","丈夫","父母","其他"};
        final String items[] = Setting.getString("member", String.valueOf(0)).split("\\s+");

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
        alertBuilder.setTitle("选择成员");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String mem;
                mem = items[i];
                alertDialog1.dismiss();
                setmMembers(mem);
            }
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }

    /**
     * 消费记录时间点击
     */
    public void onDateClick(Activity activity) {
        DatePickUtils.showDatePickDialog(activity,
                mDate,
                new DatePickUtils.OnDatePickListener() {
                    @Override
                    public void onDatePick(DialogInterface dialog,
                                           int year,
                                           int month,
                                           int dayOfMonth) {
                    }

                    @Override
                    public void onConfirmClick(DialogInterface dialog, long timeInMills) {
                        mDate = timeInMills;
                        mDateText.set(mDateFormat.format(new Date(mDate)));
                    }
                });
    }

    /**
     * 键盘数字点击
     */
    public void onNumberClick(String number) {
        String amount = mAmountText.get();
        amount = TextUtils.isEmpty(amount) ? "" : amount;
        if ("0".equals(amount)) {
            amount = "";
        }
        amount += number;
        mAmountText.set(amount);
        mAmount = CommonUtils.string2float(amount, 0);
    }

    /**
     * 键盘删除点击
     */
    public void onDeleteClick() {
        String amount = mAmountText.get();
        amount = TextUtils.isEmpty(amount) ? "" : amount;
        if (!TextUtils.isEmpty(amount)) {
            amount = amount.substring(0, amount.length() - 1);
        }
        if (TextUtils.isEmpty(amount)) {
            amount = "0";
        }
        mAmountText.set(amount);
        mAmount = CommonUtils.string2float(amount, 0);
    }

    /**
     * 键盘清除点击
     */
    public void onClearClick() {
        mAmountText.set("0");
        mAmount = 0;
    }

    /**
     * 键盘 . 点击
     */
    public void onDotClick() {
        String amount = mAmountText.get();
        amount = TextUtils.isEmpty(amount) ? "" : amount;
        if (!amount.contains(".")) {
            amount = amount + ".";
        }
        mAmountText.set(amount);
        mAmount = CommonUtils.string2float(amount, 0);
    }

    /**
     * 确定点击
     */
    public void onEnterClick(Activity activity) {
        if (getType().get() == null || getAmountText().get() == null || getAmountText().get().isEmpty() || iconId == null) {
            Toast.makeText(activity, "请输入完整的信息", Toast.LENGTH_SHORT).show();
        } else {
            Boolean blacktigerType = true;
            if (getText().getValue().contains("2")) blacktigerType = false;
            Log.e("getDesc", getDesc().get());
            if (blacktigerEdit != null) {
                blacktigerEdit.setAmount(Double.parseDouble(getAmountText().get()));
                blacktigerEdit.setAccount(getAccount().get());
                blacktigerEdit.setMembers(getMembers().get());
                blacktigerEdit.setIcon(iconId);
                blacktigerEdit.setCategory(getType().get());
                blacktigerEdit.setCategory2(getCate2().get());
                blacktigerEdit.setNote(getDesc().get());
                blacktigerEdit.setTime(mDate);
                blacktigerEdit.setType(blacktigerType);
                updateDate(blacktigerEdit);
                Toast.makeText(activity, "修改成功!", Toast.LENGTH_SHORT).show();
            } else {
                Blacktiger blacktiger = new Blacktiger(blacktigerType, Double.parseDouble(getAmountText().get()), getType().get(),getCate2().get(),getAccount().get(),getMembers().get(), iconId, mDate, getDesc().get());
                saveData(blacktiger);
            }
            //activity.finish();
            Intent intent = new Intent(activity, HomeActivity.class);
            activity.startActivity(intent);
        }
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    private void updateDate(Blacktiger blacktiger) {
        Log.e("xxxxxx", "update");
        blacktigerRepository.updateBlacktiger(blacktiger);
    }

    private void saveData(Blacktiger blacktiger) {
        Log.e("xxxxxx", "insert");
        blacktigerRepository.insertBlacktiger(blacktiger);
    }

    public LiveData<List<Category>> getAllCategoriesLive() {
        return categoryRepository.getAllCategoriesLive();
    }

    public ObservableField<String> getDateText() {
        return mDateText;
    }

    public ObservableField<String> getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType.set(mType);
    }

    public void setmCate2(String mCate2) {
        this.mCate2.set(mCate2);
    }


    public ObservableField<String> getAmountText() {
        return mAmountText;
    }

    public void setmAmountTextClear() {
        this.mAmountText.set("");
    }

    public ObservableField<String> getDesc() {
        if (mDesc.get() == null)
            mDesc.set("");
        return mDesc;
    }

    public ObservableField<String> getCate2() {
        if (mCate2.get() == null)
            mCate2.set("");
        return mCate2;
    }

    //pager data
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "index:" + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setmDateText(String mDateText) {
        this.mDateText.set(mDateText);
    }

    public void setmDesc(String mDesc) {
        this.mDesc.set(mDesc);
    }

    public void setmAmountText(String mAmountText) {
        this.mAmountText.set(mAmountText);
    }

    public Blacktiger getBlacktigerEdit() {
        return blacktigerEdit;
    }

    public void setBlacktiger(Blacktiger blacktiger) {
        this.blacktigerEdit = blacktiger;
        this.setType(blacktiger.getCategory());
        this.setmAmountText(mAmountFormat.format(blacktiger.getAmount()));
        this.setmDesc(blacktiger.getNote());
        this.setmDateText(DateToLongUtils.longToDateAdd(blacktiger.getTime()));
        this.setIconId(blacktiger.getIcon());
        this.setmAccount(blacktiger.getAccount());
        this.setmMembers(blacktiger.getMembers());
    }



    public ObservableField<String> getAccount() {
        if (mAccount.get() == null)
            mAccount.set("");
        return mAccount;
    }

    public void setmAccount(String mAccount) {
        this.mAccount.set(mAccount);
    }

    public  void setmMembers(String mMembers) {
        this.mMembers.set(mMembers);
    }

    public ObservableField<String> getMembers() {
        if (mMembers.get() == null)
            mMembers.set("");
        return mMembers;
    }
}