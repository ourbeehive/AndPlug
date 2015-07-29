package com.lean56.andplug.app.view;

/**
 * CounterView
 *
 * @author liwuhuo <www.liwushuo.com>
 * @author Charles
 */
public interface CounterView {

    /**
     * create internal view
     */
    public abstract void createInternalView();

    /**
     * nyan
     */
    public abstract void nyan();

    /**
     * set the count number of the counter view
     * @param count
     */
    public abstract void setCount(int count);

    /**
     * get the count number
     * @return
     */
    public abstract int getCount();

    /**
     * is the view selected
     * @return
     */
    public abstract boolean isSelected();

    /**
     * set selection of the view
     * @param selected
     */
    public abstract void setSelected(boolean selected);



}
