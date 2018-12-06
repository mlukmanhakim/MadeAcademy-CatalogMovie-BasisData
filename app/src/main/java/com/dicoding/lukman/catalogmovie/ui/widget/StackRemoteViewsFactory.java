package com.dicoding.lukman.catalogmovie.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dicoding.lukman.catalogmovie.BuildConfig;
import com.dicoding.lukman.catalogmovie.R;
import com.dicoding.lukman.catalogmovie.data.models.Result;

import java.util.concurrent.ExecutionException;

import static com.dicoding.lukman.catalogmovie.data.db.DatabaseContract.CONTENT_URI;


public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = "StackRemoteViewsFactory";


    private Cursor cursor;
    private Context mContext;
    private Bitmap bitmap = null;

    StackRemoteViewsFactory(Context context, Intent intent){
        mContext = context;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor!=null){
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();

        cursor = mContext.getContentResolver().query(CONTENT_URI,
                null,
                null,
                null,
                null);
        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if (cursor != null){
            cursor.close();
        }

    }

    @Override
    public int getCount() {
        return cursor == null?0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Result result = getItem(position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        try {
            bitmap = Glide.with(mContext)
                     .asBitmap()
                     .load(BuildConfig.IMAGE_URL + result.getPosterPath())
//                    .error(new ColorDrawable())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        }catch (InterruptedException | ExecutionException e){
            Log.d(TAG, "getViewAt: Error ." );
        }
        rv.setImageViewBitmap(R.id.imageView, bitmap);
//        rv.setTextViewText(R.id.textWidget, result.getTitle());
//        Log.d(TAG, "getViewAt: titile" + result.getTitle());
        return rv;
    }
    private Result getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new Result(cursor);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
