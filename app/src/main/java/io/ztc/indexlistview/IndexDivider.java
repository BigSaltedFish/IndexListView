package io.ztc.indexlistview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import io.ztc.indexlistview.IndexAdapterUtils;
import io.ztc.indexlistview.R;

/**
 * 分割线工具
 */
public class IndexDivider extends RecyclerView.ItemDecoration {
    private Drawable mDividerDrawable;
    private int mMarginLeft;
    private int mMarginRight;
    private int mOrientation = LinearLayout.VERTICAL;
    private int mStartSkipCount = 1;
    private int mEndSkipCount = 0;
    private int mSize = 1;
    private Delegate mDelegate;

    private IndexDivider(@DrawableRes int drawableResId) {
        mDividerDrawable = ContextCompat.getDrawable(IndexAdapterUtils.getApp(), drawableResId);
        assert mDividerDrawable != null;
        mSize = Math.min(mDividerDrawable.getIntrinsicHeight(), mDividerDrawable.getIntrinsicWidth());
    }

    /**
     * 自定义 drawable 资源分隔线
     *
     * @param drawableResId
     * @return
     */
    public static IndexDivider newDrawableDivider(@DrawableRes int drawableResId) {
        return new IndexDivider(drawableResId);
    }

    /**
     * 默认的 shape 资源分隔线
     *
     * @return
     */
    public static IndexDivider newShapeDivider() {
        return new IndexDivider(R.drawable.index_shape);
    }

    /**
     * 设置代理
     *
     * @param delegate
     * @return
     */
    public IndexDivider setDelegate(Delegate delegate) {
        mDelegate = delegate;
        return this;
    }

    /**
     * 设置左边距和右边距
     *
     * @param bothMarginPx 单位为 px
     * @return
     */
    public IndexDivider setBothMarginPx(int bothMarginPx) {
        mMarginLeft = bothMarginPx;
        mMarginRight = mMarginLeft;
        return this;
    }



    /**
     * 设置左边距
     *
     * @param marginLeftDp 单位为 dp
     * @return
     */
    public IndexDivider setMarginLeftDp(int marginLeftDp) {
        mMarginLeft = IndexAdapterUtils.dp2px(marginLeftDp);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeftPx 单位为 px
     * @return
     */
    public IndexDivider setMarginLeftPx(int marginLeftPx) {
        mMarginLeft = marginLeftPx;
        return this;
    }



    /**
     * 设置右边距
     *
     * @param marginRightDp 单位为 dp
     * @return
     */
    public IndexDivider setMarginRightDp(int marginRightDp) {
        mMarginRight = IndexAdapterUtils.dp2px(marginRightDp);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param marginRightPx 单位为 px
     * @return
     */
    public IndexDivider setMarginRightPx(int marginRightPx) {
        mMarginRight = marginRightPx;
        return this;
    }

    /**
     * 设置分隔线颜色
     *
     * @param color
     * @param isSrcTop
     * @return
     */
    public IndexDivider setColor(@ColorInt int color, boolean isSrcTop) {
        mDividerDrawable.setColorFilter(color, isSrcTop ? PorterDuff.Mode.SRC_ATOP : PorterDuff.Mode.SRC);
        return this;
    }

    /**
     * 设置为水平方向
     *
     * @return
     */
    public IndexDivider setHorizontal() {
        mOrientation = LinearLayout.HORIZONTAL;
        return this;
    }

    /**
     * 旋转分隔线，仅用于分隔线为 Bitmap 时。应用场景：UI 给了一个水平分隔线，恰巧项目里需要一条一模一样的竖直分隔线
     *
     * @return
     */
    public IndexDivider rotateDivider() {
        if (mDividerDrawable != null && mDividerDrawable instanceof BitmapDrawable) {
            mDividerDrawable = IndexAdapterUtils.rotateBitmap(((BitmapDrawable) mDividerDrawable).getBitmap());
        }
        return this;
    }

    /**
     * 跳过开始的条数。默认值为 1，不绘制第一个 item 顶部的分隔线
     *
     * @param startSkipCount
     * @return
     */
    public IndexDivider setStartSkipCount(@IntRange(from = 0) int startSkipCount) {
        mStartSkipCount = startSkipCount;
        if (mStartSkipCount < 0) {
            mStartSkipCount = 0;
        }
        return this;
    }

    /**
     * 跳过末尾的条数
     *
     * @param endSkipCount
     * @return
     */
    public IndexDivider setEndSkipCount(@IntRange(from = 0) int endSkipCount) {
        mEndSkipCount = endSkipCount;
        if (mEndSkipCount < 0) {
            mEndSkipCount = 0;
        }
        return this;
    }

    /**
     * 设置分割线尺寸
     *
     * @param sizeDp 单位为 dp
     * @return
     */
    public IndexDivider setSizeDp(int sizeDp) {
        mSize = IndexAdapterUtils.dp2px(sizeDp);
        return this;
    }

    /**
     * 设置分割线尺寸
     *
     * @param sizePx 单位为 px
     * @return
     */
    public IndexDivider setSizePx(int sizePx) {
        mSize = sizePx;
        return this;
    }

    /**
     * 获取左边距
     *
     * @return
     */
    public int getMarginLeft() {
        return mMarginLeft;
    }

    /**
     * 获取右边距
     *
     * @return
     */
    public int getMarginRight() {
        return mMarginRight;
    }

    private IndexHFViewAdapter getHeaderAndFooterAdapter(RecyclerView parent) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter instanceof IndexHFViewAdapter) {
            return (IndexHFViewAdapter) adapter;
        } else {
            return null;
        }
    }

    private boolean isNeedSkip(int childAdapterPosition, IndexHFViewAdapter headerAndFooterAdapter, int realChildAdapterPosition, int realItemCount) {
        if (headerAndFooterAdapter != null && headerAndFooterAdapter.isHeaderViewOrFooterView(childAdapterPosition)) {
            // 是 header 和 footer 时跳过
            return true;
        }

        int lastPosition = realItemCount - 1;
        // 跳过最后 mEndSkipCount 个
        if (realChildAdapterPosition > lastPosition - mEndSkipCount) {
            return true;
        }

        // 跳过前 mStartSkipCount 个
        // 1
        if (realChildAdapterPosition < mStartSkipCount) {
            return true;
        }

        if (mDelegate != null) {
            return mDelegate.isNeedSkip(realChildAdapterPosition, realItemCount);
        }

        // 默认不跳过
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return;
        }

        int childAdapterPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        int realChildAdapterPosition = childAdapterPosition;
        int realItemCount = itemCount;

        IndexHFViewAdapter headerAndFooterAdapter = getHeaderAndFooterAdapter(parent);
        if (headerAndFooterAdapter != null) {
            // 转换成真实 item 的索引
            realChildAdapterPosition = headerAndFooterAdapter.getRealItemPosition(childAdapterPosition);
            // 转换成真实 item 的总数
            realItemCount = headerAndFooterAdapter.getRealItemCount();
        }

        if (isNeedSkip(childAdapterPosition, headerAndFooterAdapter, realChildAdapterPosition, realItemCount)) {
            outRect.set(0, 0, 0, 0);
        } else {
            if (mDelegate != null && mDelegate.isNeedCustom(realChildAdapterPosition, realItemCount)) {
                mDelegate.getItemOffsets(this, realChildAdapterPosition, realItemCount, outRect);
            } else {
                if (mOrientation == LinearLayout.VERTICAL) {
                    getVerticalItemOffsets(outRect);
                } else {
                    outRect.set(mSize, 0, 0, 0);
                }
            }
        }
    }

    public void getVerticalItemOffsets(Rect outRect) {
        outRect.set(0, mSize, 0, 0);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        handleDraw(canvas, parent, true);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        handleDraw(canvas, parent, false);
    }

    private void handleDraw(Canvas canvas, RecyclerView parent, boolean isDrawOver) {
        if (parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return;
        }

        int itemCount = parent.getAdapter().getItemCount();
        IndexHFViewAdapter headerAndFooterAdapter = getHeaderAndFooterAdapter(parent);
        // 除去 header 和 footer 后中间部分真实 item 的总数
        int realItemCount = itemCount;
        if (headerAndFooterAdapter != null) {
            // 转换成真实 item 的总数
            realItemCount = headerAndFooterAdapter.getRealItemCount();
        }

        if (mOrientation == LinearLayout.VERTICAL) {
            drawVertical(canvas, parent, headerAndFooterAdapter, itemCount, realItemCount, isDrawOver);
        } else {
            drawHorizontal(canvas, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent, IndexHFViewAdapter headerAndFooterAdapter, int itemCount, int realItemCount, boolean
            isDrawOver) {
        int dividerLeft = parent.getPaddingLeft() + mMarginLeft;
        int dividerRight = parent.getWidth() - parent.getPaddingRight() - mMarginRight;
        View itemView;
        RecyclerView.LayoutParams itemLayoutParams;
        int realChildAdapterPosition;

        for (int childPosition = 0; childPosition < itemCount; childPosition++) {
            itemView = parent.getChildAt(childPosition);
            if (itemView == null || itemView.getLayoutParams() == null) {
                continue;
            }

            int childAdapterPosition = parent.getChildAdapterPosition(itemView);
            realChildAdapterPosition = getRealChildAdapterPosition(childAdapterPosition, headerAndFooterAdapter);

            if (isNeedSkip(childAdapterPosition, headerAndFooterAdapter, realChildAdapterPosition, realItemCount)) {
                continue;
            }

            itemLayoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            int dividerBottom = itemView.getTop() - itemLayoutParams.topMargin;

            if (mDelegate != null && mDelegate.isNeedCustom(realChildAdapterPosition, realItemCount)) {
                if (isDrawOver) {
                    mDelegate.drawOverVertical(this, canvas, dividerLeft, dividerRight, dividerBottom, realChildAdapterPosition, realItemCount);
                } else {
                    mDelegate.drawVertical(this, canvas, dividerLeft, dividerRight, dividerBottom, realChildAdapterPosition, realItemCount);
                }
            } else if (!isDrawOver) {
                // 不自定义，并且不是 drawOver 时，绘制分割线
                drawVerticalDivider(canvas, dividerLeft, dividerRight, dividerBottom);
            }
        }
    }

    /**
     * 绘制分割线
     *
     * @param canvas
     * @param dividerLeft
     * @param dividerRight
     * @param itemTop
     */
    public void drawVerticalDivider(Canvas canvas, int dividerLeft, int dividerRight, int itemTop) {
        int dividerBottom = itemTop;
        int dividerTop = dividerBottom - mSize;
        mDividerDrawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
        mDividerDrawable.draw(canvas);
    }

    private int getRealChildAdapterPosition(int childAdapterPosition, IndexHFViewAdapter headerAndFooterAdapter) {
        if (headerAndFooterAdapter != null) {
            // 转换成真实 item 的索引
            return headerAndFooterAdapter.getRealItemPosition(childAdapterPosition);
        }
        return childAdapterPosition;
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
    }

    public interface Delegate {
        boolean isNeedSkip(int position, int itemCount);

        boolean isNeedCustom(int position, int itemCount);

        void getItemOffsets(IndexDivider divider, int position, int itemCount, Rect outRect);

        void drawVertical(IndexDivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount);

        void drawOverVertical(IndexDivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount);
    }

    public static class SimpleDelegate implements Delegate {
        protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public SimpleDelegate() {
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);
            initAttr();
        }

        protected void initAttr() {
        }

        @Override
        public boolean isNeedSkip(int position, int itemCount) {
            return false;
        }

        @Override
        public boolean isNeedCustom(int position, int itemCount) {
            return false;
        }

        @Override
        public void getItemOffsets(IndexDivider divider, int position, int itemCount, Rect outRect) {
        }

        @Override
        public void drawVertical(IndexDivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
        }

        @Override
        public void drawOverVertical(IndexDivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
        }
    }

    /**
     * 继承该类实现自定义悬浮分类样式
     */
    public static abstract class StickyDelegate extends SimpleDelegate {
        protected int mCategoryBackgroundColor;
        protected int mCategoryTextColor;
        protected int mCategoryPaddingLeft;
        protected int mCategoryTextSize;
        protected int mCategoryHeight;
        protected float mCategoryTextOffset;

        @Override
        protected void initAttr() {
            mCategoryBackgroundColor = Color.parseColor("#F2F2F2");
            mCategoryTextColor = Color.parseColor("#848484");
            mCategoryPaddingLeft = IndexAdapterUtils.dp2px(16);
            mCategoryTextSize = IndexAdapterUtils.sp2px(14);
            mCategoryHeight = IndexAdapterUtils.dp2px(32);

            initCategoryAttr();

            mPaint.setStyle(Paint.Style.FILL);

            calculateCategoryTextOffset();
        }

        /**
         * 计算文字底部偏移量
         */
        public void calculateCategoryTextOffset() {
            mPaint.setTextSize(mCategoryTextSize);

            Rect rect = new Rect();
            mPaint.getTextBounds("某某", 0, 2, rect);
            int textHeight = rect.height();
            mCategoryTextOffset = (mCategoryHeight - textHeight) / 2.0f;
        }

        /**
         * 需要自定义分类属性时重写该方法
         */
        protected void initCategoryAttr() {
        }

        @Override
        public boolean isNeedCustom(int position, int itemCount) {
            // 每一项都自定义
            return true;
        }

        @Override
        public void getItemOffsets(IndexDivider divider, int position, int itemCount, Rect outRect) {
            if (isCategoryFistItem(position)) {
                // 如果是分类则设置高度为分类高度
                outRect.set(0, mCategoryHeight, 0, 0);
            } else {
                // 如果不是分类则调用 BGADivider 的 getVerticalItemOffsets 来设置 item 高度
                divider.getVerticalItemOffsets(outRect);
            }
        }

        @Override
        public void drawVertical(IndexDivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
            if (isCategoryFistItem(position)) {
                if (position == getFirstVisibleItemPosition() && itemCount > 1) {
                    // 避免悬浮分类透明时重影
                    return;
                }
                // 是分类下的第一个条目，绘制分类
                drawCategory(divider, canvas, dividerLeft, dividerRight, dividerBottom, getCategoryName(position));
            } else {
                // 不是分类下的第一个条目，绘制分割线
                divider.drawVerticalDivider(canvas, dividerLeft, dividerRight, dividerBottom);
            }
        }

        @Override
        public void drawOverVertical(IndexDivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
            if (position == getFirstVisibleItemPosition() + 1) {
                // 绘制悬浮分类
                int suspensionBottom = mCategoryHeight;
                int offset = mCategoryHeight * 2 - dividerBottom;
                if (offset > 0 && isCategoryFistItem(position)) {
                    suspensionBottom -= offset;
                }
                drawOverCategory(divider, canvas, dividerLeft, dividerRight, suspensionBottom, getCategoryName(getFirstVisibleItemPosition()));
            }
        }

        /**
         * 指定索引位置是否是其对应分类下的第一个条目
         *
         * @param position
         * @return
         */
        protected abstract boolean isCategoryFistItem(int position);

        /**
         * 获取指定索引位置的分类名称
         *
         * @param position
         * @return
         */
        protected abstract String getCategoryName(int position);

        /**
         * 获取第一个可见条目的索引位置
         *
         * @return
         */
        protected abstract int getFirstVisibleItemPosition();

        /**
         * 绘制悬浮分类
         */
        protected void drawOverCategory(IndexDivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, String category) {
            drawCategory(divider, canvas, dividerLeft, dividerRight, dividerBottom, category);
        }

        /**
         * 绘制普通分类
         */
        protected void drawCategory(IndexDivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, String category) {
            // 绘制背景
            mPaint.setColor(mCategoryBackgroundColor);
            canvas.drawRect(dividerLeft - divider.getMarginLeft(), dividerBottom - mCategoryHeight, dividerRight + divider.getMarginRight(), dividerBottom,
                    mPaint);

            // 绘制文字
            mPaint.setColor(mCategoryTextColor);
            canvas.drawText(category, 0, category.length(), mCategoryPaddingLeft, dividerBottom - mCategoryTextOffset, mPaint);
        }

        /**
         * 获取分类高度
         *
         * @return
         */
        public int getCategoryHeight() {
            return mCategoryHeight;
        }
    }
}
