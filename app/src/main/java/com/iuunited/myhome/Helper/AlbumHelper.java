package com.iuunited.myhome.Helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.iuunited.myhome.bean.ImageBucket;
import com.iuunited.myhome.bean.ImageItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/10/27 17:22
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes 附加图片专辑帮助类
 * Created by xundaozhe on 2016/10/27.
 */
public class AlbumHelper {
    final String TAG = getClass().getSimpleName();
    Context context;
    ContentResolver cr;

    // 缩略图列表
    HashMap<String, String> thumbnailList = new HashMap<String, String>();
    // 专辑列表
    List<HashMap<String, String>> albumList = new ArrayList<HashMap<String, String>>();
    //根据每个相册集ID存一个相册
    HashMap<String, ImageBucket> bucketList = new HashMap<String, ImageBucket>();

    private static AlbumHelper instance;

    private AlbumHelper() {
    }

    public static AlbumHelper getHelper() {
        if (instance == null) {
            instance = new AlbumHelper();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        if (this.context == null) {
            this.context = context;
            cr = context.getContentResolver();
        }
    }

    /**
     * 去除数据库里的缩略图
     */
    private void getThumbnail() {
        String[] projection = { MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.DATA };
        Cursor cursor = cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection,
                null, null, null);
        getThumbnailColumnData(cursor);
    }

    /**
     * 从数据库中得到缩略图
     *
     * @param cur
     */
    private void getThumbnailColumnData(Cursor cur) {
        if (cur.moveToFirst()) {
            int _id;
            int image_id;
            String image_path;
            int _idColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails._ID);
            int image_idColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            int dataColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.DATA);

            do {
                // Get the field values
                _id = cur.getInt(_idColumn);
                image_id = cur.getInt(image_idColumn);
                image_path = cur.getString(dataColumn);

                // Do something with the values.
                // Log.i(TAG, _id + " image_id:" + image_id + " path:"
                // + image_path + "---");
                // HashMap<String, String> hash = new HashMap<String, String>();
                // hash.put("image_id", image_id + "");
                // hash.put("path", image_path);
                // thumbnailList.add(hash);
                thumbnailList.put("" + image_id, image_path);
            } while (cur.moveToNext());
        }
    }

    /**
     * 得到原图
     */
    void getAlbum() {
        String[] projection = { MediaStore.Audio.Albums._ID,  MediaStore.Audio.Albums.ALBUM,  MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ALBUM_KEY,  MediaStore.Audio.Albums.ARTIST,  MediaStore.Audio.Albums.NUMBER_OF_SONGS};
        Cursor cursor = cr.query( MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null,
                null, null);
        getAlbumColumnData(cursor);

    }

    /**
     * 从本地数据库中得到原图
     *
     * @param cur
     */
    private void getAlbumColumnData(Cursor cur) {
        if (cur.moveToFirst()) {
            int _id;
            String album;
            String albumArt;
            String albumKey;
            String artist;
            int numOfSongs;

            int _idColumn = cur.getColumnIndex( MediaStore.Audio.Albums._ID);
            int albumColumn = cur.getColumnIndex( MediaStore.Audio.Albums.ALBUM);
            int albumArtColumn = cur.getColumnIndex( MediaStore.Audio.Albums.ALBUM_ART);
            int albumKeyColumn = cur.getColumnIndex( MediaStore.Audio.Albums.ALBUM_KEY);
            int artistColumn = cur.getColumnIndex( MediaStore.Audio.Albums.ARTIST);
            int numOfSongsColumn = cur.getColumnIndex( MediaStore.Audio.Albums.NUMBER_OF_SONGS);

            do {
                // Get the field values
                _id = cur.getInt(_idColumn);
                album = cur.getString(albumColumn);
                albumArt = cur.getString(albumArtColumn);
                albumKey = cur.getString(albumKeyColumn);
                artist = cur.getString(artistColumn);
                numOfSongs = cur.getInt(numOfSongsColumn);

                // Do something with the values.
                Log.i(TAG, _id + " album:" + album + " albumArt:" + albumArt
                        + "albumKey: " + albumKey + " artist: " + artist
                        + " numOfSongs: " + numOfSongs + "---");
                HashMap<String, String> hash = new HashMap<String, String>();
                hash.put("_id", _id + "");
                hash.put("album", album);
                hash.put("albumArt", albumArt);
                hash.put("albumKey", albumKey);
                hash.put("artist", artist);
                hash.put("numOfSongs", numOfSongs + "");
                albumList.add(hash);

            } while (cur.moveToNext());

        }
    }

    /**
     * 是否创建了图片集
     */
    boolean hasBuildImagesBucketList = false;

    /**
     * 得到图片集
     */
    void buildImagesBucketList() {
        long startTime = System.currentTimeMillis();

        // 构造缩略图索引
        getThumbnail();

        // 构造相册索引
        String columns[] = new String[] { MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.PICASA_ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.SIZE, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
        // 得到一个游标
        Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null,
                null);
        if (cur.moveToFirst()) {
            // 获取指定列的索引
            int photoIDIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int photoPathIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int photoNameIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            int photoTitleIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
            int photoSizeIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
            int bucketDisplayNameIndex = cur
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int bucketIdIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID);
            int picasaIdIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.PICASA_ID);
            // 获取图片总数
            int totalNum = cur.getCount();

            do {
                String _id = cur.getString(photoIDIndex);
                String name = cur.getString(photoNameIndex);
                String path = cur.getString(photoPathIndex);
                String title = cur.getString(photoTitleIndex);
                String size = cur.getString(photoSizeIndex);
                String bucketName = cur.getString(bucketDisplayNameIndex);
                String bucketId = cur.getString(bucketIdIndex);
                String picasaId = cur.getString(picasaIdIndex);

                Log.i(TAG, _id + ", bucketId: " + bucketId + ", picasaId: "
                        + picasaId + " name:" + name + " path:" + path
                        + " title: " + title + " size: " + size + " bucket: "
                        + bucketName + "---");

                ImageBucket bucket = bucketList.get(bucketId);
                if (bucket == null) {
                    bucket = new ImageBucket();//new 一个相册
                    bucketList.put(bucketId, bucket);
                    bucket.imageList = new ArrayList<ImageItem>();
                    bucket.bucketName = bucketName;
                }
                bucket.count++;
                ImageItem imageItem = new ImageItem();//new 一张图片
                imageItem.imageId = _id;
                imageItem.imagePath = path;
                imageItem.thumbnailPath = thumbnailList.get(_id);
                bucket.imageList.add(imageItem);

            } while (cur.moveToNext());
        }

        Iterator<Map.Entry<String, ImageBucket>> itr = bucketList.entrySet()
                .iterator();
        while (itr.hasNext()) {
            Map.Entry<String, ImageBucket> entry = (Map.Entry<String, ImageBucket>) itr
                    .next();
            ImageBucket bucket = entry.getValue();
            Log.d(TAG, entry.getKey() + ", " + bucket.bucketName + ", "
                    + bucket.count + " ---------- ");
            for (int i = 0; i < bucket.imageList.size(); ++i) {
                ImageItem image = bucket.imageList.get(i);
                Log.d(TAG, "----- " + image.imageId + ", " + image.imagePath
                        + ", " + image.thumbnailPath);
            }
        }
        hasBuildImagesBucketList = true;
        long endTime = System.currentTimeMillis();
        Log.d(TAG, "use time: " + (endTime - startTime) + " ms");
    }

    /**
     * 得到图片集
     *
     * @param refresh
     * @return
     */
    public List<ImageBucket> getImagesBucketList(boolean refresh) {
        if (refresh || (!refresh && !hasBuildImagesBucketList)) {
            buildImagesBucketList();
        }
        List<ImageBucket> tmpList = new ArrayList<ImageBucket>();
        Iterator<Map.Entry<String, ImageBucket>> itr = bucketList.entrySet()
                .iterator();
        while (itr.hasNext()) {
            Map.Entry<String, ImageBucket> entry = (Map.Entry<String, ImageBucket>) itr
                    .next();
            tmpList.add(entry.getValue());
        }
        return tmpList;
    }

    /**
     * 得到原始图像路径
     *
     * @param image_id
     * @return
     */
    String getOriginalImagePath(String image_id) {
        String path = null;
        Log.i(TAG, "---(^o^)----" + image_id);
        String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                MediaStore.Images.Media._ID + "=" + image_id, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

        }
        return path;
    }

}

