package com.jin123d.Interface;

import android.graphics.Bitmap;

/**
 * Created by hekr_jds on 11/15 0015.
 **/

public interface GetCodeListener extends HttpListener {
    void getSuccess(Bitmap bitmap);
}
