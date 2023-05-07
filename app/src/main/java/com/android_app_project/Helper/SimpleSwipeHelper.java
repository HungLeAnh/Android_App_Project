package com.android_app_project.Helper;

import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public abstract class SimpleSwipeHelper extends ItemTouchHelper.SimpleCallback {

    private int buttonWidth;
    private RecyclerView recyclerView;
    private List<CustomButtom> buttomList;
    private GestureDetector gestureDetector;
    private int swipePosition = -1;
    private float swipeThreshold = 0.5f;
    private Map<Integer,List<CustomButtom>> buttonBuffer;
    private Queue<Integer> removeQueue;
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder){
        return swipeThreshold;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 0.1f*defaultValue;
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return 5.0f*defaultValue;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int pos = viewHolder.getAdapterPosition();
        float translationX = dX;
        View view = viewHolder.itemView;
        if(pos < 0){
            return;
        }
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if(dX < 0 ){
                List<CustomButtom> buffer = new ArrayList<>();
                if(!buttonBuffer.containsKey(pos)){
                    instantiateCustomButton(viewHolder,buffer);
                    buttonBuffer.put(pos,buffer);
                }
                else{
                    buffer = buttonBuffer.get(pos);
                }
                translationX = dX*buttonBuffer.size()*buttonWidth/view.getWidth();
                drawButton(c,view,buffer,pos,translationX);

            }
        }
        super.onChildDraw(c,recyclerView,viewHolder,translationX,dY,actionState,isCurrentlyActive);

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        buttomList.clear();
        super.clearView(recyclerView, viewHolder);
    }

    private void drawButton(Canvas c, View view, List<CustomButtom> buffer, int pos, float translationX) {
        float right = view.getRight();
        float dButtonWidth = -1* translationX/buffer.size();
        for (CustomButtom buttom: buffer){
            float left = right - dButtonWidth;
            buttom.onDraw(c,new RectF(left,view.getTop(),right,view.getBottom()),pos);
            right = left;
        }
    }

    public abstract void instantiateCustomButton(RecyclerView.ViewHolder viewHolder, List<CustomButtom> buffer);

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {
            for (CustomButtom customButtom: buttomList){
                if(customButtom.onClick(e.getX(),e.getY()))
                    break;
            }
            return super.onSingleTapUp(e);
        }
    };
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(swipePosition<0)
                return false;
            Point point = new Point((int) event.getRawX(),(int) event.getRawY());

            RecyclerView.ViewHolder swipeViewHolder = recyclerView.findViewHolderForAdapterPosition(swipePosition);
            View swipedItem = swipeViewHolder.itemView;
            Rect rect = new Rect();
            swipedItem.getGlobalVisibleRect(rect);

            if(event.getAction() == MotionEvent.ACTION_DOWN ||
                    event.getAction() == MotionEvent.ACTION_UP||
                    event.getAction() == MotionEvent.ACTION_MOVE){
                if(rect.top<point.y && rect.bottom > point.y)
                    gestureDetector.onTouchEvent(event);
                else{
                    removeQueue.add(swipePosition);
                    swipePosition=-1;
                    recoverSwipedItem();
                }
            }
            return false;
        };
    };

    private synchronized void recoverSwipedItem() {
        while (!removeQueue.isEmpty()){
            int pos = removeQueue.poll();
            if(pos > -1)
                recyclerView.getAdapter().notifyItemChanged(pos);
        }
    }

    public SimpleSwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }
    public SimpleSwipeHelper(Context context,RecyclerView recyclerView,int buttonWidth) {
        super(0,ItemTouchHelper.LEFT);
        this.recyclerView = recyclerView;
        this.buttomList = new ArrayList<>();
        this.gestureDetector = new GestureDetector(context,simpleOnGestureListener);
        this.recyclerView.setOnTouchListener(onTouchListener);
        this.buttonBuffer = new HashMap<>();
        this.buttonWidth = buttonWidth;

        removeQueue = new LinkedList<Integer>(){
            @Override
            public boolean add(Integer integer) {
                if(contains(integer)){
                    return false;
                }
                else{
                    return super.add(integer);
                }
            }
        };
        attachSwipe();
    }

    private void attachSwipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        if(swipePosition != pos){
            removeQueue.add(swipePosition);
        }
        swipePosition = pos;
        if(buttonBuffer.containsKey(swipePosition))
            buttomList = buttonBuffer.get(swipePosition);
        else
            buttomList.clear();
        buttonBuffer.clear();
        swipeThreshold = 0.5f*buttomList.size()*buttonWidth;
        recoverSwipedItem();
    }

    public class CustomButtom {
        private String text;
        private int imageResId,textSize,color,pos;
        private RectF clickRegion;
        private CustomButtomonClickListener listener;
        private Context context;
        private Resources resources;

        public CustomButtom(Context context, String text, int imageResId, int textSize, int color, CustomButtomonClickListener listener) {
            this.text = text;
            this.imageResId = imageResId;
            this.textSize = textSize;
            this.color = color;
            this.listener = listener;
            this.context = context;
            resources = context.getResources();
        }

        public boolean onClick(float x,float y){
            if(clickRegion != null && clickRegion.contains(x,y)){
                listener.onClick(pos);
                return true;
            }
            return false;
        }

        public void onDraw(Canvas c, RectF rectF, int pos){
            Paint p = new Paint();
            p.setColor(color);
            c.drawRect(rectF,p);
            //text
            p.setColor(Color.WHITE);
            p.setTextSize(textSize);

            Rect r = new Rect();
            float cHeight = rectF.height();
            float cWidth = rectF.width();
            p.setTextAlign(Paint.Align.LEFT);
            p.getTextBounds(text,0,text.length(),r);
            float x=0,y=0;
            if(imageResId ==0 ){
                x = cWidth/2f - r.width()/2f - r.left;
                y = cHeight/2f + r.height()/2f - r.bottom;
                c.drawText(text,rectF.left+x,rectF.top+y,p);
            }
            else {
                Drawable d = ContextCompat.getDrawable(context,imageResId);
                Bitmap bitmap = drawableToBitmap(d);
                c.drawBitmap(bitmap,(rectF.left+rectF.right)/2,(rectF.top+ rectF.bottom)/2,p);
            }
            clickRegion = rectF;
            this.pos = pos;
        }
        private Bitmap drawableToBitmap(Drawable d){
            if(d instanceof BitmapDrawable)
                return ((BitmapDrawable)d).getBitmap();
            Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(),d.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            d.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
            d.draw(canvas);
            return bitmap;
        }
    }
}
