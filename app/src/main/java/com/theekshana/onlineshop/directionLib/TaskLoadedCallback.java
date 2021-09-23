package com.theekshana.onlineshop.directionLib;

import com.theekshana.onlineshop.POJO.mapDistanceObj;
import com.theekshana.onlineshop.POJO.mapTimeObj;

public interface TaskLoadedCallback {
    void onTaskDone(Object... values);
    void onDistanceTaskDone(mapDistanceObj distance);
    void onTimeTaskDone(mapTimeObj time);
}