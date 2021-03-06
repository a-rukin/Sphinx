package com.airwhip.sphinx.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.airwhip.sphinx.R;
import com.airwhip.sphinx.ResultActivity;
import com.airwhip.sphinx.parser.Characteristic;

/**
 * Created by Whiplash on 30.03.14.
 */
public class CustomizeArrayAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] names;
    private Integer[] progress;
    private TextView sphinxStatistic;
    private TextView postText;

    private int[] feedBackArray;

    public CustomizeArrayAdapter(Context context, String[] names, Integer[] progress, TextView sphinxStatistic, TextView postText) {
        super(context, R.layout.listviewitem, names);
        this.context = context;
        this.names = names;
        this.progress = progress;
        this.sphinxStatistic = sphinxStatistic;
        this.postText = postText;
        this.feedBackArray = new int[progress.length];

        for (int i = 0; i < progress.length; i++) {
            if (progress[i] == -1 && names[i].equals(context.getString(R.string.gender_is_not_defined))) {
                feedBackArray[i] ^= 1;
                break;
            }
        }
        int count = names.length;
        for (int i = 0; i < feedBackArray.length; i++) {
            Characteristic.feedBackResult[i] = feedBackArray[i];
            count -= feedBackArray[i];
        }
        sphinxStatistic.setText(count + "/" + names.length);
        Characteristic.generateResult();
        generatePostMessage(count == 0 ? 0 : (int) (count * 100. / names.length));
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.listviewitem, null, true);

            holder = new ViewHolder();
            holder.progressBar = (ImageView) rowView.findViewById(R.id.progressBar);
            holder.progressBarTriangle = (ImageView) rowView.findViewById(R.id.progressBarTriangle);
            holder.progressText = (TextView) rowView.findViewById(R.id.progressText);
            holder.typeText = (TextView) rowView.findViewById(R.id.typeName);
            holder.isRightButton = (CheckBox) rowView.findViewById(R.id.isRightButton);
            holder.questionButton = (Button) rowView.findViewById(R.id.questionButton);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.typeText.setText(names[position]);
        if (progress[position] != -1) {
            holder.progressBar.getLayoutParams().width = context.getResources().getInteger(R.integer.progress_bar_wight) * progress[position];
            holder.progressText.setText(String.valueOf(progress[position] + "%"));
            holder.progressBar.setBackgroundColor(Constants.colors[position]);
            holder.progressBarTriangle.setBackgroundColor(Constants.colors[position]);
        } else {
            holder.progressText.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.GONE);
            holder.progressBarTriangle.setVisibility(View.GONE);
            if (names[position].equals(context.getString(R.string.gender_is_not_defined))) {
                feedBackArray[position] ^= 1;
                holder.isRightButton.setVisibility(View.GONE);
                holder.questionButton.setVisibility(View.GONE);
            }
        }
        holder.isRightButton.setChecked(true);
        holder.isRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedBackArray[position] ^= 1;
                int count = names.length;
                for (int i = 0; i < feedBackArray.length; i++) {
                    Characteristic.feedBackResult[i] = feedBackArray[i];
                    count -= feedBackArray[i];
                }
                sphinxStatistic.setText(count + "/" + names.length);
                Characteristic.generateResult();
                generatePostMessage(count == 0 ? 0 : (int) (count * 100. / names.length));

            }
        });
        holder.questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.questionButton.setVisibility(View.GONE);
                holder.isRightButton.setVisibility(View.VISIBLE);
                Characteristic.generateResult();
            }
        });

        return rowView;
    }

    private void generatePostMessage(int percent) {
        if (!Characteristic.isUFO()) {
            StringBuilder postMessage = new StringBuilder();
            postMessage.append(String.format(context.getResources().getString(R.string.sphinx_thinks_that_i_smone),
                    context.getResources().getStringArray(R.array.types)[ResultActivity.maxResultIndex].toLowerCase()) + " ");
            postMessage.append(Characteristic.getAge() + " " + context.getResources().getStringArray(R.array.age_array)[Characteristic.getAge() % 10 == 1 ? 0 : 1] + ", ");
            postMessage.append(context.getResources().getStringArray(R.array.post_array)[Characteristic.feedBackCategory[1]] + ", ");

            if (progress[2] > Constants.MIN) {
                postMessage.append(String.format(context.getResources().getString(R.string.i_can_not_image_my_life_without_smth),
                        context.getResources().getStringArray(R.array.post_array)[Characteristic.feedBackCategory[2]]));
                if (progress[3] > Constants.MIN) {
                    postMessage.append(" " + String.format(context.getResources().getString(R.string.and_smth),
                            context.getResources().getStringArray(R.array.post_array)[Characteristic.feedBackCategory[3]]));
                } else {
                    postMessage.append(", " + String.format(context.getResources().getString(R.string.i_like_smth),
                            context.getResources().getStringArray(R.array.post_array)[Characteristic.feedBackCategory[3]]));
                }
            } else {
                postMessage.append(String.format(context.getResources().getString(R.string.i_like_smth),
                        context.getResources().getStringArray(R.array.post_array)[Characteristic.feedBackCategory[2]]));
                if (progress[3] > Constants.MIN) {
                    postMessage.append(", " + String.format(context.getResources().getString(R.string.i_like_smth),
                            context.getResources().getStringArray(R.array.post_array)[Characteristic.feedBackCategory[3]]));
                } else {
                    postMessage.append(" " + String.format(context.getResources().getString(R.string.and_smth),
                            context.getResources().getStringArray(R.array.post_array)[Characteristic.feedBackCategory[3]]));
                }
            }
            postMessage.append(".\n\n" + context.getResources().getString(R.string.sphinx_was_right_at) + " " + percent + "%");

            postText.setText(postMessage.toString());
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    private class ViewHolder {
        public ImageView progressBar;
        public ImageView progressBarTriangle;
        public TextView progressText;
        public TextView typeText;
        public CheckBox isRightButton;
        public Button questionButton;
    }
}
