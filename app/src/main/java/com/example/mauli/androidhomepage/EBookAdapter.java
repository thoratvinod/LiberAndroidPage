package com.example.mauli.androidhomepage;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EBookAdapter extends RecyclerView.Adapter<EBookAdapter.EBookViewHolder> {


    private Context mCtx;

    private List<EBook> eBookList;

    public EBookAdapter(Context mCtx, List<EBook> eBookList) {
        this.mCtx = mCtx;
        this.eBookList = eBookList;
    }

    @Override
    public EBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.ebook_set_list_view, null);
        return new EBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EBookViewHolder holder, int i) {
        //getting the product of the specified position
        final EBook eBook = eBookList.get(i);


        holder.eBookName.setText(eBook.geteBookName());
        holder.eBookAuthor.setText(eBook.geteBookAuthor());

        holder.eBookParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx,PdfViewerActivity.class);
                intent.putExtra("E-Book", (Parcelable) eBook);
                mCtx.startActivity(intent);
            }

        });




        //binding the data with the viewholder views
        /*holder.textViewTitle.setText(product.getTitle());
        holder.textViewShortDesc.setText(product.getShortdesc());
        holder.textViewRating.setText(String.valueOf(product.getRating()));
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));*/

    }


    @Override
    public int getItemCount() {
        return eBookList.size();
    }



    public void updateBookSetList(){
        eBookList = new ArrayList<>();

    }


    class EBookViewHolder extends RecyclerView.ViewHolder {

        TextView eBookName,eBookAuthor;
        RelativeLayout eBookParentLayout;

        public EBookViewHolder(View itemView) {
            super(itemView);


            eBookName = (TextView) itemView.findViewById(R.id.eBookName);
            eBookAuthor= (TextView) itemView.findViewById(R.id.eBookAuthor);
            eBookParentLayout = (RelativeLayout) itemView.findViewById(R.id.eBookParentLayout);

            //bookSetImage = (ImageView) itemView.findViewById(R.id.bookSetImageView);




            /*textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);*/
        }
    }
}