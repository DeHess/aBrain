package ch.zhaw.pm3.aBrain.backend.med.administration;

import java.rmi.NoSuchObjectException;

public class XHoursInterval extends AdminInterval{
    private int xHours;
    
    public XHoursInterval(int xHours){
        this.xHours = xHours;
    }
    
    public XHoursInterval(){}
    
    public void setxHours(int xHours) {
        this.xHours = xHours;
    }
    
    public int getxHours() throws NoSuchObjectException {
        if(xHours == 0)  throw new NoSuchObjectException("No hours have been set");
        return xHours;
    }
}
