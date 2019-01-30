package com.ingic.lmslawyer.ui.viewbinders.abstracts;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.ingic.lmslawyer.R;
import com.ingic.lmslawyer.entities.LawyerSearchItem;
import com.ingic.lmslawyer.ui.views.AnyTextView;
import com.ingic.lmslawyer.util.RoundCornersImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LawyerListItemBinder extends ViewBinder<LawyerSearchItem> {

    private Context context;

    private ImageLoader imageLoader;

    public LawyerListItemBinder(Context context) {
        super(R.layout.lawyer_list_item);

        this.context = context;
        this.imageLoader = ImageLoader.getInstance();
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final LawyerSearchItem entity, int position, int grpPosition, View view, Activity activity) {


        ViewHolder viewHolder = (ViewHolder) view.getTag();

      /*  imageLoader.displayImage(entity.getProfile_image(), viewHolder.profileImage);
        viewHolder.txtName.setText(entity.getUser_name());

        StringBuilder fields = new StringBuilder();

        for (int i = 0; i < entity.getFields().size(); i++) {
            fields.append(entity.getFields().get(i).getField_detail().getTitle());
            fields.append(", ");
        }

        fields = fields.deleteCharAt(fields.length() - 2);


        viewHolder.txtField.setText(fields);
        viewHolder.txtProfession.setText("Lawyer");
        viewHolder.txtExp.setText(entity.getExperience_detail().getTitle());

        viewHolder.btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LawyersProfileFragment lawyersProfileFragment = LawyersProfileFragment.newInstance();
                lawyersProfileFragment.setListItem(entity);

                ((DockActivity) context).addDockableFragment(lawyersProfileFragment, "LawyersProfileFragment");
            }
        });
*/
    }

    public static class ViewHolder extends BaseViewHolder {

        private RoundCornersImageView profileImage;
        private AnyTextView txtName;
        private AnyTextView txtField;
        private AnyTextView txtProfession;
        private AnyTextView txtExp;

        private Button btnViewProfile;

        public ViewHolder(View view) {

            profileImage = (RoundCornersImageView) view.findViewById(R.id.profileImage);
            txtName = (AnyTextView) view.findViewById(R.id.txtName);
            txtField = (AnyTextView) view.findViewById(R.id.txtField);
            txtProfession = (AnyTextView) view.findViewById(R.id.txtProfession);
            txtExp = (AnyTextView) view.findViewById(R.id.txtExp);

//            btnViewProfile = (Button) view.findViewById(R.id.btnViewProfile);

        }
    }
}