package com.desmond.citypicker.views.pull2refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.desmond.citypicker.tools.PxConvertUtil;


/**
 * The type Recycler view divider.
 *
 * @Todo
 * @Author desmond
 * @Date 2016 /12/12
 * @Pacakge com.chinasoft.widget.recyclerview
 */
public class RecyclerViewDivider extends RecyclerView.ItemDecoration
{
    /**
     * The M paint.
     */
    private Paint mPaint;
    /**
     * The M divider.
     */
    private Drawable mDivider;
    /**
     * The M divider height.
     */
    private int mDividerHeight = 2;//分割线高度，默认为1px
    /**
     * The M orientation.
     */
    private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL

    /**
     * The Span count.
     */
    private int spanCount;
    /**
     * The constant ATTRS.
     */
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * The constant VERTICAL.
     */
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    /**
     * The constant HORIZONTAL.
     */
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    /**
     * The constant BOTH.
     */
    public static final int BOTH = 3;

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context     the context
     * @param orientation 列表方向
     * @param spanCount   the span count
     */
    public RecyclerViewDivider(Context context, int orientation, int spanCount)
    {
        if (orientation != VERTICAL && orientation != HORIZONTAL && orientation != BOTH)
        {
            throw new IllegalArgumentException("请输入正确的参数！");
        }

        mOrientation = orientation;
        this.spanCount = spanCount;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
//        mDivider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 自定义分割线
     *
     * @param context     the context
     * @param orientation 列表方向
     * @param drawableId  分割线图片
     * @param spanCount   the span count
     */
    public RecyclerViewDivider(Context context, int orientation, int drawableId, int spanCount)
    {
        this(context, orientation, spanCount);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     *
     * @param context       the context
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     * @param spanCount     the span count
     */
    public RecyclerViewDivider(Context context, int orientation, int dividerHeight, int dividerColor, int spanCount)
    {
        this(context, orientation, spanCount);
        mDividerHeight = PxConvertUtil.dip2px(context,dividerHeight);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(dividerColor);
    }

    /**
     * Gets item offsets.
     *
     * @param outRect the out rect
     * @param view    the view
     * @param parent  the parent
     * @param state   the state
     */
//获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        SimpleBaseAdapter adapter = (SimpleBaseAdapter) parent.getAdapter();
        int position = parent.getChildLayoutPosition(view);
        if (adapter.isHeaderViewPosition(position) || adapter.isFooterViewPosition(position))
        {
            outRect.set(0, 0, 0, 0);
            return;
        }
        position = position - adapter.getHeaderSize();
        int right, bottom;
        switch (mOrientation)
        {
            case VERTICAL:
                if (position + 1 == adapter.getRealItemCount())
                    bottom = 0;
                else
                    bottom = mDividerHeight;
                outRect.set(0, 0, 0, bottom);
                break;
            case HORIZONTAL:
                if (position % spanCount == 1)
                    right = 0;
                else
                    right = mDividerHeight;
                outRect.set(0, 0, right, 0);
                break;
            case BOTH:
                if (position % spanCount == 1)
                    right = 0;
                else
                    right = mDividerHeight;

                if (position + 1 == adapter.getRealItemCount() || (position % spanCount == 0 && position + 2 == adapter.getRealItemCount()))
                    bottom = 0;
                else
                    bottom = mDividerHeight;
                outRect.set(0, 0, right, bottom);
                break;
        }
    }

    /**
     * On draw.
     *
     * @param c      the c
     * @param parent the parent
     * @param state  the state
     */
//绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        super.onDraw(c, parent, state);
        switch (mOrientation)
        {
            case VERTICAL:
                drawVertical(c, parent);
                break;
            case HORIZONTAL:
                drawHorizontal(c, parent);
                break;
            case BOTH:
                drawBoth(c, parent);
                break;
        }
    }


    /**
     * Draw horizontal.
     *
     * @param canvas the canvas
     * @param parent the parent
     */
//绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent)
    {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++)
        {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mPaint != null)
            {
                canvas.drawRect(left, top, right, bottom, mPaint);
                continue;
            }
            if (mDivider != null)
            {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }

        }
    }

    /**
     * Draw vertical.
     *
     * @param canvas the canvas
     * @param parent the parent
     */
//绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent)
    {
        int top = parent.getPaddingTop();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++)
        {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            final int bottom = child.getBottom();
            if (mPaint != null)
            {
                canvas.drawRect(left, top, right, bottom, mPaint);
                continue;
            }
            if (mDivider != null)
            {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
    }

    /**
     * Draw both.
     *
     * @param canvas the canvas
     * @param parent the parent
     */
    private void drawBoth(Canvas canvas, RecyclerView parent)
    {
        int left = 0;
        int right = 0;
        int bottom = 0;
        int top = 0;
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++)
        {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

            //横线
            left = child.getLeft();
            top = child.getBottom() + layoutParams.bottomMargin;
            right = child.getRight();
            bottom = top + mDividerHeight;
            canvas.drawRect(left, top, right, bottom, mPaint);

            //竖线
            left = child.getRight() + layoutParams.rightMargin;
            top = child.getTop();
            right = left + mDividerHeight;
            bottom = child.getBottom() + mDividerHeight;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

}
