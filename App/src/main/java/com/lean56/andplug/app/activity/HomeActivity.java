package com.lean56.andplug.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.lean56.andplug.app.R;
import com.lean56.andplug.app.adapter.HomeFragmentAdapter;
import com.lean56.andplug.comlib.activity.TabPagerActivity;

public class HomeActivity extends TabPagerActivity<HomeFragmentAdapter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureTabPager();
    }

    // [+] TabPagerActivity
    @Override
    protected HomeFragmentAdapter createAdapter() {
        return new HomeFragmentAdapter(this);
    }

    @Override
    protected int getIcon(int position) {
        switch (position) {
            case 0:
                return R.drawable.tab_index;
            case 1:
                return R.drawable.tab_feature;
            case 2:
                return R.drawable.tab_msg;
            case 3:
                return R.drawable.tab_profile;
            default:
                return super.getIcon(position);
        }
    }

    @Override
    protected void setCurrentItem(int position) {
        super.setCurrentItem(position);
        switch (position) {
            case 0:
                setTitle("1");
                break;
            case 1:
                setTitle("2");
                break;
            case 2:
                setTitle("3");
                break;
            case 3:
                setTitle("4");
                break;
        }
    }
    // [-] TabPagerActivity

    @Override
    protected int getColorPrimaryDark() {
        return android.R.color.primary_text_dark;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
