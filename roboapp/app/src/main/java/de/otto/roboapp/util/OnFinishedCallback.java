package de.otto.roboapp.util;

public interface OnFinishedCallback {
    public void onFinished() ;

    public void onFailed(String reason);
}
