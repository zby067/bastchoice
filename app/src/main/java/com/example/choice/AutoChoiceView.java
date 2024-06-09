package com.example.choice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class AutoChoiceView extends View {
    private static final String TAG = "AutoChoiceView";

    private int backgroundColor;

    private int borderColor;

    private int borderWidth;

    private float centerPointScale;

    private float choiceSweepAngle = 0.0F;

    private ArrayList<Choice> choices = new ArrayList<Choice>();

    private ValueAnimator choicesAnimator;

    private ArrayList<Choice> choicesCache = new ArrayList<Choice>();

    private boolean inProgress = false;

    private int innerCircleBorderColor;

    private float innerCircleBorderScale;

    private int innerCircleColor;

    private float innerCircleScale;

    private Paint mPaint = new Paint();

    private ValueAnimator maskAnimator;

    private float maskSweepAngle = 0.0F;

    private OnPointerStopListener onPointerStopListener;

    private int padding;

    private float perChoiceAngle = 1.0F;

    private int pointerBorderColor;

    private int pointerColor;

    private float pointerLengthScale;

    private Path pointerPath = new Path();

    private float pointerSweepAngle;

    private int preCircleNumber;

    private RectF rect = new RectF();

    private float startAngle;

    private Point viewCenter = new Point();

    private int viewHeight;

    private int viewWidth;

    public AutoChoiceView(Context paramContext) {
        this(paramContext, (AttributeSet)null);
    }

    public AutoChoiceView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public AutoChoiceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        this(paramContext, paramAttributeSet, paramInt, 0);
    }

    public AutoChoiceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
        super(paramContext, paramAttributeSet, paramInt1, paramInt2);
        TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AutoChoiceView);
        this.backgroundColor = typedArray.getColor(0, -1);
        this.borderColor = typedArray.getColor(R.styleable.AutoChoiceView_borderColor, -1);
        this.padding = typedArray.getDimensionPixelSize(R.styleable.AutoChoiceView_paddingWidth, 40);
        this.borderWidth = typedArray.getDimensionPixelOffset(R.styleable.AutoChoiceView_borderWidth, 20);
        this.startAngle = typedArray.getFloat(R.styleable.AutoChoiceView_startAngle, 270.0F);
        this.innerCircleScale = typedArray.getFloat(R.styleable.AutoChoiceView_innerCircleScale, 0.25F);
        this.innerCircleColor = typedArray.getColor(R.styleable.AutoChoiceView_innerCircleColor, -1);
        this.innerCircleBorderColor = typedArray.getColor(R.styleable.AutoChoiceView_innerCircleBorderColor, -1);
        this.innerCircleBorderScale = typedArray.getFloat(R.styleable.AutoChoiceView_innerCircleBorderScale, 0.2F);
        this.pointerLengthScale = typedArray.getFloat(R.styleable.AutoChoiceView_pointerLengthScale, 0.5F);
        this.pointerColor = typedArray.getColor(R.styleable.AutoChoiceView_pointerColor, -7829368);
        this.pointerBorderColor = typedArray.getColor(R.styleable.AutoChoiceView_pointerBorderColor, -1);
        this.centerPointScale = typedArray.getFloat(R.styleable.AutoChoiceView_centerPointScale, 0.1F);
        this.preCircleNumber = typedArray.getInteger(R.styleable.AutoChoiceView_preCircleNumber, 5);
        typedArray.recycle();
        correctData();
        initPaint();
        initAnimators();
    }

    private void calculateAngles() {
        int i = this.choicesCache.size();
        Choice.PER_ANGLE = 360.0F / getAllWeight();
        float f = 0.0F;
        byte b = 0;
        while (b < i) {
            float f1 = f;
            if (b > 0)
                f1 = f + ((Choice)this.choicesCache.get(b - 1)).getWeight() * Choice.PER_ANGLE;
            ((Choice)this.choicesCache.get(b)).setStartAngle(f1);
            b++;
            f = f1;
        }
    }

    private void choicesAnimation() {
        this.inProgress = true;
        refreshChoices();
        this.choicesAnimator.start();
    }

    private void correctData() {
        if (this.innerCircleScale > 0.9F)
            this.innerCircleScale = 0.9F;
        if (this.innerCircleBorderScale > 0.9F)
            this.innerCircleBorderScale = 0.9F;
        if (this.pointerLengthScale > 0.9F)
            this.pointerLengthScale = 0.9F;
        if (this.centerPointScale > 0.9F)
            this.centerPointScale = 0.9F;
        if (this.preCircleNumber < 1)
            this.preCircleNumber = 1;
    }

    private float getAllWeight() {
        Iterator<Choice> iterator = this.choicesCache.iterator();
        int i;
        for (i = 0; iterator.hasNext(); i += ((Choice)iterator.next()).getWeight());
        return i;
    }

    private int getDrawCount(float paramFloat) {
        for (int i = this.choicesCache.size() - 1; i >= 0; i--) {
            if (paramFloat >= ((Choice)this.choicesCache.get(i)).getStartAngle())
                return i + 1;
        }
        return 0;
    }

    private float getRandomAngle() {
        return (new Random()).nextFloat() * 359.9999F;
    }

    private void initAnimators() {
        this.maskAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 360.0F });
        this.maskAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 360.0F });
        this.maskAnimator.setDuration(1000L);
        this.maskAnimator.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
        this.maskAnimator.addUpdateListener(new _$$Lambda$AutoChoiceView$6kzJvynG_AHyO8cf8T_C3hm3lG8(this));
        this.maskAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            final AutoChoiceView this$0;

            public void onAnimationEnd(Animator param1Animator) {
                AutoChoiceView.this.choicesAnimation();
            }
        });
        this.choicesAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 360.0F });
        this.choicesAnimator.setDuration(1200L);
        this.choicesAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
        this.choicesAnimator.addUpdateListener(new _$$Lambda$AutoChoiceView$MtomKQRbz0RUwJxm5XthIef4Mfo(this));
        this.choicesAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            final AutoChoiceView this$0;

            public void onAnimationEnd(Animator param1Animator) {
                AutoChoiceView.access$102(AutoChoiceView.this, false);
            }
        });
    }

    private void initPaint() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
    }

    private void maskAnimation() {
        this.inProgress = true;
        this.maskAnimator.start();
    }

    private void pointerRandomAnimation() {
        float f1 = getRandomAngle();
        float f2 = (this.preCircleNumber * 360) + f1;
        f1 = this.pointerSweepAngle;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { f1, f1 + f2 });
        valueAnimator.setDuration((long)(f2 / 360.0F * 600.0F));
        valueAnimator.setInterpolator((TimeInterpolator)new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new _$$Lambda$AutoChoiceView$hiXJ62n9qoYAgY3lcgYyaex2hiA(this));
        valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            final AutoChoiceView this$0;

            public void onAnimationEnd(Animator param1Animator) {
                AutoChoiceView.access$102(AutoChoiceView.this, false);
                if (AutoChoiceView.this.onPointerStopListener != null) {
                    AutoChoiceView autoChoiceView = AutoChoiceView.this;
                    int i = autoChoiceView.getDrawCount(360.0F - autoChoiceView.pointerSweepAngle % 360.0F);
                    AutoChoiceView.this.onPointerStopListener.onPointerStop(AutoChoiceView.this.choicesCache.get(i - 1));
                }
            }
        });
        valueAnimator.start();
    }

    private void refreshChoices() {
        this.choicesCache.clear();
        this.choicesCache.addAll(this.choices);
        calculateAngles();
        this.perChoiceAngle = Choice.PER_ANGLE;
        Log.d("AutoChoiceView", "refreshColors: color has been refresh.");
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.viewWidth = getWidth();
        this.viewHeight = getWidth();
        Point point = this.viewCenter;
        int i = this.viewWidth;
        point.x = i / 2;
        point.y = this.viewHeight / 2;
        float f1 = (i / 2 - this.padding);
        this.mPaint.setColor(this.borderColor);
        paramCanvas.drawCircle(this.viewCenter.x, this.viewCenter.y, f1, this.mPaint);
        int j = this.padding + this.borderWidth;
        RectF rectF = this.rect;
        f1 = j;
        rectF.top = f1;
        rectF.left = f1;
        i = this.viewWidth;
        rectF.right = (i - j);
        rectF.bottom = (this.viewHeight - j);
        float f2 = (i / 2 - j);
        this.mPaint.setColor(this.backgroundColor);
        paramCanvas.drawCircle(this.viewCenter.x, this.viewCenter.y, f2, this.mPaint);
        if (this.choicesCache.size() > 0) {
            f1 = this.choiceSweepAngle;
            if (f1 > 0.0F) {
                i = getDrawCount(f1);
                f1 = this.choiceSweepAngle;
                ArrayList<Choice> arrayList = this.choicesCache;
                j = i - 1;
                float f = f1 - ((Choice)arrayList.get(j)).getStartAngle();
                this.mPaint.setColor(Color.parseColor(((Choice)this.choicesCache.get(j)).getColor()));
                paramCanvas.drawArc(this.rect, this.startAngle, f, true, this.mPaint);
                i -= 2;
                for (j = i; j >= 0; j--) {
                    this.mPaint.setColor(Color.parseColor(((Choice)this.choicesCache.get(j)).getColor()));
                    int k = i;
                    f1 = 0.0F;
                    while (k > j) {
                        f1 += ((Choice)this.choicesCache.get(k)).getWeight() * this.perChoiceAngle;
                        k--;
                    }
                    paramCanvas.drawArc(this.rect, this.startAngle + f + f1, this.perChoiceAngle * ((Choice)this.choicesCache.get(j)).getWeight(), true, this.mPaint);
                }
            }
        }
        this.mPaint.setColor(this.backgroundColor);
        paramCanvas.drawArc(this.rect, this.startAngle, this.maskSweepAngle, true, this.mPaint);
        this.mPaint.setColor(this.innerCircleBorderColor);
        paramCanvas.drawCircle(this.viewCenter.x, this.viewCenter.y, this.innerCircleScale * f2 * (this.innerCircleBorderScale + 1.0F), this.mPaint);
        this.mPaint.setColor(this.innerCircleColor);
        paramCanvas.drawCircle(this.viewCenter.x, this.viewCenter.y, this.innerCircleScale * f2, this.mPaint);
        this.pointerPath.reset();
        double d2 = (this.pointerLengthScale * f2);
        double d1 = d2 / Math.sqrt(3.0D) * 4.0D * 2.0D;
        double d3 = (this.pointerSweepAngle / 360.0F) * 6.283185307179586D;
        f1 = (float)(this.viewCenter.x + Math.sin(d3) * d2);
        float f3 = (float)(this.viewCenter.y - Math.cos(d3) * d2);
        float f4 = (float)(d2 * this.centerPointScale);
        this.rect.left = this.viewCenter.x - f4;
        this.rect.top = this.viewCenter.y - f4;
        this.rect.right = this.viewCenter.x + f4;
        this.rect.bottom = this.viewCenter.y + f4;
        this.pointerPath.addArc(this.rect, this.pointerSweepAngle - 60.0F, 300.0F);
        if (f4 >= d1) {
            this.pointerPath.lineTo(f1, f3);
            this.pointerPath.close();
        } else {
            d2 = ((this.pointerSweepAngle - 30.0F) / 360.0F) * 6.283185307179586D;
            f4 = (float)(this.viewCenter.x + Math.sin(d2) * d1);
            float f = (float)(this.viewCenter.y - Math.cos(d2) * d1);
            this.pointerPath.lineTo(f4, f);
            this.pointerPath.lineTo(f1, f3);
            d2 = ((this.pointerSweepAngle + 30.0F) / 360.0F) * 6.283185307179586D;
            f1 = (float)(this.viewCenter.x + Math.sin(d2) * d1);
            f3 = (float)(this.viewCenter.y - Math.cos(d2) * d1);
            this.pointerPath.lineTo(f1, f3);
            this.pointerPath.close();
        }
        this.mPaint.setColor(this.pointerColor);
        paramCanvas.drawPath(this.pointerPath, this.mPaint);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth((float)((f2 * this.pointerLengthScale) * 0.02D));
        this.mPaint.setStrokeJoin(Paint.Join.MITER);
        this.mPaint.setColor(this.pointerBorderColor);
        paramCanvas.drawPath(this.pointerPath, this.mPaint);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        if (View.MeasureSpec.getSize(paramInt1) > View.MeasureSpec.getSize(paramInt2))
            paramInt1 = paramInt2;
        super.onMeasure(paramInt1, paramInt1);
    }

    public void refreshData(ArrayList<Choice> paramArrayList) {
        boolean bool;
        if (this.inProgress)
            return;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("refreshData: old size -> ");
        stringBuilder.append(this.choicesCache.size());
        Log.d("AutoChoiceView", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("refreshData: new size -> ");
        stringBuilder.append(paramArrayList.size());
        Log.d("AutoChoiceView", stringBuilder.toString());
        if (this.choices.size() > 0) {
            bool = true;
        } else {
            bool = false;
        }
        this.choices.clear();
        this.choices.addAll(paramArrayList);
        if (bool) {
            maskAnimation();
        } else {
            choicesAnimation();
        }
    }

    public void select() {
        if (this.choices.size() != 0 && !this.inProgress) {
            this.inProgress = true;
            pointerRandomAnimation();
        }
    }

    public void setOnPointerStopListener(OnPointerStopListener paramOnPointerStopListener) {
        this.onPointerStopListener = paramOnPointerStopListener;
    }

    public static interface OnPointerStopListener {
        void onPointerStop(Choice param1Choice);
    }
}
