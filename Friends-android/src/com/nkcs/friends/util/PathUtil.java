package com.nkcs.friends.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;

public class PathUtil {
	public static String getRealPathFromURI(final Context context, final Uri uri) {

		if (null == uri)
			return null;

		final String scheme = uri.getScheme();

		String path = null;

		if (scheme == null)
			path = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			path = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {

			Cursor cursor = context.getContentResolver().query(uri, new String[]{ImageColumns.DATA}, null, null, null);

			if (null != cursor) {

				if (cursor.moveToFirst()) {

					int index = cursor.getColumnIndex(ImageColumns.DATA);

					if (index > -1) {
						//
						path = cursor.getString(index);
					}
				}

				cursor.close();
			}
		}

		return path;
	}
	
	public static String convertFilename(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date date = new Date();
	
		String now = sdf.format(date);
		
		Random rd = new Random();
		
		int random = rd.nextInt(900) + 100;
		
		return now + random;
		
	}
	
}
