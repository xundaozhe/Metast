package com.iuunited.myhome.mapdemo;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import static com.iuunited.myhome.R.drawable.location;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2016/12/13 15:17
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/13.
 */

public class AMapLocUtils implements AMapLocationListener {

    private AMapLocationClient locationClient = null;//定位
    private AMapLocationClientOption locationOption = null;//定位设置

    private LonlatListener mLonlatListener;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mLonlatListener.getLonlat(aMapLocation);
        locationClient.stopLocation();
        locationClient.onDestroy();
        locationClient = null;
        locationOption = null;
    }

    public void getLonlat(Context context,LonlatListener lonlatListener){
        locationClient = new AMapLocationClient(context);
        locationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationClient.setLocationListener(this);//设置定位监听
        locationOption.setOnceLocation(false);//单次定位
        locationOption.setNeedAddress(true);//逆地理编码
        mLonlatListener = lonlatListener;
        locationClient.setLocationOption(locationOption);//设置定位参数
        locationClient.startLocation();//启动定位
    }

    public interface LonlatListener{
        void getLonlat(AMapLocation aMapLocation);
    }
}
