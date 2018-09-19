package io.ztc.indexlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 索引列表
 */
public class SideBar extends View {


    private Context context;
    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };
    private int isCheck = -1;//选取
    private Paint paint;
    private RelativeLayout mTextDialog;
    private TextView mTextDialog_text;
    private OnTouchLetterChangedListener listener;

    /**
     * 为索引栏显示首字母提示
     * @param mTextDialog
     */
    public void setTextView(RelativeLayout mTextDialog,TextView mTextDialog_text){
        this.mTextDialog = mTextDialog;
        this.mTextDialog_text = mTextDialog_text;
    }

    public SideBar(Context context) {
        super(context);
        this.context = context;
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取焦点改变背景颜色
        int height = getHeight();//对应高度
        int width = getWidth();//对应宽度
        int singleHeight = height/b.length;//每个字母占高
        /**
         * 选中
         */
        for (int i =  0; i<b.length;i++){
            //初始化画笔
            paint = new Paint();
            paint.setColor(Color.rgb(33,65,98));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
            paint.setTextSize(textSize);

            if (i==isCheck){
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            float xPos = width/2 - paint.measureText(b[i])/2;
            float yPos = singleHeight * i +singleHeight;
            canvas.drawText(b[i],xPos,yPos,paint);
            paint.reset();//重置画笔
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldCheck = isCheck;
        //点击坐标所占总高度的比例乘以数组的长度等于点击B中的index
        final int c = (int) (y/getHeight()*b.length);
        switch (action){
            case MotionEvent.ACTION_UP:
                setBackgroundColor(0);
                isCheck = -1;
                invalidate();
                if (mTextDialog!=null){
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                setBackgroundColor(0);
                if (oldCheck != c){
                    if (c >= 0&& c<b.length){
                        if (listener!=null){
                            listener.onTouchLetterChanged(b[c]);
                        }
                        if (mTextDialog_text != null&& mTextDialog != null){
                            mTextDialog_text.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                    }
                    isCheck = c;
                    invalidate();
                }
        }
        return true;
    }

    /**
     * 对外提供设置方法
     * @param listener
     */
    public void setOnTouchLetterChangedListener(OnTouchLetterChangedListener listener){
        this.listener = listener;
    }

    /**
     * 对外接口
     */
    public interface OnTouchLetterChangedListener{
        void onTouchLetterChanged(String s);
    }
}
