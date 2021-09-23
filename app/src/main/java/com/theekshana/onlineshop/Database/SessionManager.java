package com.theekshana.onlineshop.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;


    public static final String IS_LOGIN = "IsLoggeIn";
    public static final String KEY_FULLNAME = "FULLNAME";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERIMAGE = "image";
    public static final String KEY_TOKEN = "usertoken";
    public static final String KEY_PHONENUMBER = "phonenumber";
    public static final String KEY_CARDSTATUS = "cardstatus";
    public static final String KEY_ROLE = "role";
    public static final String KEY_ISACTIVE = "isactive";
    public static final String KEY_GOOGLEID = "googleid";
    public static final String KEY_ADDRESSTATUS = "addressstatus";


   public SessionManager(Context _context){
        context = _context;
        userSession = context.getSharedPreferences("userSession",Context.MODE_PRIVATE);
        editor = userSession.edit();

   }


   public void createLoginSession(String name,String email,String googlid){

       editor.putString(KEY_FULLNAME,name);
       editor.putString(KEY_EMAIL,email);
       editor.putString(KEY_GOOGLEID,googlid);
       editor.commit();
   }

    public void LoginSession(String email){

        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }

    public void createuserSession(String fullname,String lastname,String email,String password,String image, String usertoken,String phonenum, String cardstatus, String role, String isactive,String addressStatus){

        editor.putString(KEY_FULLNAME,fullname);
        editor.putString(KEY_LASTNAME,lastname);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_USERIMAGE,image);
        editor.putString(KEY_TOKEN,usertoken);
        editor.putString(KEY_PHONENUMBER,phonenum);
        editor.putString(KEY_CARDSTATUS,cardstatus);
        editor.putString(KEY_ROLE,role);
        editor.putString(KEY_ISACTIVE,isactive);
        editor.putString(KEY_ADDRESSTATUS,addressStatus);
        editor.commit();
    }

   public HashMap<String, String> getUserDetailfromSession(){

       HashMap<String,String> userdata = new HashMap<String, String>();

       userdata.put(KEY_FULLNAME,userSession.getString(KEY_FULLNAME,null));
       userdata.put(KEY_LASTNAME,userSession.getString(KEY_LASTNAME,null));
       userdata.put(KEY_EMAIL,userSession.getString(KEY_EMAIL,null));
       userdata.put(KEY_PASSWORD,userSession.getString(KEY_PASSWORD,null));
       userdata.put(KEY_USERIMAGE,userSession.getString(KEY_USERIMAGE,null));
       userdata.put(KEY_TOKEN,userSession.getString(KEY_TOKEN,null));
       userdata.put(KEY_PHONENUMBER,userSession.getString(KEY_PHONENUMBER,null));
       userdata.put(KEY_CARDSTATUS,userSession.getString(KEY_CARDSTATUS,null));
       userdata.put(KEY_ROLE,userSession.getString(KEY_ROLE,null));
       userdata.put(KEY_ISACTIVE,userSession.getString(KEY_ISACTIVE,null));
       userdata.put(KEY_GOOGLEID,userSession.getString(KEY_GOOGLEID,null));
       userdata.put(KEY_ADDRESSTATUS,userSession.getString(KEY_ADDRESSTATUS,null));

       return userdata;
   }

   public boolean checkLogin(){
       if (userSession.getBoolean(IS_LOGIN,false)){
           return true;
       }else {
           return false;
       }
   }


   public void logoutUserFromSession(){
       editor.clear();
       editor.commit();
   }

}
