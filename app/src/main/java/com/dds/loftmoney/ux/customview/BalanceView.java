package com.dds.loftmoney.ux.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.dds.loftmoney.R;

public class BalanceView extends View {
    //region private members

    private float credit = 5400;
    private float debit = 7400;

    private  int distance = 15;

    private Paint creditPaint = new Paint();
    private Paint debitPaint = new Paint();

    //endregion

    //region ctor...

    public BalanceView(Context context) {
        super(context);
        initColors();
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initColors();
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initColors();
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initColors();
    }

    //endregion

    //region Overrided members

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float total = credit + debit;

        float creditAngle = 360f * credit / total;
        float debitAngle = 360f * debit / total;

        //// 2.5% from average size - best feet
        distance = (int)((((getWidth() + getHeight()) / 2) / 100) * 2.5f);

        int size = Math.min(getWidth(), getHeight()) - distance * 2;
        int xMargin = (getWidth() - size) / 2;
        int yMargin = (getHeight() - size) / 2;

        canvas.drawArc(xMargin - distance, yMargin,
                getWidth() - xMargin - distance,
                getHeight() - yMargin, 180 - creditAngle / 2,
                creditAngle, true, creditPaint);

        canvas.drawArc(xMargin + distance, yMargin,
                getWidth() - xMargin + distance,
                getHeight() - yMargin, 360 - debitAngle / 2,
                debitAngle, true, debitPaint);
    }

    //endregion

    //region public members

    public void initBudgetData(float credit, float debit) {
        this.credit = credit;
        this.debit = debit;

        invalidate();
    }

    //endregion

    //region private logic

    private void initColors() {
        creditPaint.setColor(ContextCompat.getColor(getContext(), R.color.dark_sky_blue));
        debitPaint.setColor(ContextCompat.getColor(getContext(), R.color.apple_green));
    }

    //endregion
}
