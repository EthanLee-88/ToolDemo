package com.ethan.tooldemo.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ethan.tooldemo.R;
import com.ethan.tooldemo.util.SelectorUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ethan on 18/10/09.
 */
public class CustomRadioGroup extends RadioGroup {
    private List<RowView> rowViews;//存放每行radioBotton的集合
    private int horizontalSpacing = 20;//默认水平间距
    private int verticalSpacing = 10;//默认垂直间距
    private OnclickListener listener;
    private List<String> chooseData = new ArrayList();
    private GradientDrawable normalDraw1, pressDraw1;
    private int pressColor = Color.RED;
    private int normalColor = Color.GRAY;
    private int strokeWidth = 1;
    private int textSize = 18;

    public void setListener(OnclickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置按下选中的返回接口，其中index为返回内容name所在数组的下标
     *
     * @return
     */
    public interface OnclickListener {
        void OnText(View viewGroup, View view, String name, int index);
    }

    public CustomRadioGroup(Context context) {
        this(context, null);
    }

    public CustomRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        rowViews = new ArrayList<>();
    }

    /**
     * 设置HorizontalSpacing(设置两个按钮之间的水平间距（单位dp）)
     *
     * @param horizontalSpacing_dp
     * @return
     */
    public CustomRadioGroup setHorizontalSpacing(int horizontalSpacing_dp) {
        this.horizontalSpacing = (int) dpToPx(horizontalSpacing_dp);
        return this;
    }

    /**
     * 设置VerticalSpacing(当所有组件分两行时，设置两行按钮之间的垂直间距（单位dp）)
     *
     * @param verticalSpacing_dp
     * @return
     */
    public CustomRadioGroup setVerticalSpacing(int verticalSpacing_dp) {
        this.verticalSpacing = (int) dpToPx(verticalSpacing_dp);
        return this;
    }

    /**
     * 设置ButtonBackgoundColor(设置按钮背景颜色，包括选中的颜色和未选中的颜色)
     *
     * @param pressColor 按钮按下的颜色
     * @param normalColor     按钮正常颜色
     * @return
     */
    public CustomRadioGroup setButtonBackgroundColor(int pressColor, int normalColor) {
        this.normalColor = getResources().getColor(normalColor);
        this.pressColor = getResources().getColor(pressColor);
        return this;
    }

    /**
     * 设置setStroke(设置按钮边框宽度和颜色)
     *
     * @param Width 边框宽度
     * @return
     */
    public CustomRadioGroup setStroke(int Width) {
        this.strokeWidth = Width;
        return this;
    }

    public void setTextSize(int sp){
        textSize = sp;
        sp = (int) spToPx(sp);
        for (int i = 0; i < getChildCount(); i ++){
            ((RadioButton)getChildAt(i)).setTextSize(sp);
        }
    }

    /**
     * * 设置SelectData(设置控件的原始数据，其中宽度和高度必须设置，
     * * 圆形控件的半径由宽和高算出，这是初始化组件时必须调用的一个方法)
     *
     * @param dataName     dataName
     * @param buttonWidth  buttonWidth
     * @param buttonHeight buttonHeight
     */
    public void setStrokeBackground(String[] dataName, int buttonWidth, int buttonHeight) {
        chooseData.clear();
        chooseData.addAll(Arrays.asList(dataName));
        int getOvalRadio = (Math.max(buttonWidth, buttonHeight)) / 2;
        for (int i = 0; i < dataName.length; i++) {
            RadioButton radioButton = getRadioButton();
            if ((normalDraw1 == null) || (pressDraw1 == null)){
                normalDraw1 = getDrawableForConorAroundStroke(this.strokeWidth, this.normalColor,
                        buttonWidth, buttonHeight);
                pressDraw1 = getDrawableForConorAroundStroke(this.strokeWidth, this.pressColor,
                        buttonWidth, buttonHeight);
            }
            StateListDrawable mStateListDrawable = getSelector(normalDraw1, pressDraw1);
            radioButton.setBackground(mStateListDrawable);
            setVerticalSpacing(getOvalRadio / 2);
            setHorizontalSpacing(getOvalRadio / 2);
            radioButton.setText(dataName[i]);
            addView(radioButton);
        }
    }

    /**
     * 生成Selector
     * @param normalDraw normalDraw
     * @param pressedDraw pressedDraw
     * @return return
     */

    public static StateListDrawable getSelector(Drawable normalDraw, Drawable pressedDraw) {
        StateListDrawable stateListDrawable  = new StateListDrawable();
        stateListDrawable.addState(new int[]{ android.R.attr.state_checked }, pressedDraw);
        stateListDrawable.addState(new int[]{ }, normalDraw);
        return stateListDrawable ;
    }

    /**
     * stroke
     *
     * @param strokeWidth strokeWidth
     * @param strokeColor strokeColor
     * @return
     */
    private GradientDrawable getDrawableForConorAroundStroke(int strokeWidth, int strokeColor, int width, int height) {
        strokeWidth = (int) dpToPx(strokeWidth);
        float[] circleAngleArr = {50, 50, 50, 50,
                50, 50, 50, 50};
        for (int i = 0; i < circleAngleArr.length; i++){
            circleAngleArr[i] = dpToPx(circleAngleArr[i]);
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setSize((int) dpToPx(width), (int) dpToPx(height));
        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setStroke(strokeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }

    private RadioButton getRadioButton() {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setButtonDrawable(null);
        radioButton.setChecked(false);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setTextSize(spToPx(textSize));
        //
        radioButton.setTextColor(new ColorStateList(new int[][]{{android.R.attr.state_checked}, {-android.R.attr.state_checked}},
                new int[]{pressColor, normalColor}));
        return radioButton;
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rowViews.clear();//清空集合
        //获取屏幕总宽度(包含默认paddingleft和paddingright)
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //去除默认宽带与实际宽带进行比较
        int nopaddingWidth = width - getPaddingLeft() - getPaddingRight();

        RowView rowView = null;
        //遍历所有的view进行分行
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);//获取对应的view
            childView.measure(0, 0);//通知父view进行测量
            if (rowView == null) {
                rowView = new RowView();
            }
            if (rowView.getRowViews().size() == 0) {//如果当前行一个view都没有，，直接添加，不用比较
                rowView.addChidView(childView);
            } else if (rowView.getRowWidth() + horizontalSpacing + childView.getMeasuredWidth() > nopaddingWidth) {
                //如果当前的行宽度+水平间距+当前view的宽带大于nopaddingWidth，则需要换行
                rowViews.add(rowView);//将之前的行保存起来
                rowView = new RowView();//重新创建一行，，将当前的view保存起来
                rowView.addChidView(childView);
            } else {
                rowView.addChidView(childView);//当前childView添加后没有超出nopaddingWidth，可以将childView添加到当前行
            }
            // 如果当前childView是最后一个子View，会造成最后一行line丢失
            if (i == getChildCount() - 1) {
                rowViews.add(rowView);
            }
        }
        //计算layout所有行需要的高度
        int heght = getPaddingTop() + getPaddingBottom();//加上padding值
        for (int i = 0; i < rowViews.size(); i++) {
            heght += rowViews.get(i).getRowHeight();//添加每行高度
        }
        heght += (rowViews.size() - 1) * verticalSpacing;//添加垂直间距高度
        if (rowViews.size() > 0) width = rowViews.get(0).rowWidth;
        setMeasuredDimension(width, heght);//向父view申请宽带和高度
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        }
    }

    //将每个view放到对应的位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        for (int i = 0; i < rowViews.size(); i++) {
            RowView rowView = rowViews.get(i);//获取到当前的line对象
            //后面每一行的top值要相应的增加,当前行的top是上一行的top值+height+垂直间距
            if (i > 0) {
                paddingTop += rowViews.get(i - 1).getRowHeight() + verticalSpacing;
            }
            List<View> viewList = rowView.getRowViews();//获取line的子View集合

            for (int j = 0; j < viewList.size(); j++) {
                View childView = viewList.get(j);//获取当前的子View
                if (j == 0) {
                    //每行的第一个子View,需要靠左边摆放
                    childView.layout(paddingLeft, paddingTop, paddingLeft + childView.getMeasuredWidth(),
                            paddingTop + childView.getMeasuredHeight());
                } else {
                    //摆放后面的子View，需要参考前一个子View的right
                    View preView = viewList.get(j - 1);//获取前一个子View
                    int left = preView.getRight() + horizontalSpacing;//前一个VIew的right+水平间距
                    childView.layout(left, preView.getTop(), left + childView.getMeasuredWidth(), preView.getBottom());
                }
            }
        }
    }

    class RowView {
        private List<View> lineViews;//用于存放每行的view
        private int rowWidth;//行宽
        private int rowHeight;//行高

        public RowView() {
            lineViews = new ArrayList<>();
        }

        public List<View> getRowViews() {
            return lineViews;
        }

        public int getRowWidth() {
            return rowWidth;
        }

        public int getRowHeight() {
            return rowHeight;
        }

        //存放view到rowViews
        public void addChidView(View view) {
            if (!lineViews.contains(view)) {
                ((RadioButton) view).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.OnText((CustomRadioGroup) v.getParent(), (RadioButton) v, ((RadioButton) v).getText().toString().trim(),
                                    chooseData.indexOf(((RadioButton) v).getText().toString().trim()));
                        }
                    }
                });
                //更新高度和宽度
                if (lineViews.size() == 0) {
                    //第一次添加view,不用添加水平间距
                    rowWidth = view.getMeasuredWidth();
                } else {
                    //不是第一次添加，需要添加水平间距
                    rowWidth += view.getMeasuredWidth() + horizontalSpacing;
                }
                //height应该是所有子view中高度最大的那个
                rowHeight = Math.max(view.getMeasuredHeight(), rowHeight);
                lineViews.add(view);
            }
        }
    }
}
