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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class SellerBookSetAdapter extends RecyclerView.Adapter<SellerBookSetAdapter.BookSetViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<BookSet> bookSetList;

    //getting the context and product list with constructor
    public SellerBookSetAdapter(Context mCtx, List<BookSet> bookSetList) {
        this.mCtx = mCtx;
        this.bookSetList = bookSetList;
    }

    @Override
    public BookSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = LayoutInflater.from(mCtx)
                .inflate(R.layout.bookset_list_view, parent, false);
        return new BookSetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookSetViewHolder holder, int i) {
        //getting the product of the specified position
        final BookSet bookSet = bookSetList.get(i);

        //holder.course.setText(bookSet.getCourse_name());
        holder.branch.setText(bookSet.getYear()+" "+bookSet.getBranch_name());
        holder.price.setText("₹ " + bookSet.getPrice());
        holder.title.setText(bookSet.getTitle());
        holder.subjects.setText(bookSet.getNoOfSubjects()+" books is/are in set.");
        if (bookSet.getBookset_image()!=null){
            holder.progressBar.setVisibility(View.VISIBLE);
            Glide.with(mCtx)
                    .load(bookSet.getBookset_image())
                    .apply(new RequestOptions().override(120   , 180))
                    .into(holder.bookSetImage);

            holder.progressBar.setVisibility(View.GONE);
        }

        //holder.bookSetImage.setImageURI();

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx,SellerBookSetInfo.class);
                intent.putExtra("BookSet", (Parcelable) bookSet);
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
        return bookSetList.size();
    }



    public void updateBookSetList(){
        bookSetList = new ArrayList<>();

    }


    class BookSetViewHolder extends RecyclerView.ViewHolder {

        TextView course, branch, price, year,type,negotiable,title,subjects;
        ImageView bookSetImage;
        LinearLayout parentLayout,sellerParentLayout;
        private ProgressBar progressBar;

        public BookSetViewHolder(View itemView) {
            super(itemView);


            course = (TextView) itemView.findViewById(R.id.course);
            branch = (TextView) itemView.findViewById(R.id.branch);
            price = (TextView) itemView.findViewById(R.id.price);
            negotiable = (TextView) itemView.findViewById(R.id.negotiable);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.parentLayout);
            sellerParentLayout = (LinearLayout) itemView.findViewById(R.id.sellerParentLayout);
            bookSetImage = (ImageView) itemView.findViewById(R.id.booksetImageInBuy);
            title = (TextView) itemView.findViewById(R.id.titleOfListView);
            subjects = (TextView) itemView.findViewById(R.id.subjects_in_books);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progrssbarInListView);

            //bookSetImage = (ImageView) itemView.findViewById(R.id.bookSetImageView);




            /*textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);*/
        }
    }
}