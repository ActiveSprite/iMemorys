package com.example.guhugang.imemorys;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.util.Log;

import com.amap.api.location.CoordinateConverter;

import com.amap.api.location.DPoint;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.guhugang.example.guhugang.uploadfileservice.Data;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

/**
 * Created by GuHuGang on 2018/1/25.
 */

public class GetPictureLocation implements GeocodeSearch.OnGeocodeSearchListener {
    private int start;
    private int end;
    ImageScanner mScanner;
    ContentResolver cr;
    Context context;

    public GetPictureLocation(Context context) {
        cr = context.getContentResolver();
        this.context=context;

    }

    public String getLocationInfo(String path) {
            RegeocodeAddress regeocodeAddress = null;
            ExifInterface exifInterface = null;
            try {
                exifInterface = new ExifInterface(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String lngRef = exifInterface.getAttribute
                    (ExifInterface.TAG_GPS_LONGITUDE_REF);
            if(latValue==null||lngValue==null)return null;
            float latitude = convertRationalLatLonToFloat(latValue, latRef);
            float longitude = convertRationalLatLonToFloat(lngValue, lngRef);
            Log.i("latitude",String.valueOf(latitude));


            try {

                LatLonPoint latLng = new LatLonPoint(latitude,longitude);
                GeocodeSearch geocoderSearch = new GeocodeSearch(context);
                geocoderSearch.setOnGeocodeSearchListener(this);
                RegeocodeQuery query = new RegeocodeQuery(latLng, 200, GeocodeSearch.GPS);
                regeocodeAddress= geocoderSearch.getFromLocation(query);
                Log.i("location",regeocodeAddress.getCity());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(regeocodeAddress!=null)
                 return   regeocodeAddress.getCity() ;

            return null;

    }
    private static float convertRationalLatLonToFloat(
            String rationalString, String ref) {

        String[] parts = rationalString.split(",");

        String[] pair;
        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[2].split("/");
        double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
        if ((ref.equals("S") || ref.equals("W"))) {
            return (float) -result;
        }
        return (float) result;
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        Log.i("location1",regeocodeResult.getRegeocodeAddress().getCity());
        Log.i("code",String.valueOf(i));
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
