package android.my.com.myandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    private FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    Fragment5 fragment5;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_1:
                    setMyFragment(1);
                    break;
                case R.id.navigation_2:
                    setMyFragment(2);
                    break;
                case R.id.navigation_3:
                    setMyFragment(3);
                    break;
                case R.id.navigation_4:
                    setMyFragment(4);
                    break;
                case R.id.navigation_5:
                    setMyFragment(5);
                    break;
            }
            // true 会显示这个Item被选中的效果 false 则不会
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation =  findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);//去除动画
        navigation.setSelectedItemId(navigation.getMenu().getItem(0).getItemId());//默认选择
        setMyFragment(1);//默认选择

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        //这里就是获取所添加的每一个Tab(或者叫menu)，
        View tab = menuView.getChildAt(4);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
        //加载我们的角标View，新创建的一个布局
        View badge = LayoutInflater.from(this).inflate(R.layout.message_number, menuView, false);
        //添加到Tab上
        itemView.addView(badge);

        TextView count = badge.findViewById(R.id.tv_msg_count);
        count.setText(String.valueOf(10));

        //如果没有消息，不需要显示的时候那只需要将它隐藏即可
//        count.setVisibility(View.GONE);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {

        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
        if (fragment3 != null) {
            transaction.hide(fragment3);
        }
        if (fragment4 != null) {
            transaction.hide(fragment4);
        }
        if (fragment5 != null) {
            transaction.hide(fragment5);
        }
    }

    private void setMyFragment(int Index){
        transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch (Index){
            case 1:
                if (fragment1 == null) {
                    fragment1 = new Fragment1();
                    transaction.add(R.id.framelayout_main, fragment1);
                } else {
                    transaction.show(fragment1);
                }
                break;
            case 2:
                if (fragment2 == null) {
                    fragment2 = new Fragment2();
                    transaction.add(R.id.framelayout_main, fragment2);
                } else {
                    transaction.show(fragment2);
                }
                break;
            case 3:
                if (fragment3 == null) {
                    fragment3 = new Fragment3();
                    transaction.add(R.id.framelayout_main, fragment3);
                } else {
                    transaction.show(fragment3);
                }
                break;
            case 4:
                if (fragment4 == null) {
                    fragment4 = new Fragment4();
                    transaction.add(R.id.framelayout_main, fragment4);
                } else {
                    transaction.show(fragment4);
                }
                break;
            case 5:
                if (fragment5 == null) {
                    fragment5 = new Fragment5();
                    transaction.add(R.id.framelayout_main, fragment5);
                } else {
                    transaction.show(fragment5);
                }
                break;
        }
        transaction.commit();
    }


    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        //获取子View BottomNavigationMenuView的对象
        BottomNavigationMenuView menuView;
        menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            //设置私有成员变量mShiftingMode可以修改
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //去除shift效果
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "没有mShiftingMode这个成员变量", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "无法修改mShiftingMode的值", e);
        }
    }
}
