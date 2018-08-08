package com.revapp.prakoso.aptifibeta;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

import static android.content.Context.ACCESSIBILITY_SERVICE;
/**
 * Created by aqilprakoso on 8/19/17.
 */

public class AksesData {
    public static boolean checkAccessibilityenable(Context context) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(ACCESSIBILITY_SERVICE);
//        boolean enable = am.isEnabled();
//        boolean touchEnabled = am.isTouchExplorationEnabled();
        List<AccessibilityServiceInfo> list = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo info : list) {
            if (info.getId().equals("com.revapp.prakoso.aptifibeta/.AksesService")) {
                return true;
            }
        }
        return false;
    }
}
