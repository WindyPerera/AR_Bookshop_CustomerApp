package com.lk.onlinebookshopcustomer.slider;

import android.view.View;
import android.widget.ImageView;

import com.lk.onlinebookshopcustomer.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
    // Adapter class for initializing
    // the views of our slider view.
    View itemView;
    ImageView imageViewBackground;

    public SliderAdapterViewHolder(View itemView) {
        super(itemView);
        imageViewBackground = itemView.findViewById(R.id.myimage);
        this.itemView = itemView;
    }
}
