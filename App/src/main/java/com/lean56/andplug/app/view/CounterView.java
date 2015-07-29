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
    void createInternalView();

    /**
     * nyan
     */
    void nyan();

    /**
     * set the count number of the counter view
     * @param count
     */
    void setCount(int count);

    /**
     * get the count number
     * @return
     */
    int getCount();

    /**
     * is the view selected
     * @return
     */
    boolean isSelected();

    /**
     * set selection of the view
     * @param selected
     */
    void setSelected(boolean selected);

}
