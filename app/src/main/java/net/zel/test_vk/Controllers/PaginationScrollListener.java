package net.zel.test_vk.Controllers;

import android.widget.AbsListView;

import net.zel.test_vk.Utils.Constants;

/**
 * Created by alexey on 03.07.14.
 */
public abstract class PaginationScrollListener implements AbsListView.OnScrollListener {

    private Pagination pagination = new Pagination();

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount > 0) {
            final int lastItem = firstVisibleItem + visibleItemCount;
            if ((lastItem == totalItemCount) && (pagination.isPagination()) && (firstVisibleItem != 0)) {
                pagination.stop();
                startPagination();
            }
        }
    }

    public void refresh() {
        pagination.offsetPagination = 0;
        pagination.isPagination = true;
    }

    public abstract void startPagination();

    public int getOffset() {
        return pagination.offsetPagination;
    }

    public void up() {
        pagination.up();
    }

    private final static class Pagination {

        private boolean isPagination = true;

        private int offsetPagination = 0;

        private void up() {
            isPagination = true;
            offsetPagination = offsetPagination + Constants.COUNT_PAGINATION;
        }

        private void stop() {
            isPagination = false;
        }

        private boolean isPagination() {
            return isPagination;
        }


    }
}
