package com.iuunited.myhome.view.smartimage;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/31 11:19
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/10/31.
 */
public class ContactImage implements SmartImage {
    private long contactId;

    public ContactImage(long contactId) {
        this.contactId = contactId;
    }

    public Bitmap getBitmap(Context context) {
        Bitmap bitmap = null;
        ContentResolver contentResolver = context.getContentResolver();

        try {
            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, uri);
            if(input != null) {
                bitmap = BitmapFactory.decodeStream(input);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}