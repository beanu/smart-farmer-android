package io.fchain.ui.widget.recycler;

import androidx.recyclerview.widget.ItemTouchHelper;

/**
 * 处理item拖拽排序
 *
 * @author liuhuan
 */
public class YolandaItemTouchHelper extends ItemTouchHelper {

    private Callback mCallback;

    public YolandaItemTouchHelper(Callback callback) {
        super(callback);
        mCallback = callback;
    }

    public Callback getCallback() {
        return mCallback;
    }
}