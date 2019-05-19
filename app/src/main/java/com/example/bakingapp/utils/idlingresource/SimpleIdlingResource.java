package com.example.bakingapp.utils.idlingresource;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

public class SimpleIdlingResource implements IdlingResource {
    @Nullable private volatile ResourceCallback mResourceCallback;
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        this.mIsIdleNow.set(isIdleNow);
        if(isIdleNow && mResourceCallback != null) {
            mResourceCallback.onTransitionToIdle();
        }
    }
}
