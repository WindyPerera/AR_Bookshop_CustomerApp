package com.lk.onlinebookshopcustomer.DirectionLib;


//import com.lk.jiatcode.pojo.mapDistanceObj;
//import com.lk.jiatcode.pojo.mapTimeObj;

import com.lk.onlinebookshopcustomer.POJO.MapDistanceObject;
import com.lk.onlinebookshopcustomer.POJO.MapTimeObject;

public interface TaskLoadedCallback {
    void onTaskDone(Object... values);
    void onDistanceTaskDone(MapDistanceObject distance);
    void onTimeTaskDone(MapTimeObject time);
}
