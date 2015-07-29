package com.lean56.andplug.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import com.lean56.andplug.app.fragment.IndexFragment;
import com.lean56.andplug.adapter.SaveFragmentPagerAdapter;


/**
 * FeatureSubAdapter
 *
 * @author Charles
 */
public class FeatureSubAdapter extends SaveFragmentPagerAdapter {

    private final static String[] titleArray = new String[]{"sub1", "sub2", "sub3"};
    private final static Fragment[] fragmentArray = new Fragment[] {
            new IndexFragment(),
            new IndexFragment(),
            new IndexFragment()
    };

    /**
     * Create pager adapter
     *
     * @param fm
     */
    public FeatureSubAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position < titleArray.length ? titleArray[position] : null;
    }

    @Override
    public int getCount() {
        return fragmentArray.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    /*@Override
    public Object instantiateItem(ViewGroup container, int position) {
        ProjectListFragment fragment = (ProjectListFragment) super.instantiateItem(container, position);
        fragment.setData(getChildData(position), requestOk);

        return fragment;
    }*/

    @Override
    public Fragment getItem(int position) {
        Log.d("", "all p " + position);
        /*ProjectListFragment fragment = new ProjectListFragment_();
        Bundle bundle = new Bundle();

        bundle.putSerializable("mData", getChildData(position));
        fragment.setArguments(bundle);

        saveFragment(fragment);

        return fragment;*/
        return position < fragmentArray.length ? fragmentArray[position] : null;
    }

    /*private ArrayList<ProjectObject> getChildData(int position) {
        ArrayList<ProjectObject> childData = new ArrayList<ProjectObject>();

        switch (position) {
            case 1:
                stuffChildData(childData, "member");
                break;
            case 2:
                stuffChildData(childData, "owner");
                break;
            default:
                childData.addAll(mData);
                break;
        }

        return childData;
    }

    void stuffChildData(ArrayList<ProjectObject> child, String type) {
        for (int i = 0; i < mData.size(); ++i) {
            ProjectObject item = mData.get(i);
            if (item.current_user_role.equals(type)) {
                child.add(item);
            }
        }
    }*/
}