package com.faisal.basemodule.example;

import androidx.appcompat.app.AppCompatActivity;

import com.faisal.basemodule.Toaster;
import com.faisal.basemodule.base.BaseActivity;
import com.faisal.basemodule.example.databinding.ActivitySampleBinding;


public class SampleActivity extends BaseActivity {
    ActivitySampleBinding mBinding;
    SampleViewModel viewModel;
    @Override
    public int setLayoutId() {
        return R.layout.activity_sample;
    }

    @Override
    public void startUI() {
        mBinding = (ActivitySampleBinding) getViewDataBinding();
        viewModel = (SampleViewModel) getViewModel(SampleViewModel.class);
        mBinding.textView.setOnClickListener(v -> {
            Toaster.ShowText("Clicked!!");
        });
    }
}
