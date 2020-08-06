package com.gasco.services;

import com.gasco.services.gescoServices.DispenseLogService;
import com.gasco.services.gescoServices.SkidProcedureService;
import com.gasco.services.gescoServices.UserService;
import com.gasco.utils.SharedData;

public class API {
    public static UserService USER_SERVICE = new UserService();
    public static DispenseLogService DISPENSE_LOG_SERVICE = new DispenseLogService();
    public static SkidProcedureService SKID_PROCEDURE_SERVICE = new SkidProcedureService();

    public static String GetAuthorizationValue(){
        return SharedData.USER_TOKEN;
    }
}
