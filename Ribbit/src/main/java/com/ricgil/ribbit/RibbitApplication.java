package com.ricgil.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by rgomes on 3/9/14.
 */
public class RibbitApplication extends Application {
    public void onCreate() {
        Parse.initialize(this, "S0kT77Zk4XIZ586VdJOPQ0rVOQqeSKRMTK4TBJlu", "VWoGTbZgoLpVkcFYKrOnvjA0osLGwrVtpz5HyEhZ");

       /* ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/
    }

}
